package com.example.odata.generated.service;

import com.example.odata.generated.dto.Customer_and_Suppliers_by_CityRequest;
import com.example.odata.generated.dto.Customer_and_Suppliers_by_CityResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Customer_and_Suppliers_by_City OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Customer_and_Suppliers_by_CityService extends AbstractODataService<Customer_and_Suppliers_by_CityResponse, Customer_and_Suppliers_by_CityRequest, String> {
  private static final String ENTITY_SET = "Customer_and_Suppliers_by_Cities";

  public Customer_and_Suppliers_by_CityService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Customer_and_Suppliers_by_CityResponse> responseType() {
    return Customer_and_Suppliers_by_CityResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Customer_and_Suppliers_by_CityResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Customer_and_Suppliers_by_CityResponse>>() {
    };
  }
}
