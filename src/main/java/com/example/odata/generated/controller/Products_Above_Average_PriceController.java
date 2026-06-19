package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Products_Above_Average_PriceRequest;
import com.example.odata.generated.dto.Products_Above_Average_PriceResponse;
import com.example.odata.generated.service.Products_Above_Average_PriceService;
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
 * Products_Above_Average_Price REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Products_Above_Average_Prices")
@RequiredArgsConstructor
public class Products_Above_Average_PriceController {
  private final Products_Above_Average_PriceService products_Above_Average_PriceService;

  @GetMapping
  public ResponseEntity<List<Products_Above_Average_PriceResponse>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(products_Above_Average_PriceService.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Products_Above_Average_PriceResponse> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(products_Above_Average_PriceService.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Products_Above_Average_PriceResponse> create(
      @RequestBody Products_Above_Average_PriceRequest request) {
    return ResponseEntity.ok(products_Above_Average_PriceService.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Products_Above_Average_PriceResponse> update(@PathVariable String key,
      @RequestBody Products_Above_Average_PriceRequest request) {
    return ResponseEntity.ok(products_Above_Average_PriceService.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    products_Above_Average_PriceService.delete(key);
    return ResponseEntity.noContent().build();
  }
}
