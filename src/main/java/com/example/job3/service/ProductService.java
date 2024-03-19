package com.example.job3.service;

import com.example.job3.dto.product.CreateProductDto;
import com.example.job3.dto.product.ProductDto;
import com.example.job3.dto.product.UpdateProductDto;
import com.example.job3.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAllProduct();

    Optional<ProductEntity> getUuidFromProductDto(UUID uuid);

    ProductDto updateProduct(UpdateProductDto productDto);

    boolean deleteProduct(UUID productDto);

    List<ProductEntity> getProductsByCategory(UUID categoryId);

    void createProduct(CreateProductDto createProductDto);
}
