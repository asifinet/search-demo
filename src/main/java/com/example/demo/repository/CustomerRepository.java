package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerOrderSummary;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

  Optional<Customer> findByEmail(String email);

  @Query("""
      select
        c.id as customerId,
        c.name as customerName,
        count(o.id) as orderCount,
        coalesce(sum(o.totalAmount), 0) as totalSpent
      from Customer c
      left join OrderEntity o on o.customer.id = c.id
      where c.id = :customerId
      group by c.id, c.name
      """)
  Optional<CustomerOrderSummary> getOrderSummary(@Param("customerId") Long customerId);
}
