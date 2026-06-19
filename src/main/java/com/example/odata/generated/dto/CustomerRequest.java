package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 요청 DTO: Customer
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@AllArgsConstructor
public class CustomerRequest {
  private String CompanyName;

  private String ContactName;

  private String ContactTitle;

  private String Address;

  private String City;

  private String Region;

  private String PostalCode;

  private String Country;

  private String Phone;

  private String Fax;
}
