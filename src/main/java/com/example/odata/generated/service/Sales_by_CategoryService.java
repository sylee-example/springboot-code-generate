package com.example.odata.generated.service;

import com.example.odata.generated.dto.Sales_by_CategoryRequest;
import com.example.odata.generated.dto.Sales_by_CategoryResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Sales_by_Category OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Sales_by_CategoryService extends AbstractODataService<Sales_by_CategoryResponse, Sales_by_CategoryRequest, Integer> {
  private static final String ENTITY_SET = "Sales_by_Categories";

  public Sales_by_CategoryService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Sales_by_CategoryResponse> responseType() {
    return Sales_by_CategoryResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Sales_by_CategoryResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Sales_by_CategoryResponse>>() {
    };
  }
}
