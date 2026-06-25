package com.example.odata.generated.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 요청 DTO (statement: searchProducts)
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductsRequest {
  private String keyword;

  private String category;

  private String minPrice;
}
