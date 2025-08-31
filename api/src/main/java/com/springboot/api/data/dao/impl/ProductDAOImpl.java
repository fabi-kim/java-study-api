package com.springboot.api.data.dao.impl;

import com.springboot.api.data.dao.ProductDAO;
import com.springboot.api.data.entity.Product;
import com.springboot.api.data.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductDAOImpl implements ProductDAO {


  private final ProductRepository productRepository;

  @Autowired
  public ProductDAOImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product insertProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product selectProduct(Long id) {
    return productRepository.findById(id).orElseThrow();
  }

  @Override
  public List<Product> selectProductsByName(String name) {
    return productRepository.findByNameOrderByNumberAsc(name);
  }

  @Override
  public List<Object[]> selectProductPartial(String name) {
    return productRepository.findByNamePartials(name);
  }

  @Override
  public Page<Product> selectProducts(Pageable page) {
    return productRepository.findAll(page);
  }


  @Override
  public Product updateProductName(Long id, String name) throws Exception {
    Optional<Product> selectedProduct = productRepository.findById(id);

    if (selectedProduct.isPresent()) {
      Product product = selectedProduct.get();
      product.setName(name);

      return productRepository.save(product);
    } else {
      throw new Exception();
    }

  }

  @Override
  public void deleteProduct(Long id) throws Exception {
    Optional<Product> selectedProduct = productRepository.findById(id);

    if (selectedProduct.isPresent()) {
      Product product = selectedProduct.get();
      productRepository.delete(product);
    } else {
      throw new Exception();
    }
  }
}
