package com.example.odata.generated.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OData 요청 DTO: Invoice
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
@AllArgsConstructor
public class InvoiceRequest {
  private String ShipName;

  private String ShipAddress;

  private String ShipCity;

  private String ShipRegion;

  private String ShipPostalCode;

  private String ShipCountry;

  private String CustomerID;

  private String Address;

  private String City;

  private String Region;

  private String PostalCode;

  private String Country;

  private OffsetDateTime OrderDate;

  private OffsetDateTime RequiredDate;

  private OffsetDateTime ShippedDate;

  private BigDecimal ExtendedPrice;

  private BigDecimal Freight;
}
