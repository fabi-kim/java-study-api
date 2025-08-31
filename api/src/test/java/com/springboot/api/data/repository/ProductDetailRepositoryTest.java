package com.springboot.api.data.repository;

import com.springboot.api.data.entity.Product;
import com.springboot.api.data.entity.ProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductDetailRepositoryTest {


  @Autowired
  ProductRepository productRepository;

  @Autowired
  ProductDetailRepository productDetailRepository;

  @Test
  public void saveAndReadTest1() {
    Product product = new Product();
    product.setName("test11");
    product.setPrice(500);
    product.setStock(10);

    productRepository.save(product);

    ProductDetail productDetail = new ProductDetail();
    productDetail.setProduct(product);
    productDetail.setDescription("desc");

    productDetailRepository.save(productDetail);

    System.out.println(productDetailRepository.findById(productDetail.getId()).get().getProduct());

    System.out.println(productDetailRepository.findById(productDetail.getId()).get());
  }
}
