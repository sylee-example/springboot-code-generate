package com.example.odata.generated.service;

import com.example.odata.generated.dto.Order_SubtotalRequest;
import com.example.odata.generated.dto.Order_SubtotalResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Order_Subtotal OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Order_SubtotalService extends AbstractODataService<Order_SubtotalResponse, Order_SubtotalRequest, Integer> {
  private static final String ENTITY_SET = "Order_Subtotals";

  public Order_SubtotalService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Order_SubtotalResponse> responseType() {
    return Order_SubtotalResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Order_SubtotalResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<Order_SubtotalResponse>>() {
    };
  }
}
