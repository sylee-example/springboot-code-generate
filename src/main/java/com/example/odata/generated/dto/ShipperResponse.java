package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Shipper
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "ShipperID"
)
@AllArgsConstructor
public class ShipperResponse {
  private Integer ShipperID;

  private String CompanyName;

  private String Phone;

  /**
   * 관계 ($expand=Orders 시 채워짐)
   */
  private List<OrderResponse> Orders;
}
