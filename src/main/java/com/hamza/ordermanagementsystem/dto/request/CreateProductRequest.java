package com.hamza.ordermanagementsystem.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private Integer stock;
}