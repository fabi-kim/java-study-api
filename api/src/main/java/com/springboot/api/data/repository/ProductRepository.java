package com.springboot.api.data.repository;

import com.springboot.api.data.entity.Product;
import com.springboot.api.data.repository.support.ProductRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

  List<Product> findByNameOrderByNumberAsc(String name);

  List<Product> findByName(String name);

  @Query("select p from Product p where p.name = :name")
  List<Product> findByNameUsingRawQuery(String name);

  @Query("select p.name, p.price, p.stock from Product p where p.name = :name")
  List<Object[]> findByNamePartials(@Param("name") String name);
}
