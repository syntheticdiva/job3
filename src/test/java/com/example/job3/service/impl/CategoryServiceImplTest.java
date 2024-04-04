package com.example.job3.service.impl;

import com.example.job3.service.CategoryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.category.*;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.utils.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void testGetAllCategory_ReturnsAllCategories() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(createMockCategoryEntity(UUID.randomUUID(), "Category 1"));
        categoryEntities.add(createMockCategoryEntity(UUID.randomUUID(), "Category 2"));

        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        List<CategoryEntity> result = categoryService.getAllCategory();

        assertEquals(categoryEntities.size(), result.size());
        assertEquals(categoryEntities.get(0).getName(), result.get(0).getName());
        assertEquals(categoryEntities.get(1).getName(), result.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById_ExistingCategory_ReturnsCategoryDto() {
        UUID categoryId = UUID.randomUUID();
        CategoryEntity categoryEntity = createMockCategoryEntity(categoryId, "Category 1");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        CategoryDto result = categoryService.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(categoryEntity.getUuid(), result.getUuid());
        assertEquals(categoryEntity.getName(), result.getName());
        assertEquals(categoryEntity.getDescription(), result.getDescription());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testCreateCategory_ValidCreateCategoryDto_ReturnsCreatedCategoryDto() {
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName("Category 1");
        createCategoryDto.setDescription("Description 1");

        CategoryEntity savedCategoryEntity = createMockCategoryEntity(UUID.randomUUID(), createCategoryDto.getName());

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(savedCategoryEntity);

        CategoryDto result = categoryService.createCategory(createCategoryDto);

        assertNotNull(result);
        assertEquals(savedCategoryEntity.getUuid(), result.getUuid());
        assertEquals(savedCategoryEntity.getName(), result.getName());
        assertEquals(savedCategoryEntity.getDescription(), result.getDescription());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void testUpdateCategory_ExistingCategory_ReturnsUpdatedCategoryDto() {
        UUID categoryId = UUID.randomUUID();
        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto();
        updateCategoryDto.setUuid(categoryId);
        updateCategoryDto.setName("Updated Category");
        CategoryEntity existingCategoryEntity = createMockCategoryEntity(categoryId, "Category 1");
        CategoryEntity updatedCategoryEntity = createMockCategoryEntity(categoryId, updateCategoryDto.getName());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategoryEntity));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(updatedCategoryEntity);

        CategoryDto result = categoryService.updateCategory(updateCategoryDto);

        assertNotNull(result);
        assertEquals(updatedCategoryEntity.getUuid(), result.getUuid());
        assertEquals(updatedCategoryEntity.getName(), result.getName());
        assertEquals(updatedCategoryEntity.getDescription(), result.getDescription());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    void testUpdateCategory_NonExistingCategory_ReturnsNull() {
        UUID categoryId = UUID.randomUUID();
        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto();
        updateCategoryDto.setUuid(categoryId);
        updateCategoryDto.setName("Updated Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        CategoryDto result = categoryService.updateCategory(updateCategoryDto);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void testDeleteCategory_ExistingCategory_ReturnsTrue() {
        UUID categoryId = UUID.randomUUID();
        CategoryEntity categoryEntity = createMockCategoryEntity(categoryId, "Category 1");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        boolean result = categoryService.deleteCategory(categoryId);

        assertTrue(result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(categoryEntity);
    }

    @Test
    void testDeleteCategory_NonExistingCategory_ReturnsFalse() {
        UUID categoryId = UUID.randomUUID();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        boolean result = categoryService.deleteCategory(categoryId);

        assertFalse(result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any(CategoryEntity.class));
    }

    private CategoryEntity createMockCategoryEntity(UUID uuid, String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setUuid(uuid);
        categoryEntity.setName(name);
        categoryEntity.setDescription("Description");
        categoryEntity.setCreatedAt(Instant.now());
        categoryEntity.setUpdatedAt(Instant.now());
        return categoryEntity;
    }
}
