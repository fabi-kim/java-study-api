package com.springboot.api.config.annotation.aspect;

import com.springboot.api.common.Constants.ExceptionClass;
import com.springboot.api.common.exception.CustomException;
import com.springboot.api.config.annotation.RedissonLock;
import com.springboot.api.config.parser.CustomSpringELParser;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {

  private final Logger logger = LoggerFactory.getLogger(RedissonLockAspect.class);
  private final RedissonClient redissonClient;
  private final AopForTransaction aopForTransaction;

  @Around("@annotation(com.springboot.api.config.annotation.RedissonLock)")
  public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    RedissonLock annotation = method.getAnnotation(RedissonLock.class);
    String lockKey =
        method.getName() + CustomSpringELParser.getDynamicValue(signature.getParameterNames(),
            joinPoint.getArgs(), annotation.value());

    RLock lock = redissonClient.getLock(lockKey);
    String errMsg = Optional.ofNullable(annotation.error())
        .filter(s -> !s.isBlank())
        .orElse("잠시후 다시 시도하세요.");
    boolean locked = false;
    try {
      locked = lock.tryLock(annotation.waitTime(), annotation.leaseTime(),
          TimeUnit.MILLISECONDS);
      if (!locked) {
        logger.info("Lock 획득 실패={}", lockKey);
        throw new CustomException(ExceptionClass.LOCK, HttpStatus.TOO_MANY_REQUESTS, errMsg);
      }
      logger.info("로직 수행");
      return aopForTransaction.proceed(joinPoint);
    } catch (InterruptedException e) {
      logger.info("에러 발생");
      throw e;
    } finally {
      try {
        lock.unlock();   // (4)
      } catch (IllegalMonitorStateException e) {
        logger.info("Redisson Lock Already UnLock {} {}", method.getName(), lockKey);
      }
    }
  }
}
