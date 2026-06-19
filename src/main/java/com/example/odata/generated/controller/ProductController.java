package com.example.odata.generated.controller;

import com.example.odata.generated.dto.ProductRequest;
import com.example.odata.generated.dto.ProductResponse;
import com.example.odata.generated.service.ProductService;
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
 * Product REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponse>> list(@RequestParam(required = false) String expand) {
    return ResponseEntity.ok(productService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<ProductResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(productService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
    return ResponseEntity.ok(productService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<ProductResponse> update(@PathVariable Integer key,
      @RequestBody ProductRequest request) {
    return ResponseEntity.ok(productService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    productService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
