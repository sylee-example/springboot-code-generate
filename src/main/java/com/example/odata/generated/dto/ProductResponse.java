package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Product
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "ProductID"
)
@AllArgsConstructor
public class ProductResponse {
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

  /**
   * 관계 ($expand=Category 시 채워짐)
   */
  private CategoryResponse Category;

  /**
   * 관계 ($expand=Order_Details 시 채워짐)
   */
  private List<Order_DetailResponse> Order_Details;

  /**
   * 관계 ($expand=Supplier 시 채워짐)
   */
  private SupplierResponse Supplier;
}
