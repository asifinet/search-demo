package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearchResponse {
  private List<CustomerResponse> items;
  private int page;
  private int size;
  private long totalElements;
  private int totalPages;

}
