package com.springboot.api.data.repository.support;

import com.springboot.api.data.entity.Product;
import java.util.List;

public interface ProductRepositoryCustom {

  List<Product> findByName(String name);
}
