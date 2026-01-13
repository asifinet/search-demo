package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CustomerCreateRequest;
import com.example.demo.dto.CustomerPatchRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.CustomerSearchResponse;
import com.example.demo.dto.CustomerUpdateRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerOrderSummary;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<CustomerResponse> listAll() {
	  return customerRepository.findAll(Sort.by("id").ascending())
	      .stream()
	      .map(this::toResponse)
	      .collect(Collectors.toList());
	}

  public CustomerResponse getById(Long id) {
    Customer c = customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Customer not found: " + id));
    return toResponse(c);
  }

  public CustomerSearchResponse search(String q, Pageable pageable) {
    String query = (q == null) ? "" : q.trim();
    Page<Customer> page = customerRepository
        .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable);

    List<CustomerResponse> items = page.getContent().stream().map(this::toResponse).collect(Collectors.toList());
    return new CustomerSearchResponse(items, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
  }

  @Transactional
  public CustomerResponse create(CustomerCreateRequest req) {
    Customer c = new Customer(null, req.getName(), req.getEmail(), null);
    Customer saved = customerRepository.save(c);
    return toResponse(saved);
  }

  @Transactional
  public CustomerResponse update(Long id, CustomerUpdateRequest req) {
    Customer c = customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Customer not found: " + id));

    c.setName(req.getName());
    c.setEmail(req.getEmail());
    return toResponse(c);
  }

  @Transactional
  public CustomerResponse patch(Long id, CustomerPatchRequest req) {
    Customer c = customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Customer not found: " + id));

    if (req.getName() != null) c.setName(req.getName());
    if (req.getEmail() != null) c.setEmail(req.getEmail());
    return toResponse(c);
  }

  @Transactional
  public void delete(Long id) {
    if (!customerRepository.existsById(id)) {
      throw new NotFoundException("Customer not found: " + id);
    }
    customerRepository.deleteById(id);
  }

  public CustomerOrderSummary getOrderSummary(Long customerId) {
    return customerRepository.getOrderSummary(customerId)
        .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));
  }

  private CustomerResponse toResponse(Customer c) {
    return new CustomerResponse(c.getId(), c.getName(), c.getEmail());
  }
}
