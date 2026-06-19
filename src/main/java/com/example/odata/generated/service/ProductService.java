package com.example.odata.generated.service;

import com.example.odata.generated.dto.ProductRequest;
import com.example.odata.generated.dto.ProductResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Product OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class ProductService extends AbstractODataService<ProductResponse, ProductRequest, Integer> {
  private static final String ENTITY_SET = "Products";

  public ProductService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<ProductResponse> responseType() {
    return ProductResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<ProductResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<ProductResponse>>() {
    };
  }
}
