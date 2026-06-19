package com.example.odata.generated.service;

import com.example.odata.generated.dto.SupplierRequest;
import com.example.odata.generated.dto.SupplierResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Supplier OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class SupplierService extends AbstractODataService<SupplierResponse, SupplierRequest, Integer> {
  private static final String ENTITY_SET = "Suppliers";

  public SupplierService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<SupplierResponse> responseType() {
    return SupplierResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<SupplierResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<SupplierResponse>>() {
    };
  }
}
