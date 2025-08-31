package com.springboot.api.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);


  @ExceptionHandler(value = CustomException.class)
  public ResponseEntity<Map<String, String>> handleCustomException(CustomException e,
      HttpServletRequest request) {
    HttpHeaders responseHeaders = new HttpHeaders();
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    logger.error("handleCustomException 호출, {}, {}", request.getRequestURI(), e.getMessage());

    Map<String, String> map = new HashMap<>();
    map.put("error type", httpStatus.getReasonPhrase());
    map.put("code", HttpStatus.BAD_REQUEST.toString());

    map.put("message", e.getMessage());

    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e,
      HttpServletRequest request) {
    HttpHeaders responseHeaders = new HttpHeaders();
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    logger.error("Advice RuntimeException 호출, {}, {}", request.getRequestURI(), e.getMessage());

    Map<String, String> map = new HashMap<>();
    map.put("error type", httpStatus.getReasonPhrase());
    map.put("code", HttpStatus.BAD_REQUEST.toString());
    map.put("message", e.getMessage());

    Throwable cause = e.getCause();

    if (cause instanceof CustomException customException) {
      map.put("code", customException.getHttpStatus().value() + "");
      map.put("message", customException.getMessage());
    }

    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }
}
