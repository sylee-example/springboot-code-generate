package com.example.odata.generated.controller;

import com.example.odata.generated.dto.SupplierRequest;
import com.example.odata.generated.dto.SupplierResponse;
import com.example.odata.generated.service.SupplierService;
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
 * Supplier REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Suppliers")
@RequiredArgsConstructor
public class SupplierController {
  private final SupplierService supplierService;

  @GetMapping
  public ResponseEntity<List<SupplierResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(supplierService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<SupplierResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(supplierService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<SupplierResponse> create(@RequestBody SupplierRequest request) {
    return ResponseEntity.ok(supplierService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<SupplierResponse> update(@PathVariable Integer key,
      @RequestBody SupplierRequest request) {
    return ResponseEntity.ok(supplierService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    supplierService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
