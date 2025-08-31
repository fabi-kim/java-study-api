package com.springboot.api.service;

import com.springboot.api.data.dto.MemberDto;
import java.net.URI;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateService {

  public String getName() {
    URI uri = UriComponentsBuilder.fromUriString("http://localhost:9090").path("/api/v1/crud-api")
        .encode().build().toUri();
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

    return responseEntity.getBody();
  }

  public String getNameWithPathVariable() {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:9090")
        .path("/api/v1/crud-api/{name}")
        .encode()
        .build()
        .expand("name")
        .toUri();

    RestTemplate restTemplate = restTemplate();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

    return responseEntity.getBody();
  }

  public String getNameWithParameter() {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:9090")
        .path("api/v1/crud-api/param")
        .queryParam("name", "sung")
        .encode()
        .build()
        .toUri();

    RestTemplate restTemplate = restTemplate();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

    return responseEntity.getBody();
  }

  public ResponseEntity<MemberDto> postWithParamAndBody() {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:9090")
        .path("api/v1/crud-api")
        .queryParam("name", "sung")
        .queryParam("email", "a@b.com")
        .queryParam("organization", "org")
        .encode()
        .build()
        .toUri();

    MemberDto memberDto = new MemberDto();
    memberDto.setName("sung");
    memberDto.setEmail("a@b.com");
    memberDto.setOrganization("org");

    RestTemplate restTemplate = restTemplate();
    ResponseEntity<MemberDto> responseEntity = restTemplate.postForEntity(uri, memberDto,
        MemberDto.class);

    return responseEntity;
  }

  public ResponseEntity<MemberDto> postWithHeader() {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:9090")
        .path("api/v1/crud-api/add-header")
        .encode()
        .build()
        .toUri();

    MemberDto memberDto = new MemberDto();
    memberDto.setName("sung");
    memberDto.setEmail("a@b.com");
    memberDto.setOrganization("org");

    RequestEntity<MemberDto> requestEntity = RequestEntity
        .post(uri)
        .header("my-header", "header111")
        .body(memberDto);

    RestTemplate restTemplate = restTemplate();
    ResponseEntity<MemberDto> responseEntity = restTemplate.exchange(requestEntity,
        MemberDto.class);

    return responseEntity;
  }

  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

    SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(Timeout.ofSeconds(5)).build();
    CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
            .setDefaultSocketConfig(socketConfig)
            .setMaxConnPerRoute(100)
            .setMaxConnTotal(80)
            .build())
        .build();

    factory.setHttpClient(httpClient);
    factory.setConnectTimeout(2000);

    return new RestTemplate(factory);
  }
}
