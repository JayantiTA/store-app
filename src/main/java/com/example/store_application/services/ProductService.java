package com.example.store_application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.store_application.dto.ProductDTO;
import com.example.store_application.entities.Product;
import com.example.store_application.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO createProduct(ProductDTO product) {
        Product productResp = productRepository.save(convertToEntity(product));
        return convertToDTO(productResp);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return convertToDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDTO).toList();
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder().id(product.getId()).name(product.getName()).price(product.getPrice())
                .stock(product.getStock()).categoryId(product.getCategoryId()).build();
    }

    private Product convertToEntity(ProductDTO product) {
        return Product.builder().id(product.getId()).name(product.getName()).price(product.getPrice())
                .stock(product.getStock()).categoryId(product.getCategoryId()).build();
    }

}
