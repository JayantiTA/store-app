package com.example.store_application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.store_application.dto.CategoryDTO;
import com.example.store_application.entities.Category;
import com.example.store_application.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO category) {
        Category categoryResp = categoryRepository.save(convertToEntity(category));
        return convertToDTO(categoryResp);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return convertToDTO(category);
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDTO).toList();
    }

    private CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder().id(category.getId()).name(category.getName()).build();
    }

    private Category convertToEntity(CategoryDTO category) {
        return Category.builder().id(category.getId()).name(category.getName()).build();
    }

}
