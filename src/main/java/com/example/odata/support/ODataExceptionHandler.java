package com.example.odata.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/**
 * 전역 예외 핸들러. 생성된 Controller 들의 OData 호출 에러를 일관된 {@link ApiErrorResponse} 로 변환.
 */
@RestControllerAdvice
public class ODataExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ODataExceptionHandler.class);

    /** SAP 가 4xx/5xx 반환 — 상태코드/코드/메시지 전달. */
    @ExceptionHandler(ODataApiException.class)
    public ResponseEntity<ApiErrorResponse> handleODataApi(ODataApiException e) {
        log.warn("OData API 에러 status={} code={} msg={}", e.getStatus(), e.getCode(), e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(ApiErrorResponse.ofOData(e.getStatus(), e.getError()));
    }

    /** SAP 연결 실패 (타임아웃/DNS/거부 등) → 502 Bad Gateway. */
    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreachable(WebClientRequestException e) {
        log.error("OData 연결 실패", e);
        return ResponseEntity.status(502)
                .body(ApiErrorResponse.of(502, "ODATA_UNREACHABLE", "OData 서비스 연결 실패: " + e.getMessage()));
    }

    /** 그 외 미처리 예외 → 500. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleEtc(Exception e) {
        log.error("내부 서버 에러", e);
        return ResponseEntity.status(500)
                .body(ApiErrorResponse.of(500, "INTERNAL_ERROR", e.getMessage()));
    }
}
