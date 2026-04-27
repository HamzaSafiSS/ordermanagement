package com.hamza.ordermanagementsystem.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private String status;
    private BigDecimal totalAmount;

    private String userEmail;

    private List<OrderItemResponse> items;
}