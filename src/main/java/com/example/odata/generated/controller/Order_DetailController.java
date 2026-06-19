package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Order_DetailRequest;
import com.example.odata.generated.dto.Order_DetailResponse;
import com.example.odata.generated.service.Order_DetailService;
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
 * Order_Detail REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Order_Details")
@RequiredArgsConstructor
public class Order_DetailController {
  private final Order_DetailService order_DetailService;

  @GetMapping
  public ResponseEntity<List<Order_DetailResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(order_DetailService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Order_DetailResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(order_DetailService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Order_DetailResponse> create(@RequestBody Order_DetailRequest request) {
    return ResponseEntity.ok(order_DetailService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Order_DetailResponse> update(@PathVariable Integer key,
      @RequestBody Order_DetailRequest request) {
    return ResponseEntity.ok(order_DetailService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    order_DetailService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
