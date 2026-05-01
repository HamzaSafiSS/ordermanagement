package com.hamza.ordermanagementsystem.service.impl;

import com.hamza.ordermanagementsystem.dto.request.CreateProductRequest;
import com.hamza.ordermanagementsystem.dto.response.ProductResponse;
import com.hamza.ordermanagementsystem.entity.Product;
import com.hamza.ordermanagementsystem.exception.ResourceNotFoundException;
import com.hamza.ordermanagementsystem.repository.ProductRepository;
import com.hamza.ordermanagementsystem.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    @Override
    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    @Override
    public List<ProductResponse> getTopSellingProducts() {

        return productRepository.findTopSellingProducts()
                .stream()
                .map(p -> ProductResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .stock(p.getStock())
                        .build())
                .toList();
    }
}