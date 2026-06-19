package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Customer_and_Suppliers_by_CityRequest;
import com.example.odata.generated.dto.Customer_and_Suppliers_by_CityResponse;
import com.example.odata.generated.service.Customer_and_Suppliers_by_CityService;
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
 * Customer_and_Suppliers_by_City REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Customer_and_Suppliers_by_Cities")
@RequiredArgsConstructor
public class Customer_and_Suppliers_by_CityController {
  private final Customer_and_Suppliers_by_CityService customer_and_Suppliers_by_CityService;

  @GetMapping
  public ResponseEntity<List<Customer_and_Suppliers_by_CityResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(customer_and_Suppliers_by_CityService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Customer_and_Suppliers_by_CityResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(customer_and_Suppliers_by_CityService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Customer_and_Suppliers_by_CityResponse> create(
      @RequestBody Customer_and_Suppliers_by_CityRequest request) {
    return ResponseEntity.ok(customer_and_Suppliers_by_CityService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Customer_and_Suppliers_by_CityResponse> update(@PathVariable String key,
      @RequestBody Customer_and_Suppliers_by_CityRequest request) {
    return ResponseEntity.ok(customer_and_Suppliers_by_CityService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    customer_and_Suppliers_by_CityService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
