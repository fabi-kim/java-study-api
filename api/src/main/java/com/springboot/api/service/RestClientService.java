package com.springboot.api.service;

import com.springboot.api.data.dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestClientService {

  RestClient restClient = RestClient.builder()
      .baseUrl("http://localhost:9090")
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .build();

  public String getName() {
    RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:9090")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    return restClient.get()
        .uri("api/v1/crud-api")
        .retrieve()
        .body(String.class);
  }

  public String getNameWithPathVariable() {
    RestClient restClient = RestClient.create("http://localhost:9090");

    ResponseEntity<String> responseEntity = restClient.get()
        .uri(uriBuilder -> uriBuilder.path("api/v1/crud-api/{name}").build("sung"))
        .retrieve()
        .toEntity(String.class);

    return responseEntity.getBody();
  }

  public String getNameWithParameter() {
    RestClient restClient = RestClient.create("http://localhost:9090");

    return restClient.get().uri(uriBuilder -> uriBuilder.path("api/v1/crud-api")
            .queryParam("name", "sung1")
            .build())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, request.toString());
        })
        .body(String.class);
  }

  public ResponseEntity<MemberDto> postWithParamAndBody() {
    restClient = this.restClient.mutate().build();

    MemberDto memberDto = new MemberDto();
    memberDto.setName("sung");
    memberDto.setEmail("a@b.com");
    memberDto.setOrganization("org");

    return restClient.post()
        .uri(uriBuilder -> uriBuilder.path("api/v1/crud-api")
            .queryParam("name", "sung")
            .queryParam("email", "a@b.com")
            .queryParam("organization", "org").build())
        .body(memberDto)
        .retrieve()
        .toEntity(MemberDto.class);
  }

  public ResponseEntity<MemberDto> postWithHeader() {
    restClient = this.restClient.mutate().build();

    MemberDto memberDto = new MemberDto();
    memberDto.setName("sung");
    memberDto.setEmail("a@b.com");
    memberDto.setOrganization("org");

    return restClient
        .post()
        .uri(uriBuilder -> uriBuilder.path("api/v1/crud-api/add-header").build())
        .body(memberDto)
        .header("my-header", "hedaer-ttt")
        .retrieve()
        .toEntity(MemberDto.class);
  }
}
