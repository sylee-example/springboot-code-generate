package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Employee
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "EmployeeID"
)
@AllArgsConstructor
public class EmployeeResponse {
  private Integer EmployeeID;

  private String LastName;

  private String FirstName;

  private String Title;

  private String TitleOfCourtesy;

  private OffsetDateTime BirthDate;

  private OffsetDateTime HireDate;

  private String Address;

  private String City;

  private String Region;

  private String PostalCode;

  private String Country;

  private String HomePhone;

  private String Extension;

  private byte[] Photo;

  private String Notes;

  private Integer ReportsTo;

  private String PhotoPath;

  /**
   * 관계 ($expand=Employees1 시 채워짐)
   */
  private List<EmployeeResponse> Employees1;

  /**
   * 관계 ($expand=Employee1 시 채워짐)
   */
  private EmployeeResponse Employee1;

  /**
   * 관계 ($expand=Orders 시 채워짐)
   */
  private List<OrderResponse> Orders;

  /**
   * 관계 ($expand=Territories 시 채워짐)
   */
  private List<TerritoryResponse> Territories;
}
