package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Sales_Totals_by_AmountRequest;
import com.example.odata.generated.dto.Sales_Totals_by_AmountResponse;
import com.example.odata.generated.service.Sales_Totals_by_AmountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sales_Totals_by_Amount REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Sales_Totals_by_Amounts")
@RequiredArgsConstructor
public class Sales_Totals_by_AmountController {
  private final Sales_Totals_by_AmountService sales_Totals_by_AmountService;

  @GetMapping
  public ResponseEntity<List<Sales_Totals_by_AmountResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(sales_Totals_by_AmountService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Sales_Totals_by_AmountResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(sales_Totals_by_AmountService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Sales_Totals_by_AmountResponse> create(
      @RequestBody Sales_Totals_by_AmountRequest request) {
    return ResponseEntity.ok(sales_Totals_by_AmountService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Sales_Totals_by_AmountResponse> update(@PathVariable String key,
      @RequestBody Sales_Totals_by_AmountRequest request) {
    return ResponseEntity.ok(sales_Totals_by_AmountService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    sales_Totals_by_AmountService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
