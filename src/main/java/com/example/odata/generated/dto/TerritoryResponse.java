package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Territory
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "TerritoryID"
)
@AllArgsConstructor
public class TerritoryResponse {
  private String TerritoryID;

  private String TerritoryDescription;

  private Integer RegionID;

  /**
   * 관계 ($expand=Region 시 채워짐)
   */
  private RegionResponse Region;

  /**
   * 관계 ($expand=Employees 시 채워짐)
   */
  private List<EmployeeResponse> Employees;
}
