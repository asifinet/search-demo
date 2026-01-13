package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
  @NotNull
  private Long customerId;

  @NotBlank
  private String productName;

  @NotNull
  @DecimalMin(value = "0.00", inclusive = false)
  private BigDecimal totalAmount;

 }
