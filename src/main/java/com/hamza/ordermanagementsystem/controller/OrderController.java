package com.hamza.ordermanagementsystem.controller;

import com.hamza.ordermanagementsystem.dto.request.CreateOrderRequest;
import com.hamza.ordermanagementsystem.dto.response.OrderResponse;
import com.hamza.ordermanagementsystem.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public String removeItem(@PathVariable Long orderId,
                             @PathVariable Long itemId) {

        orderService.removeItem(orderId, itemId);
        return "Item removed (check DB for orphan removal)";
    }

    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "Order deleted (check cascade)";
    }
}