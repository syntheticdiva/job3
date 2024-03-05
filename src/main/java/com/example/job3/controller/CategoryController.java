package com.example.job3.controller;

import com.example.job3.dto.CategoryDto;
import com.example.job3.dto.CreateCategoryDto;
import com.example.job3.dto.UpdateCategoryDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService;}

    @GetMapping("/all")
    public List<CategoryEntity> getAllCategory() { return categoryService.getAllCategory();}

    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable UUID uuid) {
        CategoryDto categoryDto = categoryService.getCategoryById(uuid);
        if (categoryDto != null) {
            return ResponseEntity.ok(categoryDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(@RequestBody CreateCategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable UUID uuid, @RequestBody UpdateCategoryDto categoryDto) {
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto);
        if (updateCategory != null) {
            return ResponseEntity.ok(updateCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable UUID uuid) {
        boolean deleted = categoryService.deleteCategory(uuid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
