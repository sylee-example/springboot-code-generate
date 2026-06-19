package com.example.odata.generated.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 조회 응답 DTO (resultMap: userResultMap)
 * 생성 코드 — 수정하지 마세요.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  /**
   * 컬럼 USER_ID
   */
  private Long userId;

  /**
   * 컬럼 USER_NAME
   */
  private String userName;

  /**
   * 컬럼 EMAIL
   */
  private String email;

  /**
   * 컬럼 AGE
   */
  private Integer age;

  /**
   * 컬럼 CREATED_AT
   */
  private LocalDateTime createdAt;
}
