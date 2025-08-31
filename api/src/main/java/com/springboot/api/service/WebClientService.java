package com.springboot.api.service;

import com.springboot.api.data.dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

  public String getName() {
    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:9090")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    return webClient.get()
        .uri("/api/v1/crud-api")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public String getNameWithPathVariable() {
    WebClient webClient = WebClient.create("http://localhost:9090");

    ResponseEntity<String> responseEntity = webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/{name}").build("sung"))
        .retrieve().toEntity(String.class).block();
    return responseEntity.getBody();
  }

  public String getNameWithParameter() {
    WebClient webClient = WebClient.create("http://localhost:9090");

    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api").queryParam("name", "query-name")
            .build())
        .exchangeToMono(clientResponse -> {
          if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(String.class);
          } else {
            return clientResponse.createException().flatMap(Mono::error);
          }
        }).block();
  }

  public ResponseEntity<MemberDto> postWithParamAndBody() {
    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:9090")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    MemberDto memberDto = new MemberDto();
    memberDto.setName("sung2");
    memberDto.setEmail("a@b.com");
    memberDto.setOrganization("org2");

    return webClient.post()
        .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api")
            .queryParam("name", "sung")
            .queryParam("email", "a@b.com")
            .queryParam("organization", "org")
            .build())
        .bodyValue(memberDto)
        .retrieve()
        .toEntity(MemberDto.class)
        .block();
  }

  public ResponseEntity<MemberDto> postWithHeader() {
    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:9090")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();

    MemberDto memberDto = new MemberDto();
    memberDto.setName("sung2");
    memberDto.setEmail("a@b.com");
    memberDto.setOrganization("org2");

    return webClient
        .post()
        .uri(uriBuilder -> uriBuilder.path("/api/v1/add-header").build())
        .bodyValue(memberDto)
        .header("my-header", "hader-test")
        .retrieve()
        .toEntity(MemberDto.class)
        .block();
  }
}
