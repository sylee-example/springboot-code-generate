package com.example.odata.generated.service;

import com.example.odata.generated.dto.Sales_Totals_by_AmountRequest;
import com.example.odata.generated.dto.Sales_Totals_by_AmountResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Sales_Totals_by_Amount OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Sales_Totals_by_AmountService extends AbstractODataService<Sales_Totals_by_AmountResponse, Sales_Totals_by_AmountRequest, String> {
  private static final String ENTITY_SET = "Sales_Totals_by_Amounts";

  public Sales_Totals_by_AmountService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Sales_Totals_by_AmountResponse> responseType() {
    return Sales_Totals_by_AmountResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Sales_Totals_by_AmountResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Sales_Totals_by_AmountResponse>>() {
    };
  }
}
