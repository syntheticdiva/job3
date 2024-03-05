package com.example.job3.service;

import com.example.job3.dto.CategoryDto;
import com.example.job3.dto.CreateCategoryDto;
import com.example.job3.dto.UpdateCategoryDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;



    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }
    //
//    public List<CategoryDto> getAllCategory() {
//        List<CategoryEntity> categories = categoryRepository.findAll();
//        return categories.isList()
//                .map(categoryMapper::toCategoryDto)
//                .collect(Collectors.toList());
//    }
    public List<CategoryEntity> getAllCategory() { return categoryRepository.findAll();}

    public CategoryDto getCategoryById(UUID uuid) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(uuid);
        return categoryOptional.map(ModelConverter::toCategoryDto).orElse(null);
    }


    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        CategoryEntity categoryEntity = ModelConverter.toCreateCategoryEntity(createCategoryDto);
        categoryEntity.setUuid(UUID.randomUUID());
        categoryEntity.setCreatedAt(Instant.now());
        categoryEntity.setUpdatedAt(Instant.now());
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return ModelConverter.toCategoryDto(savedCategory);
    }

//    private CategoryDto mapCategoryToDto(CategoryEntity categoryEntity) {
//        return CategoryDto.builder()
//                .name(categoryEntity.getName())
//                .description(categoryEntity.getDescription())
//                .products(categoryEntity.getProducts())
//                .build();
//    }
//
//    private CategoryDto mapEntityToDto(CategoryEntity categoryEntity) {
//        return CategoryDto.builder()
//                .name(categoryEntity.getName())
//                .description(categoryEntity.getDescription())
//                .products(categoryEntity.getProducts())
//                .build();
//    }

//    public CategoryDto updateCategory(UpdateCategoryDto categoryDto) {
//        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryDto.getUuid());
//        if (categoryOptional.isPresent()) {
//            CategoryEntity categoryEntity = categoryOptional.get();
//            categoryEntity.setName(categoryDto.getName());
//            categoryEntity.setDescription(categoryDto.getDescription());
//            // Используйте setProducts() для установки списка продуктов
//            categoryEntity.setProducts(categoryDto.getProducts());
//            CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
//            return categoryMapper.toCategoryDto(updatedCategory);
//        } else {
//            return null;
//        }


    //    }
//}
    public CategoryDto updateCategory(UpdateCategoryDto categoryDto) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryDto.getId());
        if (categoryOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryOptional.get();
            categoryEntity.setName(categoryDto.getName());
            categoryEntity.setDescription(categoryDto.getDescription());
//        categoryEntity.setProducts(updateCategoryDto.getProducts().toString());
            CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
            return ModelConverter.toCategoryDto(updatedCategory);
        } else {
            return null;
        }
    }
    public boolean deleteCategory(UUID categoryDto) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryDto);
        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
            return true;
        } else {
            return false;
        }
    }
}