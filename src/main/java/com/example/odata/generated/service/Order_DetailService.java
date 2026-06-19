package com.example.odata.generated.service;

import com.example.odata.generated.dto.Order_DetailRequest;
import com.example.odata.generated.dto.Order_DetailResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Order_Detail OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Order_DetailService extends AbstractODataService<Order_DetailResponse, Order_DetailRequest, Integer> {
  private static final String ENTITY_SET = "Order_Details";

  public Order_DetailService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Order_DetailResponse> responseType() {
    return Order_DetailResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Order_DetailResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<Order_DetailResponse>>() {
    };
  }
}
