package com.example.store_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store_application.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}