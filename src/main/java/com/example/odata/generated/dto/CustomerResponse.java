package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Customer
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "CustomerID"
)
@AllArgsConstructor
public class CustomerResponse {
  private String CustomerID;

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

  /**
   * 관계 ($expand=Orders 시 채워짐)
   */
  private List<OrderResponse> Orders;

  /**
   * 관계 ($expand=CustomerDemographics 시 채워짐)
   */
  private List<CustomerDemographicResponse> CustomerDemographics;
}
