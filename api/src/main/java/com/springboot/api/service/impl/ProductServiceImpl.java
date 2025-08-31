package com.springboot.api.service.impl;

import com.springboot.api.common.Constants.RedisKey;
import com.springboot.api.data.dao.ProductDAO;
import com.springboot.api.data.dto.ProductDto;
import com.springboot.api.data.dto.ProductResponseDto;
import com.springboot.api.data.entity.Product;
import com.springboot.api.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductDAO productDAO;


  @Autowired
  public ProductServiceImpl(ProductDAO productDAO) {
    this.productDAO = productDAO;
  }

  @Override
  @Cacheable(cacheNames = RedisKey.PRODUCT, key = "#number")
  public ProductResponseDto getProductById(Long number) {
    Product product = productDAO.selectProduct(number);

    return ProductResponseDto.builder()
        .name(product.getName())
        .number(product.getNumber())
        .price(product.getPrice())
        .stock(product.getStock())
        .build();
  }

  @Override
  public List<ProductResponseDto> getProductsByName(String name) {
    List<Product> products = productDAO.selectProductsByName(name);

    return products.stream().map(product -> {
      return ProductResponseDto.builder().name(product.getName()).number(product.getNumber())
          .stock(product.getStock()).price(product.getPrice()).build();
    }).toList();

  }


  public void getProducts() {
    Page<Product> products = productDAO.selectProducts(
        PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "number")));
    System.out.println(products.toString());
  }

  @Override
  public ProductResponseDto saveProduct(ProductDto productDto) {
    Product product = new Product();
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setStock(productDto.getStock());
    Product savedProduct = productDAO.insertProduct(product);

    return ProductResponseDto.builder()
        .name(savedProduct.getName())
        .number(savedProduct.getNumber())
        .price(savedProduct.getPrice())
        .stock(savedProduct.getStock())
        .build();

  }

  @Override
  public ProductResponseDto changeProductName(Long number, String name) {
    return null;
  }

  @Override
  public void deleteProduct(Long number) throws Exception {
    productDAO.deleteProduct(number);
  }
}
