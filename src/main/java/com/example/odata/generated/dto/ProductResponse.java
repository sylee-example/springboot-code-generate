package com.example.odata.generated.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 조회 응답 DTO (resultMap: productResultMap)
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
  /**
   * 컬럼 PRODUCT_ID
   */
  private Long productId;

  /**
   * 컬럼 PRODUCT_NAME
   */
  private String productName;

  /**
   * 컬럼 CATEGORY
   */
  private String category;

  /**
   * 컬럼 PRICE
   */
  private BigDecimal price;

  /**
   * 컬럼 STATUS
   */
  private String status;
}
