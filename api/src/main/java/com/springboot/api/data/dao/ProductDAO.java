package com.springboot.api.data.dao;

import com.springboot.api.data.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDAO {

  Product insertProduct(Product product);

  Product selectProduct(Long id);

  List<Product> selectProductsByName(String name);

  Page<Product> selectProducts(Pageable page);

  List<Object[]> selectProductPartial(String name);

  Product updateProductName(Long id, String name) throws Exception;

  void deleteProduct(Long id) throws Exception;
}
