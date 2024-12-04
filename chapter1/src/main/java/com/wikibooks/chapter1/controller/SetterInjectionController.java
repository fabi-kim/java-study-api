package com.wikibooks.chapter1.controller;

import com.wikibooks.chapter1.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

// 예제 1.4 setter 메서드를 통한 의존성 주입
@RestController
public class SetterInjectionController {

  MyService myService;

  @Autowired
  public void setMyService(MyService myService) {
    this.myService = myService;
  }

}
