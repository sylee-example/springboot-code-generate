package com.example.odata.generated.service;

import com.example.odata.generated.dto.TerritoryRequest;
import com.example.odata.generated.dto.TerritoryResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Territory OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class TerritoryService extends AbstractODataService<TerritoryResponse, TerritoryRequest, String> {
  private static final String ENTITY_SET = "Territories";

  public TerritoryService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<TerritoryResponse> responseType() {
    return TerritoryResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<TerritoryResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<TerritoryResponse>>() {
    };
  }
}
