package com.example.odata.generated.service;

import com.example.odata.generated.dto.CustomerDemographicRequest;
import com.example.odata.generated.dto.CustomerDemographicResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * CustomerDemographic OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class CustomerDemographicService extends AbstractODataService<CustomerDemographicResponse, CustomerDemographicRequest, String> {
  private static final String ENTITY_SET = "CustomerDemographics";

  public CustomerDemographicService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<CustomerDemographicResponse> responseType() {
    return CustomerDemographicResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<CustomerDemographicResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<CustomerDemographicResponse>>() {
    };
  }
}
