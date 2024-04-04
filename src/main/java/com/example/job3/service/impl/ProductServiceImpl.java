package com.example.job3.service.impl;


import com.example.job3.dto.product.CreateProductDto;
import com.example.job3.dto.product.ProductDto;
import com.example.job3.dto.product.UpdateProductDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;
import com.example.job3.service.ProductService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductServiceImpl (ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getAllProduct() {
    List<ProductEntity> productEntities = productRepository.findAll();
    return productEntities.stream()
            .map(ModelConverter::toProductDto)
            .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> getProductsByScore() {
        double minScore = 0.0;
        double maxScore = 5.0;

        List<ProductEntity> productEntities = productRepository.findByScoreBetween(minScore, maxScore);
        return productEntities.stream()
                .map(ModelConverter::toProductDto)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<ProductEntity> getUuidFromProductDto(UUID uuid) {
        return productRepository.findById(uuid);
    }
    @Override
    public ProductDto updateProduct(UpdateProductDto productDto) {
        Optional<ProductEntity> productOptional = productRepository.findById(productDto.getId());
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            productEntity.setName(productDto.getName());
            productEntity.setDescription(productDto.getDescription());
            productEntity.setPrice(productDto.getPrice());
            productEntity.setUpdatedAt(Instant.now());
            ProductEntity updatedProduct = productRepository.save(productEntity);
            return ModelConverter.toProductDto(updatedProduct);
        } else {
            return null;
        }
        
    }
    @Override
    public boolean deleteProduct(UUID productDto) {
        Optional<ProductEntity> productOptional = productRepository.findById(productDto);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<ProductEntity> getProductsByCategory(UUID categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
        if (categoryEntity != null) {
            List<ProductEntity> products = productRepository.findByCategory(categoryEntity);
            products.forEach(productEntity -> productEntity.setCategory(categoryEntity));
            return products;
        } else {
            return Collections.emptyList();
        }
    }
     @Override
     public void createProduct(CreateProductDto createProductDto) {
     CategoryEntity categoryEntity = categoryRepository.findById(createProductDto.getCategoryId()).orElse(null);
     if (categoryEntity != null) {
        ProductEntity productEntity = ProductEntity.builder()
                .uuid(UUID.randomUUID())
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .price(createProductDto.getPrice())
                .category(categoryEntity)
                .build();

        productRepository.save(productEntity);}
     }
}



