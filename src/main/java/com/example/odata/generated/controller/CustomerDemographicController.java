package com.example.odata.generated.controller;

import com.example.odata.generated.dto.CustomerDemographicRequest;
import com.example.odata.generated.dto.CustomerDemographicResponse;
import com.example.odata.generated.service.CustomerDemographicService;
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
 * CustomerDemographic REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/CustomerDemographics")
@RequiredArgsConstructor
public class CustomerDemographicController {
  private final CustomerDemographicService customerDemographicService;

  @GetMapping
  public ResponseEntity<List<CustomerDemographicResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(customerDemographicService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<CustomerDemographicResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(customerDemographicService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<CustomerDemographicResponse> create(
      @RequestBody CustomerDemographicRequest request) {
    return ResponseEntity.ok(customerDemographicService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<CustomerDemographicResponse> update(@PathVariable String key,
      @RequestBody CustomerDemographicRequest request) {
    return ResponseEntity.ok(customerDemographicService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    customerDemographicService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
