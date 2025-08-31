package com.springboot.api.controller;

import com.springboot.api.config.annotation.RedissonLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 예제 12.6
@RestController
@RequestMapping("/test/graceful-shutdown")
public class GracefullShutdownController {

  private final Logger logger = LoggerFactory.getLogger(GracefullShutdownController.class);

  @RedissonLock(value = "#id", error = "이미 요청되었습니다.")
  @GetMapping
  public String test(String id) throws InterruptedException {

    logger.info("-- graceful-shutdown test start --");
    Thread.sleep(8000);
    logger.info("-- graceful-shutdown test end --");
    return "ok";
  }

}
