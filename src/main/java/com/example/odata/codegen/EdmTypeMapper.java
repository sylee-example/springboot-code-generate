package com.example.odata.codegen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * OData Edm 원시 타입 → Java 타입 매핑.
 * 모두 박싱 타입 사용(nullable 표현 가능).
 */
final class EdmTypeMapper {

    private EdmTypeMapper() {
    }

    // FQN(예: "Edm.String") → Java TypeName
    private static final Map<String, TypeName> MAP = Map.ofEntries(
            Map.entry("Edm.String", ClassName.get(String.class)),
            Map.entry("Edm.Boolean", ClassName.get(Boolean.class)),
            Map.entry("Edm.Byte", ClassName.get(Short.class)),
            Map.entry("Edm.SByte", ClassName.get(Byte.class)),
            Map.entry("Edm.Int16", ClassName.get(Short.class)),
            Map.entry("Edm.Int32", ClassName.get(Integer.class)),
            Map.entry("Edm.Int64", ClassName.get(Long.class)),
            Map.entry("Edm.Single", ClassName.get(Float.class)),
            Map.entry("Edm.Double", ClassName.get(Double.class)),
            Map.entry("Edm.Decimal", ClassName.get(BigDecimal.class)),
            Map.entry("Edm.Guid", ClassName.get(UUID.class)),
            Map.entry("Edm.Date", ClassName.get(LocalDate.class)),
            Map.entry("Edm.TimeOfDay", ClassName.get(LocalTime.class)),
            Map.entry("Edm.DateTimeOffset", ClassName.get(OffsetDateTime.class)),
            Map.entry("Edm.Duration", ClassName.get(String.class)),
            Map.entry("Edm.Binary", ArrayTypeNames.BYTE_ARRAY),
            Map.entry("Edm.Stream", ClassName.get(String.class))
    );

    /** 원시 타입이면 Java 타입 반환, 아니면 null(복합/엔티티 타입은 호출부에서 별도 처리). */
    static TypeName primitiveOrNull(String edmFqn) {
        return MAP.get(edmFqn);
    }

    private static final class ArrayTypeNames {
        static final TypeName BYTE_ARRAY = com.squareup.javapoet.ArrayTypeName.of(TypeName.BYTE);
    }
}
