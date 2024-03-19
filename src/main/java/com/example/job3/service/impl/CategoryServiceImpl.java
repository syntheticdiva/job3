package com.example.job3.service.impl;

import com.example.job3.dto.category.CategoryDto;
import com.example.job3.dto.category.CreateCategoryDto;
import com.example.job3.dto.category.UpdateCategoryDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.service.CategoryService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }
@Override
    public List<CategoryEntity> getAllCategory() { return categoryRepository.findAll();}
@Override
    public CategoryDto getCategoryById(UUID uuid) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(uuid);
        return categoryOptional.map(ModelConverter::toCategoryDto).orElse(null);
    }

@Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        CategoryEntity categoryEntity = ModelConverter.toCreateCategoryEntity(createCategoryDto);
        categoryEntity.setUuid(UUID.randomUUID());
        categoryEntity.setCreatedAt(Instant.now());
        categoryEntity.setUpdatedAt(Instant.now());
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return ModelConverter.toCategoryDto(savedCategory);
    }
@Override
    public CategoryDto updateCategory(UpdateCategoryDto categoryDto) {
    Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryDto.getUuid());
    if (categoryOptional.isPresent()) {
        CategoryEntity categoryEntity = categoryOptional.get();
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.setDescription(categoryDto.getDescription());
        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
        return ModelConverter.toCategoryDto(updatedCategory);
    } else {
        return null;
    }
}
@Override
    public boolean deleteCategory(UUID categoryDto) {
    Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryDto);
    if (categoryOptional.isPresent()) {
        categoryRepository.delete(categoryOptional.get());
        return true;
    } else {
        return false;
    }
}
@Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> category = categoryRepository.findAll();
        return ModelConverter.toCategoryDtoList(category);
    }
}