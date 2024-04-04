package com.example.job3.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.product.CreateProductDto;
import com.example.job3.dto.product.ProductDto;
import com.example.job3.dto.product.UpdateProductDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;
import com.example.job3.service.impl.ProductServiceImpl;
import com.example.job3.utils.ModelConverter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    public ProductControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProduct() {
        // Arrange
        List<ProductDto> expectedProducts = new ArrayList<>();
        // Здесь добавьте логику для заполнения expectedProducts с ожидаемыми значениями

        when(productService.getAllProduct()).thenReturn(expectedProducts);

        // Act
        List<ProductDto> actualProducts = productController.getAllProduct();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productService, times(1)).getAllProduct();
    }

    @Test
    void testCreateProduct() {
        // Arrange
        String name = "Test Product";
        String description = "Test Description";
        Long price = 100L;
        UUID categoryId = UUID.randomUUID();

        CreateProductDto productDto = CreateProductDto.builder()
                .name(name)
                .description(description)
                .price(price)
                .categoryId(categoryId)
                .build();

        // Act
        ResponseEntity<Void> responseEntity = productController.createProduct(name, description, price, categoryId);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(productService, times(1)).createProduct(productDto);
    }

    @Test
    void testGetProductUuid_ExistingProduct() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        ProductEntity productEntity = new ProductEntity();
        // Здесь добавьте логику для заполнения productEntity с ожидаемыми значениями

        when(productService.getUuidFromProductDto(uuid)).thenReturn(Optional.of(productEntity));

        // Act
        ResponseEntity<ProductDto> responseEntity = productController.getProductUuid(uuid);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService, times(1)).getUuidFromProductDto(uuid);
    }

    @Test
    void testGetProductUuid_NonExistingProduct() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(productService.getUuidFromProductDto(uuid)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProductDto> responseEntity = productController.getProductUuid(uuid);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        assertFalse(responseEntity.hasBody());
        verify(productService, times(1)).getUuidFromProductDto(uuid);
    }

    @Test
    void testUpdateProduct_ExistingProduct() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String name = "Updated Product";
        String description = "Updated Description";
        Long price = 200L;

        UpdateProductDto productDto = UpdateProductDto.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .price(price)
                .build();

        ProductDto updatedProduct = new ProductDto();
        // Здесь добавьте логику для заполнения updatedProduct с ожидаемыми значениями

        when(productService.updateProduct(productDto)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<ProductDto> responseEntity = productController.updateProduct(uuid, name, description, price);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService, times(1)).updateProduct(productDto);
    }

    @Test
    void testUpdateProduct_NonExistingProduct() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String name = "Updated Product";
        String description = "Updated Description";
        Long price = 200L;

        UpdateProductDto productDto = UpdateProductDto.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .price(price)
                .build();

        when(productService.updateProduct(productDto)).thenReturn(null);

        // Act
        ResponseEntity<ProductDto> responseEntity = productController.updateProduct(uuid, name, description, price);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
        verify(productService, times(1)).updateProduct(productDto);
    }

    @Test
    void testDeleteProduct_ExistingProduct() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(productService.deleteProduct(uuid)).thenReturn(true);

        // Act
        ResponseEntity<ProductDto> responseEntity = productController.deleteProduct(uuid);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
        verify(productService, times(1)).deleteProduct(uuid);
    }

    @Test
    void testDeleteProduct_NonExistingProduct() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(productService.deleteProduct(uuid)).thenReturn(false);

        // Act
        ResponseEntity<ProductDto> responseEntity = productController.deleteProduct(uuid);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
        verify(productService, times(1)).deleteProduct(uuid);
    }

    @Test
    void testGetProductsByCategory() {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        List<ProductEntity> expectedProducts = new ArrayList<>();
        // Здесь добавьте логику для заполнения expectedProducts с ожидаемыми значениями

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setUuid(categoryId);

        when(productService.getProductsByCategory(categoryEntity.getUuid())).thenReturn(expectedProducts);

        // Act
        List<ProductEntity> actualProducts = productController.getProductsByCategory(categoryId);

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productService, times(1)).getProductsByCategory(categoryEntity.getUuid());
    }

    @Test
    void testGetProductsByScore() {
        // Arrange
        List<ProductDto> expectedProducts = new ArrayList<>();
        // Здесь добавьте логику для заполнения expectedProducts с ожидаемыми значениями

        when(productService.getProductsByScore()).thenReturn(expectedProducts);

        // Act
        List<ProductDto> actualProducts = productController.getProductsByScore();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productService, times(1)).getProductsByScore();
    }

}
