package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 응답 DTO: Invoice
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
public class InvoiceResponse {
  private String ShipName;

  private String ShipAddress;

  private String ShipCity;

  private String ShipRegion;

  private String ShipPostalCode;

  private String ShipCountry;

  private String CustomerID;

  private String CustomerName;

  private String Address;

  private String City;

  private String Region;

  private String PostalCode;

  private String Country;

  private String Salesperson;

  private Integer OrderID;

  private OffsetDateTime OrderDate;

  private OffsetDateTime RequiredDate;

  private OffsetDateTime ShippedDate;

  private String ShipperName;

  private Integer ProductID;

  private String ProductName;

  private BigDecimal UnitPrice;

  private Short Quantity;

  private Float Discount;

  private BigDecimal ExtendedPrice;

  private BigDecimal Freight;
}
