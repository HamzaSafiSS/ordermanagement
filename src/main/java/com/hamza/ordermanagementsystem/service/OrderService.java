package com.hamza.ordermanagementsystem.service;

import com.hamza.ordermanagementsystem.dto.request.CreateOrderRequest;
import com.hamza.ordermanagementsystem.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    void removeItem(Long orderId, Long orderItemId);

    void deleteOrder(Long orderId);
}