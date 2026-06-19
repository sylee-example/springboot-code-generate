package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Summary_of_Sales_by_YearRequest;
import com.example.odata.generated.dto.Summary_of_Sales_by_YearResponse;
import com.example.odata.generated.service.Summary_of_Sales_by_YearService;
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
 * Summary_of_Sales_by_Year REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Summary_of_Sales_by_Years")
@RequiredArgsConstructor
public class Summary_of_Sales_by_YearController {
  private final Summary_of_Sales_by_YearService summary_of_Sales_by_YearService;

  @GetMapping
  public ResponseEntity<List<Summary_of_Sales_by_YearResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(summary_of_Sales_by_YearService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Summary_of_Sales_by_YearResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(summary_of_Sales_by_YearService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Summary_of_Sales_by_YearResponse> create(
      @RequestBody Summary_of_Sales_by_YearRequest request) {
    return ResponseEntity.ok(summary_of_Sales_by_YearService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Summary_of_Sales_by_YearResponse> update(@PathVariable Integer key,
      @RequestBody Summary_of_Sales_by_YearRequest request) {
    return ResponseEntity.ok(summary_of_Sales_by_YearService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    summary_of_Sales_by_YearService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
