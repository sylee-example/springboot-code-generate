package com.example.odata.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;

/**
 * SAP OData 호출이 4xx/5xx 응답할 때 던지는 예외.
 * OData v4 에러 본문을 {@link ODataError} 로 파싱해 원본 code/message/details 보관.
 */
public class ODataApiException extends RuntimeException {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final int status;
    private final transient ODataError error;

    public ODataApiException(HttpStatusCode status, String rawBody) {
        super(resolveMessage(parse(rawBody), status));
        this.status = status.value();
        this.error = parse(rawBody);
    }

    public int getStatus() {
        return status;
    }

    /** SAP 가 보낸 원본 OData 에러 (code/message/details). 파싱 실패 시 null. */
    public ODataError getError() {
        return error;
    }

    public String getCode() {
        return error != null && error.code() != null ? error.code() : "ODATA_ERROR";
    }

    private static ODataError parse(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            return ODataError.from(MAPPER.readTree(body).get("error"));
        } catch (Exception ignore) {
            return null;
        }
    }

    private static String resolveMessage(ODataError error, HttpStatusCode status) {
        if (error != null && error.message() != null) {
            return error.message();
        }
        return "OData 호출 실패 (HTTP " + status.value() + ")";
    }
}
