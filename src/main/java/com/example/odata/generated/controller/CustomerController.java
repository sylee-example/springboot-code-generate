package com.example.odata.generated.controller;

import com.example.odata.generated.dto.CustomerRequest;
import com.example.odata.generated.dto.CustomerResponse;
import com.example.odata.generated.service.CustomerService;
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
 * Customer REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Customers")
@RequiredArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(customerService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<CustomerResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(customerService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest request) {
    return ResponseEntity.ok(customerService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<CustomerResponse> update(@PathVariable String key,
      @RequestBody CustomerRequest request) {
    return ResponseEntity.ok(customerService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    customerService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
