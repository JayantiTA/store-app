package com.example.store_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.store_application.controllers.domain.CreateCategoryReq;
import com.example.store_application.dto.CategoryDTO;
import com.example.store_application.services.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CreateCategoryReq category) {
        CategoryDTO categoryDTO = CategoryDTO.builder().name(category.getName()).build();
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

}
