package com.example.odata.codegen.mybatis;

import com.example.odata.codegen.GenConfig;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.List;
import java.util.Locale;

/**
 * statement → 메서드 시그니처(반환타입/파라미터) 추론. Mapper/Service/Controller 공통.
 */
final class SignatureResolver {

    private final GenConfig cfg;
    private final MapperModel model;

    SignatureResolver(GenConfig cfg, MapperModel model) {
        this.cfg = cfg;
        this.model = model;
    }

    /** 파라미터 정보. type==null 이면 파라미터 없음. */
    record ParamInfo(
            TypeName type,
            String name,
            boolean paramAnnotation,   // 스칼라 단일 → @Param 필요
            String generatedDtoName,   // 생성할 Request DTO 명 (재사용이면 null)
            List<String> generatedFields
    ) {
        boolean hasParam() {
            return type != null;
        }

        boolean isGenerated() {
            return generatedDtoName != null;
        }
    }

    /** 반환타입: select → List<E>/E/스칼라, DML → int. */
    TypeName returnType(MapperModel.StatementModel st) {
        if (!st.isSelect()) {
            return TypeName.INT;
        }
        TypeName element = selectElementType(st);
        return isSingle(st) ? element : ParameterizedTypeName.get(ClassName.get(List.class), element);
    }

    private TypeName selectElementType(MapperModel.StatementModel st) {
        if (st.resultMap() != null) {
            MapperModel.ResultMapModel rm = model.findResultMap(st.resultMap());
            String dto = rm != null ? rm.dtoSimpleName() : capitalize(st.id()) + "Response";
            return ClassName.get(cfg.dtoPackage(), dto);
        }
        if (st.resultType() != null) {
            return MyBatisTypeMapper.resolve(st.resultType()); // FQCN 재사용 또는 스칼라
        }
        return ClassName.get(Object.class);
    }

    /** select 단건 여부: 스칼라 resultType 이거나 id 가 단건 네이밍. */
    private boolean isSingle(MapperModel.StatementModel st) {
        if (MyBatisTypeMapper.isScalar(st.resultType())) {
            return true;
        }
        String id = st.id().toLowerCase(Locale.ROOT);
        return id.contains("one") || id.contains("byid") || id.contains("bykey")
                || id.endsWith("detail") || id.startsWith("get") || id.startsWith("find") && id.contains("one");
    }

    ParamInfo paramInfo(MapperModel.StatementModel st) {
        String pt = st.parameterType();
        List<String> sql = st.sqlParams();

        if (pt != null) {
            if (MyBatisTypeMapper.isFqcn(pt)) {
                return new ParamInfo(ClassName.bestGuess(pt), "param", false, null, List.of());
            }
            // 스칼라 별칭
            String name = sql.size() == 1 ? sql.get(0) : "param";
            return new ParamInfo(MyBatisTypeMapper.resolve(pt), name, true, null, List.of());
        }

        // parameterType 없음 → #{} 스캔
        if (sql.isEmpty()) {
            return new ParamInfo(null, null, false, null, List.of());
        }
        if (sql.size() == 1) {
            // 단일 파라미터 — 타입 미상 → String 기본
            return new ParamInfo(ClassName.get(String.class), sql.get(0), true, null, List.of());
        }
        // 다중 파라미터 → Request DTO 생성
        String dtoName = capitalize(st.id()) + "Request";
        return new ParamInfo(ClassName.get(cfg.dtoPackage(), dtoName), "param", false, dtoName, sql);
    }

    static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
