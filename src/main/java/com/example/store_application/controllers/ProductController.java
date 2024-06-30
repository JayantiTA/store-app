package com.example.store_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.store_application.controllers.domain.CreateProductReq;
import com.example.store_application.dto.ProductDTO;
import com.example.store_application.services.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(productService.getProductsByCategoryName(categoryName));
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid CreateProductReq product) {
        ProductDTO productDTO = ProductDTO.builder().name(product.getName()).price(product.getPrice())
                .stock(product.getStock()).categoryId(product.getCategoryId()).build();
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

}
