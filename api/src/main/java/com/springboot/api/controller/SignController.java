package com.springboot.api.controller;

import com.springboot.api.data.dto.SignInRequestDto;
import com.springboot.api.data.dto.SignInResultDto;
import com.springboot.api.data.dto.SignUpRequestDto;
import com.springboot.api.data.dto.SignUpResultDto;
import com.springboot.api.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @PostMapping(value = "/sign-up")
  public SignUpResultDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
    LOGGER.info("[signUp] 회원가입을 수행합니다. signUpRequestDto : {}", signUpRequestDto);
    SignUpResultDto signUpResultDto = signService.signUp(signUpRequestDto);

    LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", signUpResultDto);
    return signUpResultDto;
  }

  @PostMapping(value = "/sign-in")
  public SignInResultDto signIn(@RequestBody SignInRequestDto signInRequestDto) {
    LOGGER.info("[signUp] 토큰생성 요청. signUpRequestDto : {}", signInRequestDto);
    SignInResultDto signInResultDto = signService.signIn(signInRequestDto);

    LOGGER.info("[signUp] 토큰생성 완료. signInResultDto : {}", signInResultDto);
    return signInResultDto;
  }
}