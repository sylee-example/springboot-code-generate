package com.example.odata.generated.service;

import com.example.odata.generated.dto.CustomerRequest;
import com.example.odata.generated.dto.CustomerResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Customer OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class CustomerService extends AbstractODataService<CustomerResponse, CustomerRequest, String> {
  private static final String ENTITY_SET = "Customers";

  public CustomerService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<CustomerResponse> responseType() {
    return CustomerResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<CustomerResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<CustomerResponse>>() {
    };
  }
}
