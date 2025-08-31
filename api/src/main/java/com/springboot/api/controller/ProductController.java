package com.springboot.api.controller;

import com.springboot.api.config.annotation.XAuthTokenHeader;
import com.springboot.api.data.dto.ProductDto;
import com.springboot.api.data.dto.ProductResponseDto;
import com.springboot.api.service.ProductService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@XAuthTokenHeader()
@RestController
@RequestMapping("/product")
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping()
  public ResponseEntity<ProductResponseDto> getProduct(Long number) {
    ProductResponseDto productResponseDto = productService.getProductById(number);
    return ResponseEntity.ok(productResponseDto);
  }

  @GetMapping("{name}")
  public ResponseEntity<List<ProductResponseDto>> getProducts(String name) {
    List<ProductResponseDto> productResponseDtos = productService.getProductsByName(name);
    return ResponseEntity.ok(productResponseDtos);
  }

  @PostMapping()
  public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto productDto) {
    ProductResponseDto productResponseDto = productService.saveProduct(productDto);
    logger.info(productResponseDto.toString());
    return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
  }
}
