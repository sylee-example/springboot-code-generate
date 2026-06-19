package com.example.odata.generated.service;

import com.example.odata.generated.dto.Current_Product_ListRequest;
import com.example.odata.generated.dto.Current_Product_ListResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Current_Product_List OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class Current_Product_ListService extends AbstractODataService<Current_Product_ListResponse, Current_Product_ListRequest, Integer> {
  private static final String ENTITY_SET = "Current_Product_Lists";

  public Current_Product_ListService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<Current_Product_ListResponse> responseType() {
    return Current_Product_ListResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<Current_Product_ListResponse>> collectionType(
      ) {
    return new ParameterizedTypeReference<ODataCollection<Current_Product_ListResponse>>() {
    };
  }
}
