package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Customer;
import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;

  public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
    this.orderRepository = orderRepository;
    this.customerRepository = customerRepository;
  }

  public List<OrderResponse> listAll() {
    return orderRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
  }

  @Transactional
  public OrderResponse create(OrderCreateRequest req) {
    Customer customer = customerRepository.findById(req.getCustomerId())
        .orElseThrow(() -> new NotFoundException("Customer not found: " + req.getCustomerId()));

    OrderEntity o = new OrderEntity(null, customer, req.getProductName(), req.getTotalAmount(), null);
    OrderEntity saved = orderRepository.save(o);
    return toResponse(saved);
  }

  private OrderResponse toResponse(OrderEntity o) {
    return new OrderResponse(
        o.getId(),
        o.getCustomer().getId(),
        o.getProductName(),
        o.getTotalAmount(),
        o.getCreatedAt()
    );
  }
}
