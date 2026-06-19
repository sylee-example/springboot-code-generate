package com.example.odata.generated.controller;

import com.example.odata.generated.dto.OrderRequest;
import com.example.odata.generated.dto.OrderResponse;
import com.example.odata.generated.service.OrderService;
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
 * Order REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<List<OrderResponse>> list(@RequestParam(required = false) String expand) {
    return ResponseEntity.ok(orderService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<OrderResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(orderService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
    return ResponseEntity.ok(orderService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<OrderResponse> update(@PathVariable Integer key,
      @RequestBody OrderRequest request) {
    return ResponseEntity.ok(orderService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    orderService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
