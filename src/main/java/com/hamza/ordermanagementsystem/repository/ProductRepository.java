package com.hamza.ordermanagementsystem.repository;

import com.hamza.ordermanagementsystem.entity.Product;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = """
        SELECT p.*
        FROM product p
        JOIN order_item oi ON p.id = oi.product_id
        GROUP BY p.id
        ORDER BY SUM(oi.quantity) DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Product> findTopSellingProducts();
}