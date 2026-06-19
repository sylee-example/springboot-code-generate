package com.example.odata.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * OData v4 컬렉션 응답 래퍼: {"value": [ ... ]}.
 * 생성된 Service 들이 공용으로 사용.
 */
@Data
public class ODataCollection<T> {

    @JsonProperty("value")
    private List<T> value;

    @JsonProperty("@odata.count")
    private Long count;

    @JsonProperty("@odata.nextLink")
    private String nextLink;
}
