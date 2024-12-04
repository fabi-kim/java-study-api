package com.springboot.rest.controller;

import com.springboot.rest.dto.MemberDto;
import com.springboot.rest.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest-client")
public class RestClientController {

    private RestClientService restClientService;

    @Autowired
    public RestClientController(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    @GetMapping
    public String getName() {
        return restClientService.getName();
    }

    @GetMapping("/path-variable")
    public String getNameWithPathVariable() {
        return restClientService.getNameWithPathVariable();
    }

    @GetMapping("/parameter")
    public String getNameWithParameter() {
        return restClientService.getNameWithParameter();
    }

    @PostMapping
    public ResponseEntity<MemberDto> postDto() {
        return restClientService.postWithParamAndBody();
    }

    @PostMapping("/header")
    public ResponseEntity<MemberDto> postWithHeader(){
        return restClientService.postWithHeader();
    }
}
