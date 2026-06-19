package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Order
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "OrderID"
)
@AllArgsConstructor
public class OrderResponse {
  private Integer OrderID;

  private String CustomerID;

  private Integer EmployeeID;

  private OffsetDateTime OrderDate;

  private OffsetDateTime RequiredDate;

  private OffsetDateTime ShippedDate;

  private Integer ShipVia;

  private BigDecimal Freight;

  private String ShipName;

  private String ShipAddress;

  private String ShipCity;

  private String ShipRegion;

  private String ShipPostalCode;

  private String ShipCountry;

  /**
   * 관계 ($expand=Customer 시 채워짐)
   */
  private CustomerResponse Customer;

  /**
   * 관계 ($expand=Employee 시 채워짐)
   */
  private EmployeeResponse Employee;

  /**
   * 관계 ($expand=Order_Details 시 채워짐)
   */
  private List<Order_DetailResponse> Order_Details;

  /**
   * 관계 ($expand=Shipper 시 채워짐)
   */
  private ShipperResponse Shipper;
}
