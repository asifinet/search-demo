package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
    ApiError err = new ApiError(null, 404, "Not Found", ex.getMessage(), req.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    StringBuilder sb = new StringBuilder("Validation failed: ");
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("; ");
    }
    ApiError err = new ApiError(null, 400, "Bad Request", sb.toString(), req.getRequestURI());
    return ResponseEntity.badRequest().body(err);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    ApiError err = new ApiError(null, 500, "Internal Server Error", ex.getMessage(), req.getRequestURI());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
  }
}
