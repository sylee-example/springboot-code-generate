package com.example.odata.generated.service;

import com.example.odata.generated.dto.OrderRequest;
import com.example.odata.generated.dto.OrderResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Order OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class OrderService extends AbstractODataService<OrderResponse, OrderRequest, Integer> {
  private static final String ENTITY_SET = "Orders";

  public OrderService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<OrderResponse> responseType() {
    return OrderResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<OrderResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<OrderResponse>>() {
    };
  }
}
