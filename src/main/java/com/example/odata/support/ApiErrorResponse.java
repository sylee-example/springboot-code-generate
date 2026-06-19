package com.example.odata.support;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * 일관된 API 에러 응답 포맷.
 *
 * @param success    항상 false (에러)
 * @param status     HTTP 상태코드
 * @param code       에러 코드 (OData code 또는 내부 코드)
 * @param message    사람이 읽을 메시지
 * @param odataError SAP 가 보낸 원본 OData 에러 (code/message/details). OData 에러가 아니면 null
 * @param timestamp  발생 시각
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        boolean success,
        int status,
        String code,
        String message,
        ODataError odataError,
        Instant timestamp
) {
    /** 내부 에러(연결 실패 등) — 원본 OData 에러 없음. */
    public static ApiErrorResponse of(int status, String code, String message) {
        return new ApiErrorResponse(false, status, code, message, null, Instant.now());
    }

    /** OData 에러 — code/message + 원본 OData 에러 그대로 전달. */
    public static ApiErrorResponse ofOData(int status, ODataError error) {
        String code = error != null && error.code() != null ? error.code() : "ODATA_ERROR";
        String message = error != null ? error.message() : null;
        return new ApiErrorResponse(false, status, code, message, error, Instant.now());
    }
}
