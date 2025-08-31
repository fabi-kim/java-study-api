package com.springboot.api.controller;

import com.springboot.api.data.dto.ValidRequestDto;
import com.springboot.api.data.group.ValidatorGroup1;
import com.springboot.api.data.group.ValidatorGroup2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validation")
public class ValidationController {

  private final Logger logger = LoggerFactory.getLogger(ValidationController.class);

  @PostMapping("/valid")
  public ResponseEntity<String> checkValidationByValid(
      @Validated @RequestBody ValidRequestDto validRequestDto) {
    logger.info(validRequestDto.toString());
    return ResponseEntity.status(HttpStatus.OK).body(validRequestDto.toString());

  }

  @PostMapping("/validated/group1")
  public ResponseEntity<String> checkValidation1(
      @Validated(ValidatorGroup1.class) @RequestBody ValidRequestDto validRequestDto) {
    logger.info(validRequestDto.toString());
    return ResponseEntity.status(HttpStatus.OK).body(validRequestDto.toString());

  }

  @PostMapping("/validated/group2")
  public ResponseEntity<String> checkValidation2(
      @Validated(ValidatorGroup2.class) @RequestBody ValidRequestDto validRequestDto) {
    logger.info(validRequestDto.toString());
    return ResponseEntity.status(HttpStatus.OK).body(validRequestDto.toString());

  }

  @PostMapping("/validated/all-group")
  public ResponseEntity<String> checkValidation3(
      @Validated({ValidatorGroup1.class,
          ValidatorGroup2.class}) @RequestBody ValidRequestDto validRequestDto) {
    logger.info(validRequestDto.toString());
    return ResponseEntity.status(HttpStatus.OK).body(validRequestDto.toString());

  }
}
