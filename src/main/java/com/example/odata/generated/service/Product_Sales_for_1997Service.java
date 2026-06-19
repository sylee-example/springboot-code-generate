package com.example.odata.generated.service;

import com.example.odata.generated.dto.Product_Sales_for_1997Request;
import com.example.odata.generated.dto.Product_Sales_for_1997Response;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Product_Sales_for_1997 OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Product_Sales_for_1997Service extends AbstractODataService<Product_Sales_for_1997Response, Product_Sales_for_1997Request, String> {
  private static final String ENTITY_SET = "Product_Sales_for_1997";

  public Product_Sales_for_1997Service(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Product_Sales_for_1997Response> responseType() {
    return Product_Sales_for_1997Response.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Product_Sales_for_1997Response>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Product_Sales_for_1997Response>>() {
    };
  }
}
