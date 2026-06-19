package com.example.odata.generated.controller;

import com.example.odata.generated.dto.Product_Sales_for_1997Request;
import com.example.odata.generated.dto.Product_Sales_for_1997Response;
import com.example.odata.generated.service.Product_Sales_for_1997Service;
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
 * Product_Sales_for_1997 REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/Product_Sales_for_1997")
@RequiredArgsConstructor
public class Product_Sales_for_1997Controller {
  private final Product_Sales_for_1997Service product_Sales_for_1997Service;

  @GetMapping
  public ResponseEntity<List<Product_Sales_for_1997Response>> list(
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(product_Sales_for_1997Service.list(expand));
  }

  @GetMapping("/{key}")
  public ResponseEntity<Product_Sales_for_1997Response> getByKey(@PathVariable String key,
      @RequestParam(required = false) String expand) {
    return ResponseEntity.ok(product_Sales_for_1997Service.getByKey(key, expand));
  }

  @PostMapping
  public ResponseEntity<Product_Sales_for_1997Response> create(
      @RequestBody Product_Sales_for_1997Request request) {
    return ResponseEntity.ok(product_Sales_for_1997Service.create(request));
  }

  @PatchMapping("/{key}")
  public ResponseEntity<Product_Sales_for_1997Response> update(@PathVariable String key,
      @RequestBody Product_Sales_for_1997Request request) {
    return ResponseEntity.ok(product_Sales_for_1997Service.update(key, request));
  }

  @DeleteMapping("/{key}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    product_Sales_for_1997Service.delete(key);
    return ResponseEntity.noContent().build();
  }
}
