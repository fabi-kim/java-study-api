package com.springboot.api.data.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.api.data.entity.Product;
import com.springboot.api.data.entity.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  ProductRepository productRepository;

  @Test
  void queryTest() {
    JPAQuery<Product> query = new JPAQuery<Product>(entityManager);
    QProduct qProduct = QProduct.product;

    List<Product> productList = query
        .from(qProduct)
        .where(qProduct.name.eq("펜"))
        .orderBy(qProduct.price.asc())
        .fetch();

    productList.forEach(System.out::println);
  }

  @Test
  void queryTest2() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    QProduct qProduct = QProduct.product;

    List<Product> productList = queryFactory
        .selectFrom(qProduct)
        .where(qProduct.name.eq("펜"))
        .orderBy(qProduct.price.asc())
        .fetch();
  }


  @Test
  void findByIdTest() {
    List<Product> productList = productRepository.findByName("펜");
  }

  @Test
  void auditingTest() {
    Product product = new Product();
    product.setName("펜");
    product.setPrice(1000);
    product.setStock(100);

    Product savedProduct = productRepository.save(product);

    System.out.println(savedProduct);
    System.out.println(savedProduct.getCreatedAt());
  }
}
