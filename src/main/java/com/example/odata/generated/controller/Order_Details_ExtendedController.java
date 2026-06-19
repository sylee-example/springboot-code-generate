package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Order_Details_ExtendedRequest;
import com.example.odata.generated.dto.Order_Details_ExtendedResponse;
import com.example.odata.generated.service.Order_Details_ExtendedService;
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
 * Order_Details_Extended REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Order_Details_Extendeds")
@RequiredArgsConstructor
public class Order_Details_ExtendedController {
  private final Order_Details_ExtendedService order_Details_ExtendedService;

  @GetMapping
  public ResponseEntity<List<Order_Details_ExtendedResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(order_Details_ExtendedService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Order_Details_ExtendedResponse> getByKey(@PathVariable Float key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(order_Details_ExtendedService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Order_Details_ExtendedResponse> create(
      @RequestBody Order_Details_ExtendedRequest request) {
    return ResponseEntity.ok(order_Details_ExtendedService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Order_Details_ExtendedResponse> update(@PathVariable Float key,
      @RequestBody Order_Details_ExtendedRequest request) {
    return ResponseEntity.ok(order_Details_ExtendedService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Float key) {
    order_Details_ExtendedService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
