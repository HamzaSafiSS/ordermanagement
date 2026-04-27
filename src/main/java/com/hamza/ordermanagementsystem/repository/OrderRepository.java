package com.hamza.ordermanagementsystem.repository;

import com.hamza.ordermanagementsystem.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @EntityGraph(attributePaths = {
            "user",
            "orderItems",
            "orderItems.product"
    })
    List<Order> findAll();
}