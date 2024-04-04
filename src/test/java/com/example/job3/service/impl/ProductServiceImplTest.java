package com.example.job3.service.impl;

import com.example.job3.service.ProductService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.product.CreateProductDto;
import com.example.job3.dto.product.ProductDto;
import com.example.job3.dto.product.UpdateProductDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;
import com.example.job3.utils.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Test
    void testGetAllProduct_ReturnsAllProducts() {
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(createMockProductEntity(UUID.randomUUID(), "Product 1"));
        productEntities.add(createMockProductEntity(UUID.randomUUID(), "Product 2"));

        when(productRepository.findAll()).thenReturn(productEntities);

        List<ProductDto> result = productService.getAllProduct();

        assertEquals(productEntities.size(), result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductsByScore_ReturnsProductsInRange() {
        double minScore = 0.0;
        double maxScore = 5.0;

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(createMockProductEntity(UUID.randomUUID(), "Product 1"));
        productEntities.add(createMockProductEntity(UUID.randomUUID(), "Product 2"));

        when(productRepository.findByScoreBetween(minScore, maxScore)).thenReturn(productEntities);

        List<ProductDto> result = productService.getProductsByScore();

        assertEquals(productEntities.size(), result.size());
        verify(productRepository, times(1)).findByScoreBetween(minScore, maxScore);
    }

    @Test
    void testGetUuidFromProductDto_ExistingProduct_ReturnsProductEntity() {
        UUID productId = UUID.randomUUID();
        ProductEntity productEntity = createMockProductEntity(productId, "Product 1");

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        Optional<ProductEntity> result = productService.getUuidFromProductDto(productId);

        assertTrue(result.isPresent());
        assertEquals(productEntity.getUuid(), result.get().getUuid());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetUuidFromProductDto_NonExistingProduct_ReturnsEmptyOptional() {
        UUID productId = UUID.randomUUID();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<ProductEntity> result = productService.getUuidFromProductDto(productId);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testUpdateProduct_ExistingProduct_ReturnsUpdatedProductDto() {
        UUID productId = UUID.randomUUID();
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setUuid(productId);
        updateProductDto.setName("Updated Product");

        ProductEntity existingProductEntity = createMockProductEntity(productId, "Product 1");
        ProductEntity updatedProductEntity = createMockProductEntity(productId, updateProductDto.getName());

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProductEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedProductEntity);

        ProductDto result = productService.updateProduct(updateProductDto);

        assertNotNull(result);
        assertEquals(updatedProductEntity.getUuid(), result.getUuid());
        assertEquals(updatedProductEntity.getName(), result.getName());
        assertEquals(updatedProductEntity.getDescription(), result.getDescription());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testUpdateProduct_NonExistingProduct_ReturnsNull() {
        UUID productId = UUID.randomUUID();
        UpdateProductDto updateProductDto = new UpdateProductDto();
        updateProductDto.setUuid(productId);
        updateProductDto.setName("Updated Product");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductDto result = productService.updateProduct(updateProductDto);

        assertNull(result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void testDeleteProduct_ExistingProduct_ReturnsTrue() {
        UUID productId = UUID.randomUUID();
        ProductEntity productEntity = createMockProductEntity(productId, "Product 1");

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        boolean result = productService.deleteProduct(productId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(productEntity);
    }

    @Test
    void testDeleteProduct_NonExistingProduct_ReturnsFalse() {
        UUID productId = UUID.randomUUID();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        boolean result = productService.deleteProduct(productId);

        assertFalse(result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any(ProductEntity.class));
    }

    @Test
    void testGetProductsByCategory_ExistingCategory_ReturnsProductsInCategory() {
        UUID categoryId = UUID.randomUUID();
        CategoryEntity categoryEntity = createMockCategoryEntity(categoryId, "Category 1");

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(createMockProductEntity(UUID.randomUUID(), "Product 1"));
        productEntities.add(createMockProductEntity(UUID.randomUUID(), "Product 2"));

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(productRepository.findByCategory(categoryEntity)).thenReturn(productEntities);

        List<ProductEntity> result = productService.getProductsByCategory(categoryId);

        assertEquals(productEntities.size(), result.size());
        assertEquals(categoryEntity, result.get(0).getCategory());
        assertEquals(categoryEntity, result.get(1).getCategory());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, times(1)).findByCategory(categoryEntity);
    }

    @Test
    void testGetProductsByCategory_NonExistingCategory_ReturnsEmptyList() {
        UUID categoryId = UUID.randomUUID();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        List<ProductEntity> result = productService.getProductsByCategory(categoryId);

        assertTrue(result.isEmpty());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, never()).findByCategory(any(CategoryEntity.class));
    }

    private ProductEntity createMockProductEntity(UUID uuid, String name) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setUuid(uuid);
        productEntity.setName(name);
        productEntity.setDescription("Description");
        productEntity.setPrice((long) 10.0);
        productEntity.setCreatedAt(Instant.now());
        productEntity.setUpdatedAt(Instant.now());
        return productEntity;
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
