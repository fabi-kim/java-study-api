package com.springboot.api.data.repository;

import com.springboot.api.data.entity.Product;
import com.springboot.api.data.entity.Provider;
import jakarta.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProviderRepositoryTest {


  @Autowired
  ProductRepository productRepository;

  @Autowired
  ProviderRepository providerRepository;

  @Test
  public void saveAndReadTest1() {
    Provider provider = new Provider();
    provider.setName("업체");

    providerRepository.save(provider);

    Product product = new Product();
    product.setName("test11");
    product.setPrice(500);
    product.setStock(10);
    product.setProvider(provider);

    productRepository.save(product);

    System.out.println(product);
    System.out.println(provider);

    System.out.println(productRepository.findById(product.getNumber()).get().getProvider());
  }


  @Test
  @Transactional
  public void saveAndReadTest2() {
    Provider provider = new Provider();
    provider.setName("업체");

    providerRepository.save(provider);

    Product product = new Product();
    product.setName("test11");
    product.setPrice(500);
    product.setStock(10);
    product.setProvider(provider);

    productRepository.save(product);

    Product product2 = new Product();
    product2.setName("test12");
    product2.setPrice(510);
    product2.setStock(20);
    product2.setProvider(provider);

    productRepository.save(product2);

    System.out.println(product);
    System.out.println(provider);

    System.out.println(providerRepository.findById(provider.getId()).get().getProductList());
  }

  @Test
  void cascadeTest() {
    Provider provider = savedProvider("새로운 공급업체");

    Product product1 = saveProduct("펜1", 500, 1000);
    Product product2 = saveProduct("펜2", 500, 1000);
    Product product3 = saveProduct("펜3", 500, 1000);

    product1.setProvider(provider);
    product2.setProvider(provider);
    product3.setProvider(provider);

    //provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

    providerRepository.save(provider);
  }

  @Test
  @Transactional
  void orpahnRemovalTest() {
    Provider provider = savedProvider("공급업체2");

    Product product1 = savedProduct("펜11", 500, 1000, provider);
    Product product2 = savedProduct("펜12", 500, 1000, provider);
    Product product3 = savedProduct("펜13", 500, 1000, provider);

    // 연관관계 설정
    product1.setProvider(provider);
    product2.setProvider(provider);
    product3.setProvider(provider);

    provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));
    providerRepository.saveAndFlush(provider);

    providerRepository.findAll().forEach(System.out::println);
    productRepository.findAll().forEach(System.out::println);

    Provider foundProvider = providerRepository.findById(provider.getId()).get();
    System.out.println(foundProvider);
    System.out.println("getProductLis:" + foundProvider.getProductList().get(0));

    foundProvider.getProductList().remove(0);

    providerRepository.findAll().forEach(System.out::println);
    productRepository.findAll().forEach(System.out::println);

  }

  private Provider savedProvider(String name) {
    Provider provider = new Provider();
    provider.setName(name);

    return provider;
  }


  private Product saveProduct(String name, int price, int stock) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setStock(stock);

    return productRepository.save(product);
  }


  private Product savedProduct(String name, int price, int stock, Provider provider) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setStock(stock);
    product.setProvider(provider);

    return product;
  }
}
