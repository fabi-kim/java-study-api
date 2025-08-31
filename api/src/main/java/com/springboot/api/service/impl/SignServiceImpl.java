package com.springboot.api.service.impl;

import com.springboot.api.common.common.CommonResponse;
import com.springboot.api.config.security.JwtTokenProvider;
import com.springboot.api.data.dto.SignInRequestDto;
import com.springboot.api.data.dto.SignInResultDto;
import com.springboot.api.data.dto.SignUpRequestDto;
import com.springboot.api.data.dto.SignUpResultDto;
import com.springboot.api.data.entity.User;
import com.springboot.api.data.repository.UserRepository;
import com.springboot.api.external.redis.RedisService;
import com.springboot.api.service.SignService;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl implements SignService {

  private final Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;

  @Autowired
  public SignServiceImpl(
      UserRepository userRepository,
      JwtTokenProvider jwtTokenProvider,
      RedisService redisService
  ) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.redisService = redisService;
  }

  @Override
  public SignUpResultDto signUp(SignUpRequestDto signUpRequestDto) {
    String id = signUpRequestDto.getId();
    String password = signUpRequestDto.getPassword();
    String name = signUpRequestDto.getName();
    String role = signUpRequestDto.getRole();

    logger.info("[signUp] 회원 가입 정보 전달");
    User user;

    if (role.equalsIgnoreCase("admin")) {
      user = User.builder().
          uid(id)
          .name(name)
          .password(password)
          .role(Collections.singletonList("ROLE_ADMIN"))
          .build();
    } else {
      user = User.builder().
          uid(id)
          .name(name)
          .password(password)
          .role(Collections.singletonList("ROLE_USER"))
          .build();
    }

    User savedUser = userRepository.save(user);
    SignUpResultDto signUpResultDto = new SignUpResultDto();

    logger.info("[signUp] 회원 정보 저장여부 확인");

    if (!savedUser.getName().isEmpty()) {
      logger.info("[signUp] 정상처리 완료");
      setSuccessResult(signUpResultDto);
    } else {
      logger.info("[signUp] 정상처리 실패");
      setFailResult(signUpResultDto);
    }
    return signUpResultDto;
  }


  @Override
  public SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException {
    String uid = signInRequestDto.getId();
    String password = signInRequestDto.getPassword();

    logger.info("[signUp] 회원 정보 요청");
    User user = userRepository.getByUid(uid);
    logger.info("[signUp] uid: {}", uid);

    SignInResultDto signInResultDto = SignInResultDto.builder()
        .token(jwtTokenProvider.createToken(user.getUid(), user.getRole()))
        .build();

    setSuccessResult(signInResultDto);

    SignInResultDto cached = redisService.getSingleData("signIn::" + uid, SignInResultDto.class);
    if (cached != null) {
      return cached;
    }
    redisService.setSingleData("signIn::" + uid, signInResultDto);
    return signInResultDto;
  }

  private void setSuccessResult(SignUpResultDto result) {
    result.setSuccess(true);
    result.setCode(CommonResponse.SUCCESS.getCode());
    result.setMsg(CommonResponse.SUCCESS.getMsg());
  }

  private void setFailResult(SignUpResultDto result) {
    result.setSuccess(false);
    result.setCode(CommonResponse.FAIL.getCode());
    result.setMsg(CommonResponse.FAIL.getMsg());
  }
}
