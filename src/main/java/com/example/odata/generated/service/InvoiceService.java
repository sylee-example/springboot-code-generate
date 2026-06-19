package com.example.odata.generated.service;

import com.example.odata.generated.dto.InvoiceRequest;
import com.example.odata.generated.dto.InvoiceResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Invoice OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class InvoiceService extends AbstractODataService<InvoiceResponse, InvoiceRequest, String> {
  private static final String ENTITY_SET = "Invoices";

  public InvoiceService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<InvoiceResponse> responseType() {
    return InvoiceResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<InvoiceResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<InvoiceResponse>>() {
    };
  }
}
