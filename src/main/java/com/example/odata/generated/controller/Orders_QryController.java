package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Orders_QryRequest;
import com.example.odata.generated.dto.Orders_QryResponse;
import com.example.odata.generated.service.Orders_QryService;
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
 * Orders_Qry REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Orders_Qries")
@RequiredArgsConstructor
public class Orders_QryController {
  private final Orders_QryService orders_QryService;

  @GetMapping
  public ResponseEntity<List<Orders_QryResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(orders_QryService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Orders_QryResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(orders_QryService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Orders_QryResponse> create(@RequestBody Orders_QryRequest request) {
    return ResponseEntity.ok(orders_QryService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Orders_QryResponse> update(@PathVariable String key,
      @RequestBody Orders_QryRequest request) {
    return ResponseEntity.ok(orders_QryService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    orders_QryService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
