package com.example.odata.generated.service;

import com.example.odata.generated.dto.ShipperRequest;
import com.example.odata.generated.dto.ShipperResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Shipper OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class ShipperService extends AbstractODataService<ShipperResponse, ShipperRequest, Integer> {
  private static final String ENTITY_SET = "Shippers";

  public ShipperService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<ShipperResponse> responseType() {
    return ShipperResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<ShipperResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<ShipperResponse>>() {
    };
  }
}
