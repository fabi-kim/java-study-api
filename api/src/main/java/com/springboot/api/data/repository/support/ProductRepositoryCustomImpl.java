package com.springboot.api.data.repository.support;

import com.springboot.api.data.entity.Product;
import com.springboot.api.data.entity.QProduct;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements
    ProductRepositoryCustom {

  public ProductRepositoryCustomImpl() {
    super(Product.class);
  }

  @Override
  public List<Product> findByName(String name) {
    QProduct qProduct = QProduct.product;

    return from(qProduct)
        .where(qProduct.name.eq(name))
        .select(qProduct)
        .fetch();

  }
}
