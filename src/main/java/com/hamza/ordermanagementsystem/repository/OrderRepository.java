package com.hamza.ordermanagementsystem.repository;

import com.hamza.ordermanagementsystem.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.*;
import com.hamza.ordermanagementsystem.dto.projection.OrderSummaryDTO;
import com.hamza.ordermanagementsystem.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @EntityGraph(attributePaths = {
            "user",
            "orderItems",
            "orderItems.product"
    })
    List<Order> findAll();

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findOrdersByUserId(Long userId);

    // ✅ JPQL — Revenue calculation
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double calculateTotalRevenue();

    // ✅ DTO Projection
    @Query("""
        SELECT o.id AS id,
               o.totalAmount AS totalAmount,
               COUNT(oi.id) AS itemCount
        FROM Order o
        JOIN o.orderItems oi
        GROUP BY o.id
    """)
    List<OrderSummaryDTO> getOrderSummaries();

    // ✅ Pagination + Filtering
    Page<Order> findByStatusAndCreatedAtBetween(
            OrderStatus status,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}