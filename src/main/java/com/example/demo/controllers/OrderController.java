package com.example.demo.controllers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.Loggable;
import com.example.demo.dto.OrderCreateRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.service.OrderService;

import jakarta.validation.Valid;

@Loggable
@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public List<OrderResponse> listAll() {
    return orderService.listAll();
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest req) {
    OrderResponse created = orderService.create(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }
}
