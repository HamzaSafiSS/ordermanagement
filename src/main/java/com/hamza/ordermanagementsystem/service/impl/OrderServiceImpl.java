package com.hamza.ordermanagementsystem.service.impl;

import com.hamza.ordermanagementsystem.dto.request.CreateOrderRequest;
import com.hamza.ordermanagementsystem.dto.response.OrderItemResponse;
import com.hamza.ordermanagementsystem.dto.projection.OrderSummaryDTO;
import com.hamza.ordermanagementsystem.dto.response.OrderResponse;
import com.hamza.ordermanagementsystem.entity.*;
import com.hamza.ordermanagementsystem.entity.enums.OrderStatus;
import com.hamza.ordermanagementsystem.exception.ResourceNotFoundException;
import com.hamza.ordermanagementsystem.repository.OrderRepository;
import com.hamza.ordermanagementsystem.repository.ProductRepository;
import com.hamza.ordermanagementsystem.repository.UserRepository;
import com.hamza.ordermanagementsystem.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        BigDecimal total = BigDecimal.ZERO;

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            // 🔥 CHECK STOCK
            if (product.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock");
            }

            // 🔥 DEDUCT STOCK
            product.setStock(product.getStock() - itemReq.getQuantity());

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());

            order.addItem(item);

            total = total.add(product.getPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        order.setTotalAmount(total);

        // 🔥 SAVE ORDER (cascade saves items)
        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        // ❌ This will cause N+1 (try this first)

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void removeItem(Long orderId, Long orderItemId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderItem itemToRemove = order.getOrderItems()
                .stream()
                .filter(i -> i.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        order.removeItem(itemToRemove); // 🔥 triggers orphanRemoval
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        orderRepository.delete(order); // 🔥 cascade delete
    }

    private OrderResponse mapToResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .userEmail(order.getUser().getEmail()) // 🔥 triggers lazy
                .items(order.getOrderItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(item.getProduct().getId())
                                .productName(item.getProduct().getName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {

        return orderRepository.findOrdersByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Double getTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }

    @Override
    public List<OrderSummaryDTO> getOrderSummaries() {
        return orderRepository.getOrderSummaries();
    }

    @Override
    public Page<Order> getOrdersWithFilter(
            String status,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size) {

        return orderRepository.findByStatusAndCreatedAtBetween(
                Enum.valueOf(com.hamza.ordermanagementsystem.entity.enums.OrderStatus.class, status),
                start,
                end,
                org.springframework.data.domain.PageRequest.of(page, size)
        );
    }
}