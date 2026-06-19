package com.example.odata.generated.controller;

import com.example.odata.generated.dto.CategoryRequest;
import com.example.odata.generated.dto.CategoryResponse;
import com.example.odata.generated.service.CategoryService;
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
 * Category REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(categoryService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<CategoryResponse> getByKey(@PathVariable Integer key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(categoryService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
    return ResponseEntity.ok(categoryService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<CategoryResponse> update(@PathVariable Integer key,
      @RequestBody CategoryRequest request) {
    return ResponseEntity.ok(categoryService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable Integer key) {
    categoryService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
