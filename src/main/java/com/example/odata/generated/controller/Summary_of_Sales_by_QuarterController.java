package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Summary_of_Sales_by_QuarterRequest;
import com.example.odata.generated.dto.Summary_of_Sales_by_QuarterResponse;
import com.example.odata.generated.service.Summary_of_Sales_by_QuarterService;
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
 * Summary_of_Sales_by_Quarter REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Summary_of_Sales_by_Quarters")
@RequiredArgsConstructor
public class Summary_of_Sales_by_QuarterController {
  private final Summary_of_Sales_by_QuarterService summary_of_Sales_by_QuarterService;

  @GetMapping
  public ResponseEntity<List<Summary_of_Sales_by_QuarterResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(summary_of_Sales_by_QuarterService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Summary_of_Sales_by_QuarterResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(summary_of_Sales_by_QuarterService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Summary_of_Sales_by_QuarterResponse> create(
      @RequestBody Summary_of_Sales_by_QuarterRequest request) {
    return ResponseEntity.ok(summary_of_Sales_by_QuarterService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Summary_of_Sales_by_QuarterResponse> update(@PathVariable Integer key,
      @RequestBody Summary_of_Sales_by_QuarterRequest request) {
    return ResponseEntity.ok(summary_of_Sales_by_QuarterService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    summary_of_Sales_by_QuarterService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
