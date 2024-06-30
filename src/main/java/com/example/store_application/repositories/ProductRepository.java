package com.example.store_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store_application.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
