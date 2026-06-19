package com.example.odata.generated.service;

import com.example.odata.generated.dto.Products_by_CategoryRequest;
import com.example.odata.generated.dto.Products_by_CategoryResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Products_by_Category OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Products_by_CategoryService extends AbstractODataService<Products_by_CategoryResponse, Products_by_CategoryRequest, String> {
  private static final String ENTITY_SET = "Products_by_Categories";

  public Products_by_CategoryService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Products_by_CategoryResponse> responseType() {
    return Products_by_CategoryResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Products_by_CategoryResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Products_by_CategoryResponse>>() {
    };
  }
}
