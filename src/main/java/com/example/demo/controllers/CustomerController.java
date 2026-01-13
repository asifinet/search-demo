package com.example.demo.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.Loggable;
import com.example.demo.dto.CustomerCreateRequest;
import com.example.demo.dto.CustomerPatchRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.CustomerSearchResponse;
import com.example.demo.dto.CustomerUpdateRequest;
import com.example.demo.entity.CustomerOrderSummary;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;

@Loggable
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  
private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  // GET
  @GetMapping
  public List<CustomerResponse> listAll() {
    return customerService.listAll();
  }

  // GET by id
  @GetMapping("/{id}")
  public CustomerResponse getById(@PathVariable Long id) {
    return customerService.getById(id);
  }

  // GET search with pagination
  @GetMapping("/search")
  public CustomerSearchResponse search(
      @RequestParam(required = false) String q,
      @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
  ) {
    return customerService.search(q, pageable);
  }

  // POST
  @PostMapping
  public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerCreateRequest req) {
    CustomerResponse created = customerService.create(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  // PUT
  @PutMapping("/{id}")
  public CustomerResponse update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest req) {
    return customerService.update(id, req);
  }

  // PATCH
  @PatchMapping("/{id}")
  public CustomerResponse patch(@PathVariable Long id, @Valid @RequestBody CustomerPatchRequest req) {
    return customerService.patch(id, req);
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    customerService.delete(id);
    return ResponseEntity.noContent().build();
  }

  // JOIN query summary
  @GetMapping("/{id}/order-summary")
  public CustomerOrderSummary orderSummary(@PathVariable Long id) {
    return customerService.getOrderSummary(id);
  }
}
