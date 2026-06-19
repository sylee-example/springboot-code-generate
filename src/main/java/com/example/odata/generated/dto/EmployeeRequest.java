package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 요청 DTO: Employee
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@AllArgsConstructor
public class EmployeeRequest {
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
}
