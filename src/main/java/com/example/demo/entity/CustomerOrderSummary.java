package com.example.demo.entity;

import java.math.BigDecimal;

public interface CustomerOrderSummary {
  Long getCustomerId();
  String getCustomerName();
  Long getOrderCount();
  BigDecimal getTotalSpent();
}
