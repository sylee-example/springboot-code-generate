package com.example.odata.generated.service;

import com.example.odata.generated.dto.InsertProductRequest;
import com.example.odata.generated.dto.ProductResponse;
import com.example.odata.generated.dto.SearchProductsRequest;
import com.example.odata.generated.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Product Service — 생성 코드. Mapper 위임.
 */
@Service
@RequiredArgsConstructor
@Transactional(
    readOnly = true
)
public class ProductService {
  private final ProductMapper productMapper;

  public List<ProductResponse> searchProducts(SearchProductsRequest param) {
    return productMapper.searchProducts(param);
  }

  public List<ProductResponse> findByStatus(String status) {
    return productMapper.findByStatus(status);
  }

  public ProductResponse findByIds(String productIds) {
    return productMapper.findByIds(productIds);
  }

  public ProductResponse getProductById(Long productId) {
    return productMapper.getProductById(productId);
  }

  @Transactional(
      rollbackFor = Exception.class
  )
  public int insertProduct(InsertProductRequest param) {
    return productMapper.insertProduct(param);
  }
}
