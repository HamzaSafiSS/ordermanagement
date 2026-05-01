package com.hamza.ordermanagementsystem.controller;

import com.hamza.ordermanagementsystem.dto.projection.OrderSummaryDTO;
import com.hamza.ordermanagementsystem.dto.request.CreateOrderRequest;
import com.hamza.ordermanagementsystem.dto.response.OrderResponse;
import com.hamza.ordermanagementsystem.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
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

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/revenue")
    public Double getRevenue() {
        return orderService.getTotalRevenue();
    }

    @GetMapping("/summary")
    public List<OrderSummaryDTO> getSummaries() {
        return orderService.getOrderSummaries();
    }

    @GetMapping("/filter")
    public Object filterOrders(
            @RequestParam String status,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam int page,
            @RequestParam int size) {

        return orderService.getOrdersWithFilter(
                status,
                java.time.LocalDateTime.parse(start),
                java.time.LocalDateTime.parse(end),
                page,
                size
        );
    }
}