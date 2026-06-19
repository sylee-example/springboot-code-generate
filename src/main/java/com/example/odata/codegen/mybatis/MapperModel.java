package com.example.odata.codegen.mybatis;

import java.util.List;

/**
 * 파싱된 MyBatis mapper XML 모델.
 *
 * @param namespace  mapper namespace (= Mapper 인터페이스 FQCN)
 * @param resultMaps resultMap 정의들
 * @param statements select/insert/update/delete 문들
 */
record MapperModel(String namespace, List<ResultMapModel> resultMaps, List<StatementModel> statements) {

    /** namespace 마지막 세그먼트에서 "Mapper" 제거 → 엔티티명. 예: com.x.UserMapper → User */
    String entityName() {
        String simple = namespace.substring(namespace.lastIndexOf('.') + 1);
        return simple.endsWith("Mapper") ? simple.substring(0, simple.length() - "Mapper".length()) : simple;
    }

    /** id 로 resultMap 찾기. */
    ResultMapModel findResultMap(String id) {
        return resultMaps.stream().filter(r -> r.id().equals(id)).findFirst().orElse(null);
    }

    /** resultMap 정의: id, type(대상 클래스), 컬럼 매핑 필드들. */
    record ResultMapModel(String id, String type, List<FieldModel> fields) {

        /** resultMap id → Response DTO 클래스명. 예: userResultMap → UserResponse */
        String dtoSimpleName() {
            String s = id;
            for (String suffix : new String[]{"ResultMap", "Result", "Map"}) {
                if (s.length() > suffix.length() && s.regionMatches(true, s.length() - suffix.length(), suffix, 0, suffix.length())) {
                    s = s.substring(0, s.length() - suffix.length());
                    break;
                }
            }
            return Character.toUpperCase(s.charAt(0)) + s.substring(1) + "Response";
        }
    }

    /** 컬럼-프로퍼티 매핑. javaType 은 없을 수 있음(null). */
    record FieldModel(String column, String property, String javaType) {
    }

    /**
     * statement.
     *
     * @param kind          select | insert | update | delete
     * @param id            statement id (= 메서드명)
     * @param parameterType parameterType 속성 (없으면 null)
     * @param resultType    resultType 속성 (없으면 null)
     * @param resultMap     resultMap 속성 (없으면 null)
     * @param sqlParams     SQL 본문의 #{...} 파라미터명 (중복 제거, 등장 순서)
     */
    record StatementModel(String kind, String id, String parameterType, String resultType,
                          String resultMap, List<String> sqlParams) {

        boolean isSelect() {
            return "select".equals(kind);
        }
    }
}
