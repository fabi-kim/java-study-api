package com.springboot.api.data.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto implements Serializable {

  private Long number;
  private String name;
  private int price;
  private int stock;

  public static ProductResponseDtoBuilder builder() {
    return new ProductResponseDtoBuilder();
  }

  public static class ProductResponseDtoBuilder {

    private Long number;
    private String name;
    private int price;
    private int stock;

    ProductResponseDtoBuilder() {
    }

    public ProductResponseDtoBuilder number(Long number) {
      this.number = number;
      return this;
    }

    public ProductResponseDtoBuilder name(String name) {
      this.name = name;
      return this;
    }

    public ProductResponseDtoBuilder price(int price) {
      this.price = price;
      return this;
    }

    public ProductResponseDtoBuilder stock(int stock) {
      this.stock = stock;
      return this;
    }

    public ProductResponseDto build() {
      return new ProductResponseDto(number, name, price, stock);
    }

    @Override
    public String toString() {
      return "ProductResponseDtoBuilder{" +
          "number=" + number +
          ", name='" + name + '\'' +
          ", price=" + price +
          ", stock=" + stock +
          '}';
    }
  }
}
