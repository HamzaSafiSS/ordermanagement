package com.hamza.ordermanagementsystem.service;

import com.hamza.ordermanagementsystem.dto.projection.OrderSummaryDTO;
import com.hamza.ordermanagementsystem.dto.request.CreateOrderRequest;
import com.hamza.ordermanagementsystem.dto.response.OrderResponse;
import com.hamza.ordermanagementsystem.entity.Order;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByUser(Long userId);

    Double getTotalRevenue();

    List<OrderSummaryDTO> getOrderSummaries();

    Page<Order> getOrdersWithFilter(
            String status,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size
    );

    void removeItem(Long orderId, Long orderItemId);

    void deleteOrder(Long orderId);
}