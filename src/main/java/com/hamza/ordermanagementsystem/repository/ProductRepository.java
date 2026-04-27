package com.hamza.ordermanagementsystem.repository;

import com.hamza.ordermanagementsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}