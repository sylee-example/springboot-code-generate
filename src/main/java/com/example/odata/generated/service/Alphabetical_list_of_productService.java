package com.example.odata.generated.service;

import com.example.odata.generated.dto.Alphabetical_list_of_productRequest;
import com.example.odata.generated.dto.Alphabetical_list_of_productResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Alphabetical_list_of_product OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Alphabetical_list_of_productService extends AbstractODataService<Alphabetical_list_of_productResponse, Alphabetical_list_of_productRequest, String> {
  private static final String ENTITY_SET = "Alphabetical_list_of_products";

  public Alphabetical_list_of_productService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Alphabetical_list_of_productResponse> responseType() {
    return Alphabetical_list_of_productResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Alphabetical_list_of_productResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Alphabetical_list_of_productResponse>>() {
    };
  }
}
