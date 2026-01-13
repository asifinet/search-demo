package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.entity.CustomerOrderSummary;
import com.example.demo.repository.CustomerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired CustomerRepository customerRepository;

  @Test
  void joinSummaryQuery_returnsAggregates() {
    CustomerOrderSummary summary = customerRepository.getOrderSummary(1L).orElseThrow();
    assertThat(summary.getCustomerId()).isEqualTo(1L);
    assertThat(summary.getOrderCount()).isGreaterThanOrEqualTo(1L);
    assertThat(summary.getTotalSpent()).isNotNull();
  }
}
