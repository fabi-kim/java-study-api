package com.springboot.rest.service;

import com.springboot.rest.dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestClientService {

  // 예제 12.12
  public String getName() {
    RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:9090")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    return restClient.get()
            .uri("/api/v1/crud-api")
            .retrieve()
            .body(String.class);
  }

  public String getNameWithPathVariable() {
    RestClient restClient = RestClient.create("http://localhost:9090");

    ResponseEntity<String> responseEntity = restClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/{name}")
                    .build("Flature"))
            .retrieve().toEntity(String.class);

    ResponseEntity<String> responseEntity1 = restClient.get()
            .uri("/api/v1/crud-api/{name}", "Flature")
            .retrieve()
            .toEntity(String.class);

    return responseEntity.getBody();
  }

  public String getNameWithParameter() {
    RestClient restClient = RestClient.create("http://localhost:9090");

    return restClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/param")
                    .queryParam("name", "Flature")
                    .build())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
              throw new RuntimeException("400 Error occurred");
            })
            .body(String.class);
  }

  // 예제 12.13
  public ResponseEntity<MemberDto> postWithParamAndBody() {
    RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:9090")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    MemberDto memberDTO = new MemberDto();
    memberDTO.setName("flature!!");
    memberDTO.setEmail("flature@gmail.com");
    memberDTO.setOrganization("Around Hub Studio");

    return restClient.post().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api")
                    .queryParam("name", "Flature")
                    .queryParam("email", "flature@wikibooks.co.kr")
                    .queryParam("organization", "Wikibooks")
                    .build())
            .body(memberDTO)
            .retrieve()
            .toEntity(MemberDto.class);
  }

  public ResponseEntity<MemberDto> postWithHeader() {
    RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:9090")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    MemberDto memberDTO = new MemberDto();
    memberDTO.setName("flature!!");
    memberDTO.setEmail("flature@gmail.com");
    memberDTO.setOrganization("Around Hub Studio");

    return restClient
            .post()
            .uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/add-header")
                    .build())
            .body(memberDTO)
            .header("my-header", "Wikibooks API")
            .retrieve()
            .toEntity(MemberDto.class);
  }

  public void cloneWebClient() {
    RestClient restClient = RestClient.create("http://localhost:9090");

    RestClient clone = restClient.mutate().build();
  }
}
