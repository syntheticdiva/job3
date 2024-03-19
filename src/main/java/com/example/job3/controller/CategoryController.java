package com.example.job3.controller;

import com.example.job3.dto.category.CategoryDto;
import com.example.job3.dto.category.CreateCategoryDto;
import com.example.job3.dto.category.UpdateCategoryDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) { this.categoryService = categoryService;}

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
    public ResponseEntity<Void> createCategory(
            @RequestParam ("name") String name,
            @RequestParam("description") String description){

        CreateCategoryDto categoryDto = CreateCategoryDto.builder()
                .name(name)
                .description(description)
                .build();
        categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable ("uuid") UUID uuid,
            @RequestParam ("name") String name,
            @RequestParam ("description") String description) {
        UpdateCategoryDto categoryDto = UpdateCategoryDto.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .build();
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
