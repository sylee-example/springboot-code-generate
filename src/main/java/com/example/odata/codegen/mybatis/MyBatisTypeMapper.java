package com.example.odata.codegen.mybatis;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * MyBatis 타입 → Java 타입 매핑.
 * - 내장 별칭(int, string, long ...) → 박싱 Java 타입
 * - FQCN(java.lang.String, com.x.FooVo ...) → 해당 클래스 그대로(기존 클래스 재사용)
 */
final class MyBatisTypeMapper {

    private MyBatisTypeMapper() {
    }

    // MyBatis 내장 타입 별칭 (소문자 키)
    private static final Map<String, TypeName> ALIASES = Map.ofEntries(
            Map.entry("string", ClassName.get(String.class)),
            Map.entry("byte", ClassName.get(Byte.class)),
            Map.entry("short", ClassName.get(Short.class)),
            Map.entry("int", ClassName.get(Integer.class)),
            Map.entry("integer", ClassName.get(Integer.class)),
            Map.entry("long", ClassName.get(Long.class)),
            Map.entry("float", ClassName.get(Float.class)),
            Map.entry("double", ClassName.get(Double.class)),
            Map.entry("boolean", ClassName.get(Boolean.class)),
            Map.entry("date", ClassName.get(java.util.Date.class)),
            Map.entry("decimal", ClassName.get(BigDecimal.class)),
            Map.entry("bigdecimal", ClassName.get(BigDecimal.class)),
            Map.entry("biginteger", ClassName.get(BigInteger.class)),
            Map.entry("object", ClassName.get(Object.class)),
            Map.entry("map", ClassName.get(java.util.Map.class))
    );

    /** 타입 문자열 → Java TypeName. 알 수 없으면 String 으로 fallback. */
    static TypeName resolve(String type) {
        if (type == null || type.isBlank()) {
            return ClassName.get(String.class);
        }
        String t = type.trim();
        TypeName alias = ALIASES.get(t.toLowerCase());
        if (alias != null) {
            return alias;
        }
        if (t.contains(".")) {
            return ClassName.bestGuess(t); // FQCN — 기존 클래스 참조
        }
        // 패키지 없는 미지의 별칭 → String fallback
        return ClassName.get(String.class);
    }

    /** FQCN(패키지 포함 클래스명) 여부 — 기존 클래스 재사용 판단용. */
    static boolean isFqcn(String type) {
        return type != null && type.contains(".") && !ALIASES.containsKey(type.trim().toLowerCase());
    }

    /** 스칼라(원시/문자/숫자) 타입 여부 — select 단건/스칼라 판단용. */
    static boolean isScalar(String type) {
        if (type == null) {
            return false;
        }
        return ALIASES.containsKey(type.trim().toLowerCase()) && !"map".equalsIgnoreCase(type.trim());
    }
}
