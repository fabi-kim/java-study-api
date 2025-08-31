package com.springboot.api.data.dto;


import com.springboot.api.config.annotation.Telephone;
import com.springboot.api.data.group.ValidatorGroup1;
import com.springboot.api.data.group.ValidatorGroup2;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class ValidRequestDto {

  @NotBlank
  String name;

  @Email
  String email;

  @Telephone
  String phoneNumber;

  @Min(value = 20, groups = ValidatorGroup1.class)
  @Max(value = 40, groups = ValidatorGroup1.class)
  int age;

  @Size(min = 0, max = 40)
  String description;

  @Positive(groups = ValidatorGroup2.class)
  int count;

  @AssertTrue
  boolean booleanCheck;
}
