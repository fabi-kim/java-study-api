package com.springboot.api.controller;

import com.springboot.api.common.Constants;
import com.springboot.api.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

  private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

  @GetMapping
  public void getRuntimeException() {

    throw new RuntimeException("테스트 exception");
    //return  ResponseEntity.ok("ok");
  }

  @GetMapping("/custom")
  public void getCustomException() throws CustomException {

    throw new CustomException(Constants.ExceptionClass.PRODUCT, HttpStatus.BAD_REQUEST,
        "getCustomException 호출");
  }

/*

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleException(RuntimeException e, HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        logger.error("ExceptionController 클래스 내 호출, {}, {}", request.getRequestURI(), e.getMessage());

        Map<String,String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code",  HttpStatus.BAD_REQUEST.toString());
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
*/

}
