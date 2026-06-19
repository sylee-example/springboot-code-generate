package com.example.odata.codegen.mybatis;

import com.example.odata.codegen.GenConfig;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Service → REST Controller 생성. statement 종류별 HTTP 매핑.
 * select→GET, insert→POST, update→PUT, delete→DELETE. 경로 = /api/{entity}/{statementId}.
 */
final class MapperControllerGenerator {

    private static final String WEB = "org.springframework.web.bind.annotation";
    private static final ClassName RESPONSE_ENTITY = ClassName.get("org.springframework.http", "ResponseEntity");

    // kind → 매핑 어노테이션
    private static final Map<String, ClassName> MAPPING = Map.of(
            "select", ClassName.get(WEB, "GetMapping"),
            "insert", ClassName.get(WEB, "PostMapping"),
            "update", ClassName.get(WEB, "PutMapping"),
            "delete", ClassName.get(WEB, "DeleteMapping"));

    private final GenConfig cfg;

    MapperControllerGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    void generate(MapperModel model) throws IOException {
        String entity = model.entityName();
        ClassName serviceType = ClassName.get(cfg.servicePackage(), entity + "Service");
        String serviceField = decapitalize(entity) + "Service";
        String basePath = "/api/" + entity.toLowerCase(Locale.ROOT);

        SignatureResolver resolver = new SignatureResolver(cfg, model);

        FieldSpec field = FieldSpec.builder(serviceType, serviceField, Modifier.PRIVATE, Modifier.FINAL).build();

        TypeSpec.Builder type = TypeSpec.classBuilder(entity + "Controller")
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("$L REST Controller — 생성 코드.\n", entity)
                .addAnnotation(ClassName.get(WEB, "RestController"))
                .addAnnotation(AnnotationSpec.builder(ClassName.get(WEB, "RequestMapping"))
                        .addMember("value", "$S", basePath).build())
                .addAnnotation(ClassName.get("lombok", "RequiredArgsConstructor"))
                .addField(field);

        for (MapperModel.StatementModel st : model.statements()) {
            type.addMethod(method(resolver, st, serviceField));
        }

        JavaFile.builder(cfg.controllerPackage(), type.build())
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }

    private MethodSpec method(SignatureResolver resolver, MapperModel.StatementModel st, String serviceField) {
        TypeName rt = resolver.returnType(st);
        TypeName boxed = rt.isPrimitive() ? rt.box() : rt;

        MethodSpec.Builder m = MethodSpec.methodBuilder(st.id())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(MAPPING.get(st.kind()))
                        .addMember("value", "$S", "/" + st.id()).build())
                .returns(ParameterizedTypeName.get(RESPONSE_ENTITY, boxed));

        SignatureResolver.ParamInfo p = resolver.paramInfo(st);
        if (p.hasParam()) {
            m.addParameter(controllerParam(p, st.kind()));
            m.addStatement("return $T.ok($N.$L($L))", RESPONSE_ENTITY, serviceField, st.id(), p.name());
        } else {
            m.addStatement("return $T.ok($N.$L())", RESPONSE_ENTITY, serviceField, st.id());
        }
        return m.build();
    }

    /** 스칼라 → @RequestParam, 객체 → GET 은 @ModelAttribute / 그 외 @RequestBody. */
    private ParameterSpec controllerParam(SignatureResolver.ParamInfo p, String kind) {
        ParameterSpec.Builder b = ParameterSpec.builder(p.type(), p.name());
        if (p.paramAnnotation()) { // 스칼라 단일
            b.addAnnotation(AnnotationSpec.builder(ClassName.get(WEB, "RequestParam"))
                    .addMember("value", "$S", p.name()).build());
        } else if ("select".equals(kind)) {
            b.addAnnotation(ClassName.get(WEB, "ModelAttribute"));
        } else {
            b.addAnnotation(ClassName.get(WEB, "RequestBody"));
        }
        return b.build();
    }

    private static String decapitalize(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
