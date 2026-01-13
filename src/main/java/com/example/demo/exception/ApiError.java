package com.example.demo.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private Instant timestamp = Instant.now();
  private int status;
  private String error;
  private String message;
  private String path;
}
