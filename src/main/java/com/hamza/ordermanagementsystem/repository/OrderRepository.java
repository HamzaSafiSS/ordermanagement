package com.hamza.ordermanagementsystem.repository;

import com.hamza.ordermanagementsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}