package com.hamza.ordermanagementsystem.controller;

import com.hamza.ordermanagementsystem.dto.request.CreateProductRequest;
import com.hamza.ordermanagementsystem.dto.response.ProductResponse;
import com.hamza.ordermanagementsystem.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/top-selling")
    public List<ProductResponse> getTopSelling() {
        return productService.getTopSellingProducts();
    }
}