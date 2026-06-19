package com.example.odata.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * OData v4 표준 에러 본문 모델.
 * {@code {"error":{"code","message","target","details":[{"code","target","message"}]}}}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ODataError(
        String code,
        String message,
        String target,
        List<Detail> details
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Detail(String code, String target, String message) {
    }

    /** OData error 노드(JsonNode) → ODataError. 없으면 null. */
    public static ODataError from(JsonNode errorNode) {
        if (errorNode == null || !errorNode.isObject()) {
            return null;
        }
        return new ODataError(
                text(errorNode, "code"),
                message(errorNode),
                text(errorNode, "target"),
                details(errorNode)
        );
    }

    private static String text(JsonNode node, String field) {
        return node.hasNonNull(field) ? node.get(field).asText() : null;
    }

    /** message 는 문자열 또는 {"value":"..."} 객체(SAP 변형) 둘 다 처리. */
    private static String message(JsonNode node) {
        JsonNode msg = node.get("message");
        if (msg == null || msg.isNull()) {
            return null;
        }
        return msg.isObject() && msg.hasNonNull("value") ? msg.get("value").asText() : msg.asText();
    }

    private static List<Detail> details(JsonNode node) {
        JsonNode arr = node.get("details");
        if (arr == null || !arr.isArray() || arr.isEmpty()) {
            return null;
        }
        List<Detail> list = new ArrayList<>();
        for (JsonNode d : arr) {
            list.add(new Detail(text(d, "code"), text(d, "target"), text(d, "message")));
        }
        return list;
    }
}
