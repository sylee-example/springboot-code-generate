package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Products_by_CategoryRequest;
import com.example.odata.generated.dto.Products_by_CategoryResponse;
import com.example.odata.generated.service.Products_by_CategoryService;
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
 * Products_by_Category REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Products_by_Categories")
@RequiredArgsConstructor
public class Products_by_CategoryController {
  private final Products_by_CategoryService products_by_CategoryService;

  @GetMapping
  public ResponseEntity<List<Products_by_CategoryResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(products_by_CategoryService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Products_by_CategoryResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(products_by_CategoryService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Products_by_CategoryResponse> create(
      @RequestBody Products_by_CategoryRequest request) {
    return ResponseEntity.ok(products_by_CategoryService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Products_by_CategoryResponse> update(@PathVariable String key,
      @RequestBody Products_by_CategoryRequest request) {
    return ResponseEntity.ok(products_by_CategoryService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    products_by_CategoryService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
