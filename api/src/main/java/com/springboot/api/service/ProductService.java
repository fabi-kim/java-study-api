package com.springboot.api.service;

import com.springboot.api.data.dto.ProductDto;
import com.springboot.api.data.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {

  ProductResponseDto getProductById(Long number);

  List<ProductResponseDto> getProductsByName(String name);

  ProductResponseDto saveProduct(ProductDto productDto);

  ProductResponseDto changeProductName(Long number, String name);

  void deleteProduct(Long number) throws Exception;
}
