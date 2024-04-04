package com.example.job3.controller;

import org.junit.jupiter.api.Test;

import com.example.job3.dto.category.CategoryDto;
import com.example.job3.dto.category.CreateCategoryDto;
import com.example.job3.dto.category.UpdateCategoryDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryControllerTest{

    @Test
    public void testGetAllCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание тестовых данных
        List<CategoryEntity> categories = new ArrayList<>();
        categories.add(new CategoryEntity(UUID.randomUUID(), "Category1", "Description1"));
        categories.add(new CategoryEntity(UUID.randomUUID(), "Category2", "Description2"));

        // Задание поведения мок-объекта
        when(categoryService.getAllCategory()).thenReturn(categories);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Вызов метода, который необходимо протестировать
        List<CategoryEntity> result = categoryController.getAllCategory();

        // Проверка результатов
        assertEquals(categories, result);
    }

    @Test
    public void testGetCategoryById_ExistingCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание тестовых данных
        UUID categoryId = UUID.randomUUID();
        CategoryDto categoryDto = new CategoryDto(categoryId, "Category1", "Description1");

        // Задание поведения мок-объекта
        when(categoryService.getCategoryById(categoryId)).thenReturn(categoryDto);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Вызов метода, который необходимо протестировать
        ResponseEntity<CategoryDto> result = categoryController.getCategoryById(categoryId);

        // Проверка результатов
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(categoryDto, result.getBody());
    }

    @Test
    public void testGetCategoryById_NonExistingCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Задание поведения мок-объекта
        when(categoryService.getCategoryById(any(UUID.class))).thenReturn(null);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Вызов метода, который необходимо протестировать
        ResponseEntity<CategoryDto> result = categoryController.getCategoryById(UUID.randomUUID());

        // Проверка результатов
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testCreateCategory() {
        // Создание мок-объекта для CategoryServiceImpl

        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Вызов метода, который необходимо протестировать
        ResponseEntity<Void> result = categoryController.createCategory("Category1", "Description1");

        // Проверка результатов
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testUpdateCategory_ExistingCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Создание тестовых данных
        UUID categoryId = UUID.randomUUID();
        CategoryDto categoryDto = new CategoryDto(categoryId, "Category1", "Description1");

        // Задание поведения мок-объекта
        when(categoryService.updateCategory(any(UpdateCategoryDto.class))).thenReturn(categoryDto);

        // Вызов метода, который необходимо протестировать
        ResponseEntity<CategoryDto> result = categoryController.updateCategory(
                categoryId, "Updated Category", "Updated Description");

        // Проверка результатов
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(categoryDto, result.getBody());
    }

    @Test
    public void testUpdateCategory_NonExistingCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Задание поведения мок-объекта
        when(categoryService.updateCategory(any(UpdateCategoryDto.class))).thenReturn(null);

        // Вызов метода, который необходимо протестировать
        ResponseEntity<CategoryDto> result = categoryController.updateCategory(
                UUID.randomUUID(), "Updated Category", "Updated Description");

        // Проверка результатов
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteCategory_ExistingCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Задание поведения мок-объекта
        when(categoryService.deleteCategory(any(UUID.class))).thenReturn(true);

        // Вызов метода, который необходимо протестировать
        ResponseEntity<CategoryDto> result = categoryController.deleteCategory(UUID.randomUUID());

        // Проверка результатов
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testDeleteCategory_NonExistingCategory() {
        // Создание мок-объекта для CategoryServiceImpl
        CategoryServiceImpl categoryService = mock(CategoryServiceImpl.class);

        // Создание экземпляра CategoryController с использованием мок-объекта
        CategoryController categoryController = new CategoryController(categoryService);

        // Задание поведения мок-объекта
        when(categoryService.deleteCategory(any(UUID.class))).thenReturn(false);
    }
}