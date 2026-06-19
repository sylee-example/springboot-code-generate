package com.example.odata.generated.service;

import com.example.odata.generated.dto.EmployeeRequest;
import com.example.odata.generated.dto.EmployeeResponse;
import com.example.odata.support.AbstractODataService;
import com.example.odata.support.ODataCollection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Employee OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.
 */
@Service
public class EmployeeService extends AbstractODataService<EmployeeResponse, EmployeeRequest, Integer> {
  private static final String ENTITY_SET = "Employees";

  public EmployeeService(WebClient odataWebClient) {
    super(odataWebClient);
  }

  @Override
  protected String entitySet() {
    return ENTITY_SET;
  }

  @Override
  protected Class<EmployeeResponse> responseType() {
    return EmployeeResponse.class;
  }

  @Override
  protected ParameterizedTypeReference<ODataCollection<EmployeeResponse>> collectionType() {
    return new ParameterizedTypeReference<ODataCollection<EmployeeResponse>>() {
    };
  }
}
