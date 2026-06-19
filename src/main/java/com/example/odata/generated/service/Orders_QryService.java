package com.example.odata.generated.service;

import com.example.odata.generated.dto.Orders_QryRequest;
import com.example.odata.generated.dto.Orders_QryResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Orders_Qry OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Orders_QryService extends AbstractODataService<Orders_QryResponse, Orders_QryRequest, String> {
  private static final String ENTITY_SET = "Orders_Qries";

  public Orders_QryService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Orders_QryResponse> responseType() {
    return Orders_QryResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Orders_QryResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<Orders_QryResponse>>() {
    };
  }
}
