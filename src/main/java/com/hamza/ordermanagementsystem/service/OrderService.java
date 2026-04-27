package com.hamza.ordermanagementsystem.service;

import com.hamza.ordermanagementsystem.dto.request.CreateOrderRequest;
import com.hamza.ordermanagementsystem.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    void removeItem(Long orderId, Long orderItemId);

    void deleteOrder(Long orderId);
}