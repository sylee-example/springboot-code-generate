package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 요청 DTO: Alphabetical_list_of_product
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@AllArgsConstructor
public class Alphabetical_list_of_productRequest {
  private Integer SupplierID;

  private Integer CategoryID;

  private String QuantityPerUnit;

  private BigDecimal UnitPrice;

  private Short UnitsInStock;

  private Short UnitsOnOrder;

  private Short ReorderLevel;
}
