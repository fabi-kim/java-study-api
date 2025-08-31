package com.springboot.api.service;


import com.springboot.api.data.dto.SignInRequestDto;
import com.springboot.api.data.dto.SignInResultDto;
import com.springboot.api.data.dto.SignUpRequestDto;
import com.springboot.api.data.dto.SignUpResultDto;

// 예제 13.24
public interface SignService {

  SignUpResultDto signUp(SignUpRequestDto signUpRequestDto);

  SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException;

}