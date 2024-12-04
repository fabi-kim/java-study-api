package com.springboot.security.controller;

import com.springboot.security.data.dto.SignInResultDto;
import com.springboot.security.data.dto.SignUpResultDto;
import com.springboot.security.service.SignService;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 예제 13.28
@RestController
@RequestMapping("/sign-api")
public class SignController {

  private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
  private final SignService signService;

  @Autowired
  public SignController(SignService signService) {
    this.signService = signService;
  }

  @PostMapping(value = "/sign-in")
  public SignInResultDto signIn(
          @Parameter(name = "id", description = "아이디", required = true) @RequestParam String id,
          @Parameter(name = "password", description = "패스워드", required = true) @RequestParam String password)
          throws RuntimeException {
    LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
    SignInResultDto signInResultDto = signService.signIn(id, password);

    if (signInResultDto.getCode() == 0) {
      LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", id,
              signInResultDto.getToken());
    }
    return signInResultDto;
  }

  @PostMapping(value = "/sign-up")
  public SignUpResultDto signUp(
          @Parameter(name = "id", description = "아이디", required = true) @RequestParam String id,
          @Parameter(name = "password", description = "패스워드", required = true) @RequestParam String password,
          @Parameter(name = "name", description = "이름", required = true) @RequestParam String name,
          @Parameter(name = "role", description = "역할", required = true) @RequestParam String role) {
    LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", id,
            name, role);
    SignUpResultDto signUpResultDto = signService.signUp(id, password, name, role);

    LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", id);
    return signUpResultDto;
  }

  @GetMapping(value = "/exception")
  public void exceptionTest() throws RuntimeException {
    throw new RuntimeException("접근이 금지되었습니다.");
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
    HttpHeaders responseHeaders = new HttpHeaders();
    //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

    Map<String, String> map = new HashMap<>();
    map.put("error type", httpStatus.getReasonPhrase());
    map.put("code", "400");
    map.put("message", "에러 발생");

    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }

}