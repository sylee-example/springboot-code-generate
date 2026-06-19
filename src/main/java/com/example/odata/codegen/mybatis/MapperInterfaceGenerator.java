package com.example.odata.codegen.mybatis;

import com.example.odata.codegen.GenConfig;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * Mapper 인터페이스 생성. namespace FQCN 에 그대로 생성하여 기존 XML 과 바인딩.
 */
final class MapperInterfaceGenerator {

    private static final ClassName MAPPER_ANN = ClassName.get("org.apache.ibatis.annotations", "Mapper");
    private static final ClassName PARAM_ANN = ClassName.get("org.apache.ibatis.annotations", "Param");

    private final GenConfig cfg;

    MapperInterfaceGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    void generate(MapperModel model) throws IOException {
        String namespace = model.namespace();
        int lastDot = namespace.lastIndexOf('.');
        String pkg = lastDot > 0 ? namespace.substring(0, lastDot) : "";
        String simpleName = namespace.substring(lastDot + 1);

        SignatureResolver resolver = new SignatureResolver(cfg, model);

        TypeSpec.Builder iface = TypeSpec.interfaceBuilder(simpleName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("$L Mapper — 생성 코드. XML namespace 와 바인딩.\n", simpleName)
                .addAnnotation(MAPPER_ANN);

        for (MapperModel.StatementModel st : model.statements()) {
            iface.addMethod(method(resolver, st));
        }

        JavaFile.builder(pkg, iface.build())
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }

    private MethodSpec method(SignatureResolver resolver, MapperModel.StatementModel st) {
        MethodSpec.Builder m = MethodSpec.methodBuilder(st.id())
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addJavadoc("<$L id=\"$L\">\n", st.kind(), st.id())
                .returns(resolver.returnType(st));

        SignatureResolver.ParamInfo p = resolver.paramInfo(st);
        if (p.hasParam()) {
            ParameterSpec.Builder param = ParameterSpec.builder(p.type(), p.name());
            if (p.paramAnnotation()) {
                param.addAnnotation(AnnotationSpec.builder(PARAM_ANN)
                        .addMember("value", "$S", p.name()).build());
            }
            m.addParameter(param.build());
        }
        return m.build();
    }
}
