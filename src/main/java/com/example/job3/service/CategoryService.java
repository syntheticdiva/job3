package com.example.job3.service;

import com.example.job3.dto.category.CategoryDto;
import com.example.job3.dto.category.CreateCategoryDto;
import com.example.job3.dto.category.UpdateCategoryDto;
import com.example.job3.entity.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryEntity> getAllCategory();

    CategoryDto getCategoryById(UUID uuid);

    CategoryDto createCategory(CreateCategoryDto createCategoryDto);

    CategoryDto updateCategory(UpdateCategoryDto categoryDto);

    boolean deleteCategory(UUID categoryDto);

    List<CategoryDto> getAllCategories();
}
