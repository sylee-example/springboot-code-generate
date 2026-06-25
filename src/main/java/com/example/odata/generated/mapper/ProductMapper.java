package com.example.odata.generated.mapper;

import com.example.odata.generated.dto.InsertProductRequest;
import com.example.odata.generated.dto.ProductResponse;
import com.example.odata.generated.dto.SearchProductsRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ProductMapper Mapper — 생성 코드. XML namespace 와 바인딩.
 */
@Mapper
public interface ProductMapper {
  /**
   * <select id="searchProducts">
   */
  List<ProductResponse> searchProducts(SearchProductsRequest param);

  /**
   * <select id="findByStatus">
   */
  List<ProductResponse> findByStatus(@Param("status") String status);

  /**
   * <select id="findByIds">
   */
  ProductResponse findByIds(@Param("productIds") String productIds);

  /**
   * <select id="getProductById">
   */
  ProductResponse getProductById(@Param("productId") Long productId);

  /**
   * <insert id="insertProduct">
   */
  int insertProduct(InsertProductRequest param);
}
