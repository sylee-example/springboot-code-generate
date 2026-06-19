package com.example.odata.codegen;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmKeyPropertyRef;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.edm.EdmStructuredType;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * EntityType → Response/Request DTO 생성.
 * Lombok @Data 부착. 원시타입은 EdmTypeMapper로, 복합/엔티티 타입은 동일 패키지 DTO 참조.
 */
final class DtoGenerator {

    private final GenConfig cfg;

    DtoGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    /**
     * Response DTO: 모든 속성 포함. EntityType/ComplexType 공용.
     *
     * @param keySource 엔티티 타입이면 키 정보 전달(순환참조 방어용 @JsonIdentityInfo), 복합 타입이면 null
     */
    void generateResponse(EdmStructuredType st, EdmEntityType keySource) throws IOException {
        String name = st.getName() + "Response";
        TypeSpec.Builder type = baseType(name, "OData 응답 DTO: " + st.getName());
        if (keySource != null) {
            type.addAnnotation(identityInfo(keySource));
        }
        int count = 0;
        for (String propName : st.getPropertyNames()) {
            EdmProperty prop = (EdmProperty) st.getProperty(propName);
            type.addField(field(prop));
            count++;
        }
        // NavigationProperty → 관계 필드 ($expand 시 채워짐)
        for (String navName : st.getNavigationPropertyNames()) {
            EdmNavigationProperty nav = st.getNavigationProperty(navName);
            type.addField(navField(nav));
            count++;
        }
        finishAndWrite(type, count);
    }

    /** Request DTO: 키 속성 제외(생성/수정 입력용). 관계는 $ref 로 별도 처리하므로 미포함. */
    void generateRequest(EdmEntityType et) throws IOException {
        String name = et.getName() + "Request";
        Set<String> keyNames = et.getKeyPropertyRefs().stream()
                .map(EdmKeyPropertyRef::getName)
                .collect(Collectors.toSet());

        TypeSpec.Builder type = baseType(name, "OData 요청 DTO: " + et.getName());
        int count = 0;
        for (String propName : et.getPropertyNames()) {
            if (keyNames.contains(propName)) {
                continue;
            }
            EdmProperty prop = (EdmProperty) et.getProperty(propName);
            type.addField(field(prop));
            count++;
        }
        finishAndWrite(type, count);
    }

    private TypeSpec.Builder baseType(String name, String javadoc) {
        return TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("$L\n생성 코드 — 수정하지 마세요.\n", javadoc)
                .addAnnotation(ClassName.get("lombok", "Data"))
                .addAnnotation(ClassName.get("lombok", "NoArgsConstructor"))
                // OData 제어 필드(@odata.etag 등) 역직렬화 시 무시
                .addAnnotation(AnnotationSpec.builder(
                                ClassName.get("com.fasterxml.jackson.annotation", "JsonIgnoreProperties"))
                        .addMember("ignoreUnknown", "$L", true).build());
    }

    /**
     * 순환참조 방어: @JsonIdentityInfo.
     * 직렬화 중 같은 객체 재등장 시 전체 재귀 대신 id 값만 출력 → 무한루프 차단.
     * 단일 키 → 키 속성을 id 로 재사용. 복합 키 → 시퀀스 id("@id") 합성.
     */
    private AnnotationSpec identityInfo(EdmEntityType et) {
        ClassName ann = ClassName.get("com.fasterxml.jackson.annotation", "JsonIdentityInfo");
        ClassName gens = ClassName.get("com.fasterxml.jackson.annotation", "ObjectIdGenerators");
        List<EdmKeyPropertyRef> keys = et.getKeyPropertyRefs();
        if (keys.size() == 1) {
            return AnnotationSpec.builder(ann)
                    .addMember("generator", "$T.PropertyGenerator.class", gens)
                    .addMember("property", "$S", keys.get(0).getName())
                    .build();
        }
        return AnnotationSpec.builder(ann)
                .addMember("generator", "$T.IntSequenceGenerator.class", gens)
                .addMember("property", "$S", "@id")
                .build();
    }

    /** 필드가 1개 이상일 때만 @AllArgsConstructor 부착(빈 DTO 생성자 충돌 방지). */
    private void finishAndWrite(TypeSpec.Builder type, int fieldCount) throws IOException {
        if (fieldCount > 0) {
            type.addAnnotation(ClassName.get("lombok", "AllArgsConstructor"));
        }
        write(type.build());
    }

    private FieldSpec field(EdmProperty prop) {
        TypeName base = resolveType(prop);
        TypeName fieldType = prop.isCollection()
                ? ParameterizedTypeName.get(ClassName.get(List.class), base)
                : base;
        return FieldSpec.builder(fieldType, prop.getName(), Modifier.PRIVATE).build();
    }

    /** 관계 필드: 대상 EntityType 의 Response DTO 참조. 컬렉션이면 List 래핑. */
    private FieldSpec navField(EdmNavigationProperty nav) {
        ClassName base = ClassName.get(cfg.dtoPackage(), nav.getType().getName() + "Response");
        TypeName fieldType = nav.isCollection()
                ? ParameterizedTypeName.get(ClassName.get(List.class), base)
                : base;
        return FieldSpec.builder(fieldType, nav.getName(), Modifier.PRIVATE)
                .addJavadoc("관계 ($$expand=$L 시 채워짐)\n", nav.getName())
                .build();
    }

    /** 원시타입이면 매핑, 아니면 같은 dto 패키지의 *Response 로 참조. */
    private TypeName resolveType(EdmProperty prop) {
        String fqn = prop.getType().getFullQualifiedName().getFullQualifiedNameAsString();
        TypeName primitive = EdmTypeMapper.primitiveOrNull(fqn);
        if (primitive != null) {
            return primitive;
        }
        // ComplexType / EntityType: 단순화 — 타입명 기반 DTO 참조
        String simpleName = prop.getType().getName() + "Response";
        return ClassName.get(cfg.dtoPackage(), simpleName);
    }

    private void write(TypeSpec type) throws IOException {
        JavaFile.builder(cfg.dtoPackage(), type)
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }
}
