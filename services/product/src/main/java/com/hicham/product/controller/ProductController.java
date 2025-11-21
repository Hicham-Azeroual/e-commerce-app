package com.hicham.product.controller;


import com.hicham.product.dto.ProductPurchaseRequest;
import com.hicham.product.dto.ProductPurchaseResponse;
import com.hicham.product.dto.ProductRequest;
import com.hicham.product.dto.ProductResponse;
import com.hicham.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request
    ) {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> request
    ) {
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("product-id") Integer productId
    ) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<Integer> updateProduct(
            @RequestBody @Valid ProductRequest request,
            @PathVariable("product-id") Integer productId
    ) {
        return ResponseEntity.ok(service.updateProduct(request, productId));
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("product-id") Integer productId
    ) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
