package com.hamza.ordermanagementsystem.entity;

import com.hamza.ordermanagementsystem.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {

    private String name;

    private BigDecimal price;

    private Integer stock;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}