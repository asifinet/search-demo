package com.example.demo.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

 }
