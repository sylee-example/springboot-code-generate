package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Alphabetical_list_of_productRequest;
import com.example.odata.generated.dto.Alphabetical_list_of_productResponse;
import com.example.odata.generated.service.Alphabetical_list_of_productService;
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
 * Alphabetical_list_of_product REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Alphabetical_list_of_products")
@RequiredArgsConstructor
public class Alphabetical_list_of_productController {
  private final Alphabetical_list_of_productService alphabetical_list_of_productService;

  @GetMapping
  public ResponseEntity<List<Alphabetical_list_of_productResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(alphabetical_list_of_productService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Alphabetical_list_of_productResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(alphabetical_list_of_productService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Alphabetical_list_of_productResponse> create(
      @RequestBody Alphabetical_list_of_productRequest request) {
    return ResponseEntity.ok(alphabetical_list_of_productService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Alphabetical_list_of_productResponse> update(@PathVariable String key,
      @RequestBody Alphabetical_list_of_productRequest request) {
    return ResponseEntity.ok(alphabetical_list_of_productService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    alphabetical_list_of_productService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
