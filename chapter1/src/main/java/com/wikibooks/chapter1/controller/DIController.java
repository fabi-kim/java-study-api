package com.wikibooks.chapter1.controller;

import com.wikibooks.chapter1.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 예제 1.2 생성자를 통한 의존성 주입
@RestController
public class DIController {

    MyService myService;

    @Autowired
    public DIController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/di/hello")
    public String getHello() {
        return myService.getHello();
    }

}
