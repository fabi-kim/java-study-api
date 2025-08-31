package com.springboot.api.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

public class ConfigProperties {

  @Getter
  @Setter
  @Component
  @ConfigurationProperties(prefix = "springboot.jwt")
  @Validated
  public static class JwtProperties {

    @NotBlank
    private String secret;
  }


  @Getter
  @Setter
  @Component
  @ConfigurationProperties(prefix = "spring.data.redis")
  @Validated
  public static class RedisProperties {

    @NotBlank
    private String host;

    @NumberFormat
    private int port;
  }
}
