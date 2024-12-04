package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 예제 5.1
@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    // 예제 5.26
    private final Logger LOGGER = LoggerFactory.getLogger(GetController.class);

    // 예제 5.2
    // http://localhost:8080/api/v1/get-api/hello
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        // 예제 5.27
        LOGGER.info("getHello 메서드가 호출되었습니다.");
        return "Hello World";
    }

    // 예제 5.3
    // http://localhost:8080/api/v1/get-api/name
    @GetMapping(value = "/name")
    public String getName() {
        // 예제 5.27
        LOGGER.info("getName 메서드가 호출되었습니다.");
        return "Flature";
    }

    // 예제 5.4
    // http://localhost:8080/api/v1/get-api/variable1/{String 값}
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable) {
        // 예제 5.28
        LOGGER.info("@PathVariable을 통해 들어온 값 : {}", variable);
        return variable;
    }

    // 예제 5.5
    // http://localhost:8080/api/v1/get-api/variable2/{String 값}
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable") String var) {
        return var;
    }

    // 예제 5.6, 예제 5.23
    // http://localhost:8080/api/v1/get-api/request2?name=flature&email=thinkground.flature@gmail.com&organization=thinkground
    @Operation(summary = "GET 메서드 예제", description = "@RequestParam을 활용한 GET 메서드")
    @GetMapping(value = "/request1")
    public String getRequestParam1(
            @Parameter(name = "name", description = "이름", required = true) @RequestParam String name,
            @Parameter(name = "email", description = "이메일", required = true) @RequestParam String email,
            @Parameter(name = "organization", description = "회사", required = true) @RequestParam String organization) {
        return name + " " + email + " " + organization;
    }

    // 예제 5.7
    // http://localhost:8080/api/v1/get-api/request2?key1=value1&key2=value2
    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String, String> param) {
        StringBuilder sb = new StringBuilder();

        param.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });

        return sb.toString();
    }

    // 예제 5.9
    // http://localhost:8080/api/v1/get-api/request3?name=value1&email=value2&organization=value3
    @Operation(summary = "GET 메서드 예제", description = "DTO를 활용한 GET 메서드")
    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto memberDTO) {
        return memberDTO.toString();
    }
}
