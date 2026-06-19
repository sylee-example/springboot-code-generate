package com.example.odata.codegen.mybatis;

import com.example.odata.codegen.GenConfig;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * resultMap → Response DTO, 다중 #{} 파라미터 → Request DTO 생성. Lombok @Data.
 */
final class MapperDtoGenerator {

    private final GenConfig cfg;
    private final Set<String> written = new HashSet<>();

    MapperDtoGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    void generate(MapperModel model) throws IOException {
        SignatureResolver resolver = new SignatureResolver(cfg, model);

        // 1) resultMap → Response DTO
        for (MapperModel.ResultMapModel rm : model.resultMaps()) {
            String name = rm.dtoSimpleName();
            if (!written.add(name)) {
                continue;
            }
            TypeSpec.Builder type = baseType(name, "조회 응답 DTO (resultMap: " + rm.id() + ")");
            int count = 0;
            for (MapperModel.FieldModel f : rm.fields()) {
                TypeName ft = MyBatisTypeMapper.resolve(f.javaType());
                type.addField(FieldSpec.builder(ft, f.property(), Modifier.PRIVATE)
                        .addJavadoc("컬럼 $L\n", f.column()).build());
                count++;
            }
            finishAndWrite(type, count);
        }

        // 2) 다중 파라미터 statement → Request DTO
        for (MapperModel.StatementModel st : model.statements()) {
            SignatureResolver.ParamInfo p = resolver.paramInfo(st);
            if (!p.isGenerated() || !written.add(p.generatedDtoName())) {
                continue;
            }
            TypeSpec.Builder type = baseType(p.generatedDtoName(), "요청 DTO (statement: " + st.id() + ")");
            for (String field : p.generatedFields()) {
                // #{} 스캔은 타입 미상 → String 기본
                type.addField(FieldSpec.builder(ClassName.get(String.class), field, Modifier.PRIVATE).build());
            }
            finishAndWrite(type, p.generatedFields().size());
        }
    }

    private TypeSpec.Builder baseType(String name, String javadoc) {
        return TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("$L\n생성 코드 — 수정하지 마세요.\n", javadoc)
                .addAnnotation(ClassName.get("lombok", "Data"))
                .addAnnotation(ClassName.get("lombok", "NoArgsConstructor"));
    }

    private void finishAndWrite(TypeSpec.Builder type, int fieldCount) throws IOException {
        if (fieldCount > 0) {
            type.addAnnotation(ClassName.get("lombok", "AllArgsConstructor"));
        }
        JavaFile.builder(cfg.dtoPackage(), type.build())
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }
}
