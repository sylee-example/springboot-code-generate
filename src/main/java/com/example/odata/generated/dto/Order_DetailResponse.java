package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Order_Detail
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.IntSequenceGenerator.class,
    property = "@id"
)
@AllArgsConstructor
public class Order_DetailResponse {
  private Integer OrderID;

  private Integer ProductID;

  private BigDecimal UnitPrice;

  private Short Quantity;

  private Float Discount;

  /**
   * 관계 ($expand=Order 시 채워짐)
   */
  private OrderResponse Order;

  /**
   * 관계 ($expand=Product 시 채워짐)
   */
  private ProductResponse Product;
}
