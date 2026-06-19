package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Order_SubtotalRequest;
import com.example.odata.generated.dto.Order_SubtotalResponse;
import com.example.odata.generated.service.Order_SubtotalService;
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
 * Order_Subtotal REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Order_Subtotals")
@RequiredArgsConstructor
public class Order_SubtotalController {
  private final Order_SubtotalService order_SubtotalService;

  @GetMapping
  public ResponseEntity<List<Order_SubtotalResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(order_SubtotalService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Order_SubtotalResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(order_SubtotalService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Order_SubtotalResponse> create(@RequestBody Order_SubtotalRequest request) {
    return ResponseEntity.ok(order_SubtotalService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Order_SubtotalResponse> update(@PathVariable Integer key,
      @RequestBody Order_SubtotalRequest request) {
    return ResponseEntity.ok(order_SubtotalService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    order_SubtotalService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
