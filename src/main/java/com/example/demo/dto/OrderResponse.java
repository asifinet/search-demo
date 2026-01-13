package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
  private Long id;
  private Long customerId;
  private String productName;
  private BigDecimal totalAmount;
  private Instant createdAt;
 }
