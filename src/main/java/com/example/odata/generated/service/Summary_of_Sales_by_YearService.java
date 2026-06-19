package com.example.odata.generated.service;

import com.example.odata.generated.dto.Summary_of_Sales_by_YearRequest;
import com.example.odata.generated.dto.Summary_of_Sales_by_YearResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Summary_of_Sales_by_Year OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Summary_of_Sales_by_YearService extends AbstractODataService<Summary_of_Sales_by_YearResponse, Summary_of_Sales_by_YearRequest, Integer> {
  private static final String ENTITY_SET = "Summary_of_Sales_by_Years";

  public Summary_of_Sales_by_YearService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Summary_of_Sales_by_YearResponse> responseType() {
    return Summary_of_Sales_by_YearResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Summary_of_Sales_by_YearResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Summary_of_Sales_by_YearResponse>>() {
    };
  }
}
