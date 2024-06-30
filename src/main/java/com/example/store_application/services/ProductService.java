package com.example.store_application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.store_application.dto.ProductDTO;
import com.example.store_application.entities.Category;
import com.example.store_application.entities.Product;
import com.example.store_application.repositories.CategoryRepository;
import com.example.store_application.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO createProduct(ProductDTO product) {
        Product productResp = productRepository.save(convertToEntity(product));
        return convertToDTO(productResp);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return convertToDTO(product);
    }

    public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElse(null);
        if (category == null) {
            return null;
        }
        List<Product> products = productRepository.findByCategoryId(category.getId());
        return products.stream().map(this::convertToDTO).toList();
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDTO).toList();
    }

    public ProductDTO updateProductQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        product.setStock(quantity);
        Product productResp = productRepository.save(product);
        return convertToDTO(productResp);
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
