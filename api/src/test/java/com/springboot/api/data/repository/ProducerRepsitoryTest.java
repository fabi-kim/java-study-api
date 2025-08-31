package com.springboot.api.data.repository;

import com.springboot.api.data.entity.Producer;
import com.springboot.api.data.entity.Product;
import jakarta.transaction.Transactional;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProducerRepsitoryTest {

  @Autowired
  private ProducerRepository producerRepository;

  @Autowired
  private ProductRepository productRepository;

  @Test
  @Transactional
  void relationshipTest() {
    Product product1 = saveProduct("펜1", 500, 1000);
    Product product2 = saveProduct("펜2", 500, 1000);
    Product product3 = saveProduct("펜3", 500, 1000);

    Producer producer1 = saveProducer("업체1");
    Producer producer2 = saveProducer("업체2");

    producer1.addProduct(product1);
    producer1.addProduct(product2);

    producer2.addProduct(product2);
    producer2.addProduct(product3);

    producerRepository.saveAll(Lists.newArrayList(producer1, producer2));

    System.out.println(producerRepository.findById(producer1.getId()).get().getProducts());

  }


  @Test
  @Transactional
  void relationshipTest2() {
    Product product1 = saveProduct("펜1", 500, 1000);
    Product product2 = saveProduct("펜2", 500, 1000);
    Product product3 = saveProduct("펜3", 500, 1000);

    Producer producer1 = saveProducer("업체1");
    Producer producer2 = saveProducer("업체2");

    producer1.addProduct(product1);
    producer1.addProduct(product2);
    producer2.addProduct(product2);
    producer2.addProduct(product3);

    product1.addProducer(producer1);
    product2.addProducer(producer1);
    product2.addProducer(producer2);
    product3.addProducer(producer2);

    producerRepository.saveAll(Lists.newArrayList(producer1, producer2));
    List<Product> products = productRepository.saveAll(
        Lists.newArrayList(product1, product2, product3));

    System.out.println(products);
    //System.out.println("products : " + producerRepository.findById(1L).get().getProducts());

    System.out.println(
        "producers : " + productRepository.findById(products.get(0).getNumber()).get()
            .getProducers());
  }

  private Product saveProduct(String name, int price, int stock) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setStock(stock);

    return productRepository.save(product);
  }

  private Producer saveProducer(String name) {
    Producer producer = new Producer();
    producer.setName(name);

    return producerRepository.save(producer);
  }
}
