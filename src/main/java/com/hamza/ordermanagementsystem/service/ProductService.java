package com.hamza.ordermanagementsystem.service;

import com.hamza.ordermanagementsystem.dto.request.CreateProductRequest;
import com.hamza.ordermanagementsystem.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProduct(Long id);
}