package com.example.odata.generated.service;

import com.example.odata.generated.dto.Summary_of_Sales_by_QuarterRequest;
import com.example.odata.generated.dto.Summary_of_Sales_by_QuarterResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Summary_of_Sales_by_Quarter OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Summary_of_Sales_by_QuarterService extends AbstractODataService<Summary_of_Sales_by_QuarterResponse, Summary_of_Sales_by_QuarterRequest, Integer> {
  private static final String ENTITY_SET = "Summary_of_Sales_by_Quarters";

  public Summary_of_Sales_by_QuarterService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Summary_of_Sales_by_QuarterResponse> responseType() {
    return Summary_of_Sales_by_QuarterResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Summary_of_Sales_by_QuarterResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Summary_of_Sales_by_QuarterResponse>>() {
    };
  }
}
