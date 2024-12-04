package com.wikibooks.chapter1.controller;

import com.wikibooks.chapter1.service.MyService;
import com.wikibooks.chapter1.service.MyServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 예제 1.1 일반적인 자바 코드에서의 객체 사용법
@RestController
public class NoDIController {

    private MyService service = new MyServiceImpl();

    @GetMapping("/no-di/hello")
    public String getHello() {
        return service.getHello();
    }

}
