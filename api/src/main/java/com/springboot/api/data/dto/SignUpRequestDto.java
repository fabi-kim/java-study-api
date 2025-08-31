package com.springboot.api.data.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpRequestDto {

  @Parameter(name = "id", description = "아이디", required = true)
  private String id;

  @Parameter(name = "password", description = "패스워드", required = true)
  private String password;

  @Parameter(name = "name", description = "이름", required = true)
  private String name;

  @Parameter(name = "role", description = "역할", required = true)
  private String role;

}