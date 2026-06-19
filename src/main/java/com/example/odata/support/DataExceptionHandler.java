package com.example.odata.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * DB/MyBatis 쿼리 실행 예외 전역 핸들러.
 * Spring 이 SQLException 을 DataAccessException 계층으로 변환 → 일관된 {@link ApiErrorResponse} 로 응답.
 * code 에는 가능하면 Oracle 에러코드(ORA-xxxxx) 를 실어줌.
 */
@RestControllerAdvice
public class DataExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DataExceptionHandler.class);

    /** UNIQUE 제약 위반 (ORA-00001 등) → 409. */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicate(DuplicateKeyException e) {
        return build(409, dbCode(e, "DUPLICATE_KEY"), e);
    }

    /** NOT NULL / FK / CHECK 등 무결성 위반 → 409. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleIntegrity(DataIntegrityViolationException e) {
        return build(409, dbCode(e, "DATA_INTEGRITY_VIOLATION"), e);
    }

    /** selectOne 결과 없음 → 404. */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleEmpty(EmptyResultDataAccessException e) {
        return build(404, "NOT_FOUND", e);
    }

    /** 잘못된 SQL 문법/객체명 (ORA-00904, ORA-00942 등) → 500 (개발 오류). */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<ApiErrorResponse> handleGrammar(BadSqlGrammarException e) {
        return build(500, dbCode(e, "SQL_GRAMMAR_ERROR"), e);
    }

    /** 쿼리 타임아웃/락 획득 실패 → 503. */
    @ExceptionHandler(QueryTimeoutException.class)
    public ResponseEntity<ApiErrorResponse> handleTimeout(QueryTimeoutException e) {
        return build(503, dbCode(e, "QUERY_TIMEOUT"), e);
    }

    /** 그 외 모든 DB 접근 예외 → 500. */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDataAccess(DataAccessException e) {
        return build(500, dbCode(e, "DB_ERROR"), e);
    }

    private ResponseEntity<ApiErrorResponse> build(int status, String code, DataAccessException e) {
        String message = e.getMostSpecificCause().getMessage();
        log.error("DB 에러 status={} code={} msg={}", status, code, message, e);
        return ResponseEntity.status(status).body(ApiErrorResponse.of(status, code, message));
    }

    /** 루트 원인이 SQLException 이면 Oracle 에러코드(ORA-xxxxx), 아니면 fallback. */
    private String dbCode(DataAccessException e, String fallback) {
        Throwable root = e.getRootCause();
        if (root instanceof SQLException sql && sql.getErrorCode() != 0) {
            return String.format("ORA-%05d", sql.getErrorCode());
        }
        return fallback;
    }
}
