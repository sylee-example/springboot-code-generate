package com.example.odata.codegen;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmAction;
import org.apache.olingo.commons.api.edm.EdmActionImport;
import org.apache.olingo.commons.api.edm.EdmEntityContainer;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmFunction;
import org.apache.olingo.commons.api.edm.EdmFunctionImport;
import org.apache.olingo.commons.api.edm.EdmKeyPropertyRef;
import org.apache.olingo.commons.api.edm.EdmOperation;
import org.apache.olingo.commons.api.edm.EdmSchema;
import org.apache.olingo.commons.api.edm.FullQualifiedName;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * EDMX(OData v4 metadata) → OData URL 경로 템플릿 상수 클래스({@code ODataPaths}) 생성.
 *
 * 생성 대상:
 *  - EntitySet           : /{Set}, /{Set}({key})
 *  - NavigationProperty  : /{Set}({key})/{Nav}
 *  - bound function      : /{Set}({key})/{FQN}(params)  또는 컬렉션 바인딩 /{Set}/{FQN}(params)
 *  - bound action        : /{Set}({key})/{FQN}          또는 컬렉션 바인딩 /{Set}/{FQN}
 *  - FunctionImport      : /{Import}(params)             (unbound, GET)
 *  - ActionImport        : /{Import}                     (unbound, POST)
 */
final class ODataPathGenerator {

    private final GenConfig cfg;

    ODataPathGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    /** path 1건. kind 는 분류용(entitySet/byKey/navigation/boundAction/boundFunction/actionImport/functionImport). */
    record PathEntry(String constName, String template, String kind) {
    }

    /** EDMX 모델 → path 목록. 상수명 중복은 _2, _3 … 으로 회피. */
    List<PathEntry> resolve(Edm edm) {
        List<PathEntry> raw = new ArrayList<>();

        EdmEntityContainer container = edm.getEntityContainer();
        if (container == null) {
            return raw;
        }

        // entityType FQN → EntitySet (bound operation 바인딩 대상 매핑용). 같은 타입 다중 set 이면 첫 set.
        Map<String, EdmEntitySet> setByType = new HashMap<>();
        for (EdmEntitySet set : container.getEntitySets()) {
            setByType.putIfAbsent(set.getEntityType().getFullQualifiedName().getFullQualifiedNameAsString(), set);
        }

        // 1) EntitySet + by-key + navigation
        for (EdmEntitySet set : container.getEntitySets()) {
            String setName = set.getName();
            EdmEntityType et = set.getEntityType();
            String keySeg = keySegment(et);

            raw.add(new PathEntry(setName, "/" + setName, "entitySet"));
            raw.add(new PathEntry(setName + "_byKey", "/" + setName + keySeg, "byKey"));

            for (String nav : et.getNavigationPropertyNames()) {
                raw.add(new PathEntry(setName + "_" + nav,
                        "/" + setName + keySeg + "/" + nav, "navigation"));
            }
        }

        // 2) bound operations (스키마의 isBound 함수/액션) → 바인딩 대상 EntitySet 경로에 부착
        for (EdmSchema schema : edm.getSchemas()) {
            for (EdmFunction fn : schema.getFunctions()) {
                if (fn.isBound()) {
                    addBoundOp(raw, setByType, fn, "boundFunction");
                }
            }
            for (EdmAction ac : schema.getActions()) {
                if (ac.isBound()) {
                    addBoundOp(raw, setByType, ac, "boundAction");
                }
            }
        }

        // 3) unbound — ActionImport / FunctionImport (컨테이너 노출)
        for (EdmActionImport ai : container.getActionImports()) {
            raw.add(new PathEntry(ai.getName(), "/" + ai.getName(), "actionImport"));
        }
        for (EdmFunctionImport fi : container.getFunctionImports()) {
            String params = unboundFunctionParams(fi);
            raw.add(new PathEntry(fi.getName(), "/" + fi.getName() + params, "functionImport"));
        }

        return dedupeConstNames(raw);
    }

    /** {@code ODataPaths.java} 파일 생성. */
    void generate(Edm edm) throws IOException {
        List<PathEntry> entries = resolve(edm);

        TypeSpec.Builder type = TypeSpec.classBuilder("ODataPaths")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("OData URL 경로 템플릿 상수 — 생성 코드. 서비스 루트 기준 상대 경로.\n수정하지 마세요.\n");

        for (PathEntry e : entries) {
            type.addField(FieldSpec.builder(String.class, toConst(e.constName()),
                            Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addJavadoc("$L\n", e.kind())
                    .initializer("$S", e.template())
                    .build());
        }

        // 유틸 클래스 — 인스턴스화 방지
        type.addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build());

        JavaFile.builder(cfg.basePackage(), type.build())
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }

    // --- helpers ---

    private void addBoundOp(List<PathEntry> raw, Map<String, EdmEntitySet> setByType,
                            EdmOperation op, String kind) {
        FullQualifiedName bindingFqn = op.getBindingParameterTypeFqn();
        if (bindingFqn == null) {
            return; // 바인딩 타입 불명 — 건너뜀
        }
        EdmEntitySet set = setByType.get(bindingFqn.getFullQualifiedNameAsString());
        if (set == null) {
            return; // 매핑되는 EntitySet 없음 (예: 추상/노출 안 된 타입)
        }
        String setName = set.getName();
        String opFqn = op.getFullQualifiedName().getFullQualifiedNameAsString();
        // 컬렉션 바인딩이면 키 없이 set 에, 단건 바인딩이면 키 세그먼트 뒤에
        String prefix = op.isBindingParameterTypeCollection()
                ? "/" + setName
                : "/" + setName + keySegment(set.getEntityType());
        String params = op instanceof EdmFunction fn ? boundFunctionParams(fn) : ""; // action 은 파라미터 세그먼트 없음
        raw.add(new PathEntry(setName + "_" + op.getName(), prefix + "/" + opFqn + params, kind));
    }

    /** by-key 세그먼트: 단일키 {@code (Id={Id})} 형태는 단일키일 때 이름 생략 가능 → {@code ({Id})}. 복합키는 {@code (K1={K1},K2={K2})}. */
    private String keySegment(EdmEntityType et) {
        List<EdmKeyPropertyRef> keys = et.getKeyPropertyRefs();
        if (keys.isEmpty()) {
            return "()";
        }
        if (keys.size() == 1) {
            return "({" + keys.get(0).getName() + "})";
        }
        StringJoiner j = new StringJoiner(",", "(", ")");
        for (EdmKeyPropertyRef k : keys) {
            j.add(k.getName() + "={" + k.getName() + "}");
        }
        return j.toString();
    }

    /** bound function 파라미터 세그먼트(바인딩 파라미터 제외). 없으면 {@code ()}. */
    private String boundFunctionParams(EdmFunction fn) {
        List<String> names = new ArrayList<>(fn.getParameterNames());
        if (!names.isEmpty()) {
            names.remove(0); // 첫 파라미터 = 바인딩 파라미터
        }
        return paramSegment(names);
    }

    /** function import(unbound) 파라미터 세그먼트. */
    private String unboundFunctionParams(EdmFunctionImport fi) {
        List<EdmFunction> fns = fi.getUnboundFunctions();
        if (fns.isEmpty()) {
            return "()";
        }
        return paramSegment(fns.get(0).getParameterNames());
    }

    private String paramSegment(List<String> names) {
        StringJoiner j = new StringJoiner(",", "(", ")");
        for (String n : names) {
            j.add(n + "={" + n + "}");
        }
        return j.toString();
    }

    /** 상수명 충돌 시 _2, _3 … 부여. */
    private List<PathEntry> dedupeConstNames(List<PathEntry> raw) {
        Set<String> used = new LinkedHashSet<>();
        List<PathEntry> out = new ArrayList<>(raw.size());
        for (PathEntry e : raw) {
            String base = toConst(e.constName());
            String name = base;
            int i = 2;
            while (!used.add(name)) {
                name = base + "_" + i++;
            }
            out.add(new PathEntry(name, e.template(), e.kind()));
        }
        return out;
    }

    /** 식별자 → UPPER_SNAKE 상수명. 영숫자 외 문자는 _ 로. */
    static String toConst(String s) {
        StringBuilder sb = new StringBuilder();
        boolean prevUnderscore = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean upper = Character.isUpperCase(c);
            if (Character.isLetterOrDigit(c)) {
                // camelCase 경계에 _ 삽입 (앞이 소문자/숫자고 현재가 대문자)
                if (upper && i > 0 && sb.length() > 0 && !prevUnderscore
                        && Character.isLetterOrDigit(s.charAt(i - 1)) && !Character.isUpperCase(s.charAt(i - 1))) {
                    sb.append('_');
                }
                sb.append(Character.toUpperCase(c));
                prevUnderscore = false;
            } else if (sb.length() > 0 && !prevUnderscore) {
                sb.append('_');
                prevUnderscore = true;
            }
        }
        // 끝 _ 정리
        int end = sb.length();
        while (end > 0 && sb.charAt(end - 1) == '_') {
            end--;
        }
        return sb.substring(0, end);
    }
}
