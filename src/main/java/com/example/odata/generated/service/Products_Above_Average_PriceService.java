package com.example.odata.generated.service;

import com.example.odata.generated.dto.Products_Above_Average_PriceRequest;
import com.example.odata.generated.dto.Products_Above_Average_PriceResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Products_Above_Average_Price OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Products_Above_Average_PriceService extends AbstractODataService<Products_Above_Average_PriceResponse, Products_Above_Average_PriceRequest, String> {
  private static final String ENTITY_SET = "Products_Above_Average_Prices";

  public Products_Above_Average_PriceService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Products_Above_Average_PriceResponse> responseType() {
    return Products_Above_Average_PriceResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Products_Above_Average_PriceResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Products_Above_Average_PriceResponse>>() {
    };
  }
}
