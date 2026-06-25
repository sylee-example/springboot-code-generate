package com.example.odata.generated.controller;

import com.example.odata.generated.dto.InsertProductRequest;
import com.example.odata.generated.dto.ProductResponse;
import com.example.odata.generated.dto.SearchProductsRequest;
import com.example.odata.generated.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Product REST Controller — 생성 코드.
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/searchProducts")
  public ResponseEntity<List<ProductResponse>> searchProducts(
      @ModelAttribute SearchProductsRequest param) {
    return ResponseEntity.ok(productService.searchProducts(param));
  }

  @GetMapping("/findByStatus")
  public ResponseEntity<List<ProductResponse>> findByStatus(@RequestParam("status") String status) {
    return ResponseEntity.ok(productService.findByStatus(status));
  }

  @GetMapping("/findByIds")
  public ResponseEntity<ProductResponse> findByIds(@RequestParam("productIds") String productIds) {
    return ResponseEntity.ok(productService.findByIds(productIds));
  }

  @GetMapping("/getProductById")
  public ResponseEntity<ProductResponse> getProductById(@RequestParam("productId") Long productId) {
    return ResponseEntity.ok(productService.getProductById(productId));
  }

  @PostMapping("/insertProduct")
  public ResponseEntity<Integer> insertProduct(@RequestBody InsertProductRequest param) {
    return ResponseEntity.ok(productService.insertProduct(param));
  }
}
