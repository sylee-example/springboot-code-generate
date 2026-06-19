package com.example.odata.codegen.mybatis;

import com.example.odata.codegen.GenConfig;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * Mapper → Service 생성. Mapper 주입받아 위임.
 */
final class MapperServiceGenerator {

    private static final ClassName SERVICE_ANN = ClassName.get("org.springframework.stereotype", "Service");
    private static final ClassName RAC_ANN = ClassName.get("lombok", "RequiredArgsConstructor");
    private static final ClassName TX_ANN =
            ClassName.get("org.springframework.transaction.annotation", "Transactional");

    private final GenConfig cfg;

    MapperServiceGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    void generate(MapperModel model) throws IOException {
        String entity = model.entityName();
        String namespace = model.namespace();
        int lastDot = namespace.lastIndexOf('.');
        ClassName mapperType = ClassName.get(
                lastDot > 0 ? namespace.substring(0, lastDot) : "",
                namespace.substring(lastDot + 1));
        String mapperField = decapitalize(mapperType.simpleName());

        SignatureResolver resolver = new SignatureResolver(cfg, model);

        FieldSpec field = FieldSpec.builder(mapperType, mapperField, Modifier.PRIVATE, Modifier.FINAL).build();

        TypeSpec.Builder type = TypeSpec.classBuilder(entity + "Service")
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("$L Service — 생성 코드. Mapper 위임.\n", entity)
                .addAnnotation(SERVICE_ANN)
                .addAnnotation(RAC_ANN)
                // 클래스 기본: 읽기 전용 트랜잭션 (조회 메서드 적용)
                .addAnnotation(AnnotationSpec.builder(TX_ANN)
                        .addMember("readOnly", "$L", true).build())
                .addField(field);

        for (MapperModel.StatementModel st : model.statements()) {
            type.addMethod(method(resolver, st, mapperField));
        }

        JavaFile.builder(cfg.servicePackage(), type.build())
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }

    private MethodSpec method(SignatureResolver resolver, MapperModel.StatementModel st, String mapperField) {
        MethodSpec.Builder m = MethodSpec.methodBuilder(st.id())
                .addModifiers(Modifier.PUBLIC)
                .returns(resolver.returnType(st));

        // DML(insert/update/delete) → 쓰기 트랜잭션. 예외 시 전체 롤백(rollbackFor=Exception)
        if (!st.isSelect()) {
            m.addAnnotation(AnnotationSpec.builder(TX_ANN)
                    .addMember("rollbackFor", "$T.class", Exception.class).build());
        }

        SignatureResolver.ParamInfo p = resolver.paramInfo(st);
        if (p.hasParam()) {
            m.addParameter(ParameterSpec.builder(p.type(), p.name()).build());
            m.addStatement("return $L.$L($L)", mapperField, st.id(), p.name());
        } else {
            m.addStatement("return $L.$L()", mapperField, st.id());
        }
        return m.build();
    }

    private static String decapitalize(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
