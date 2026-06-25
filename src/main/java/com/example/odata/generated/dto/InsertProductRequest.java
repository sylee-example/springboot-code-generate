package com.example.odata.generated.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 요청 DTO (statement: insertProduct)
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertProductRequest {
  private String productName;

  private String category;

  private String price;

  private String status;
}
