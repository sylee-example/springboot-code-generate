package com.example.odata.generated.service;

import com.example.odata.generated.dto.CategoryRequest;
import com.example.odata.generated.dto.CategoryResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Category OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class CategoryService extends AbstractODataService<CategoryResponse, CategoryRequest, Integer> {
  private static final String ENTITY_SET = "Categories";

  public CategoryService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<CategoryResponse> responseType() {
    return CategoryResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<CategoryResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<CategoryResponse>>() {
    };
  }
}
