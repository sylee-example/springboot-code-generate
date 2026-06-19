package com.example.odata.support;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * OData v4 CRUD 공통 베이스. WebClient 호출 로직을 한 곳에 모음.
 * 생성된 Service 들이 상속하여 엔티티셋명/타입 토큰만 제공.
 *
 * @param <T>  Response DTO 타입
 * @param <R>  Request DTO 타입
 * @param <ID> 키 타입 (Integer, String, UUID 등)
 */
public abstract class AbstractODataService<T, R, ID> {

    protected final WebClient odataWebClient;

    protected AbstractODataService(WebClient odataWebClient) {
        this.odataWebClient = odataWebClient;
    }

    /** OData 엔티티셋 이름 (예: "Orders"). */
    protected abstract String entitySet();

    /** Response DTO 클래스. */
    protected abstract Class<T> responseType();

    /** 컬렉션 역직렬화용 타입 토큰: {@code new ParameterizedTypeReference<ODataCollection<T>>(){}}. */
    protected abstract ParameterizedTypeReference<ODataCollection<T>> collectionType();

    /** 전체 목록 조회 (GET /Set[?$expand=...]). expand null 이면 미적용. */
    public List<T> list(String expand) {
        ODataCollection<T> result = onError(odataWebClient.get().uri(uri("", expand)).retrieve())
                .bodyToMono(collectionType()).block();
        return result == null ? List.of() : result.getValue();
    }

    /** 단건 조회 (GET /Set(key)[?$expand=...]). */
    public T getByKey(ID key, String expand) {
        return onError(odataWebClient.get().uri(uri(keyPath(key), expand)).retrieve())
                .bodyToMono(responseType()).block();
    }

    /** 생성 (POST /Set). */
    public T create(R request) {
        return sendBody(odataWebClient.post(), "", request);
    }

    /** 수정 (PATCH /Set(key)). */
    public T update(ID key, R request) {
        return sendBody(odataWebClient.patch(), keyPath(key), request);
    }

    /** 삭제 (DELETE /Set(key)). */
    public void delete(ID key) {
        onError(odataWebClient.delete().uri(uri(keyPath(key), null)).retrieve())
                .toBodilessEntity().block();
    }

    // ── 공통 헬퍼 ───────────────────────────────────────────────

    /** URI 빌더: "/{entitySet}{suffix}" + optional $expand. 모든 호출이 공유. */
    private Function<UriBuilder, URI> uri(String suffix, String expand) {
        return b -> b.path("/" + entitySet() + suffix)
                .queryParamIfPresent("$expand", Optional.ofNullable(expand))
                .build();
    }

    /** body 전송(POST/PATCH) 공통: uri + bodyValue + retrieve + 에러변환 + block. */
    private T sendBody(WebClient.RequestBodyUriSpec spec, String suffix, R request) {
        return onError(spec.uri(uri(suffix, null)).bodyValue(request).retrieve())
                .bodyToMono(responseType()).block();
    }

    /** 4xx/5xx → ODataApiException 변환. 모든 호출의 retrieve() 가 통과. */
    private WebClient.ResponseSpec onError(WebClient.ResponseSpec spec) {
        return spec.onStatus(HttpStatusCode::isError, this::toException);
    }

    private Mono<? extends Throwable> toException(ClientResponse response) {
        return response.bodyToMono(String.class)
                .defaultIfEmpty("")
                .map(body -> new ODataApiException(response.statusCode(), body));
    }

    /** 키 URL 세그먼트. 문자열/Guid 는 작은따옴표, 숫자는 그대로. */
    protected String keyPath(ID key) {
        boolean quoted = key instanceof String || key instanceof UUID;
        return quoted ? "('" + key + "')" : "(" + key + ")";
    }
}
