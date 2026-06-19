package com.example.odata.generated.service;

import com.example.odata.generated.dto.Order_Details_ExtendedRequest;
import com.example.odata.generated.dto.Order_Details_ExtendedResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Order_Details_Extended OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Order_Details_ExtendedService extends AbstractODataService<Order_Details_ExtendedResponse, Order_Details_ExtendedRequest, Float> {
  private static final String ENTITY_SET = "Order_Details_Extendeds";

  public Order_Details_ExtendedService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Order_Details_ExtendedResponse> responseType() {
    return Order_Details_ExtendedResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Order_Details_ExtendedResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Order_Details_ExtendedResponse>>() {
    };
  }
}
