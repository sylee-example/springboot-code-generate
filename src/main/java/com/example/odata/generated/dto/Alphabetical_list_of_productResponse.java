package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Alphabetical_list_of_product
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
public class Alphabetical_list_of_productResponse {
  private Integer ProductID;

  private String ProductName;

  private Integer SupplierID;

  private Integer CategoryID;

  private String QuantityPerUnit;

  private BigDecimal UnitPrice;

  private Short UnitsInStock;

  private Short UnitsOnOrder;

  private Short ReorderLevel;

  private Boolean Discontinued;

  private String CategoryName;
}
