package com.springboot.api.external.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.api.config.RedisConfig;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisHandler redisHandler;
  private final RedisConfig redisConfig;

  public int setSingleData(String key, Object value) {
    return redisHandler.executeOperation(() -> redisHandler.getValueOperations().set(key, value));
  }

  public int setSingleData(String key, Object value, Duration duration) {
    return redisHandler.executeOperation(
        () -> redisHandler.getValueOperations().set(key, value, duration));
  }

  public <T> T getSingleData(String key, Class<T> clazz) {
    Object value = redisHandler.getValueOperations().get(key);
    if (value instanceof String) {
      try {
        return new ObjectMapper().readValue((String) value, clazz);
      } catch (Exception e) {
        throw new RuntimeException("역직렬화 실패", e);
      }
    }
    return clazz.cast(value);
  }

  public int deleteSingleData(String key) {
    return redisHandler.executeOperation(() -> redisConfig.redisTemplate().delete(key));
  }

}
