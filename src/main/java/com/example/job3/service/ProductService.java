package com.example.job3.service;


import com.example.job3.dto.CreateProductDto;
import com.example.job3.dto.ProductDto;
import com.example.job3.dto.UpdateProductDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


//    public List<ProductEntity> getAllProduct() {
//        return productRepository.findAll();
//    }
public List<ProductDto> getAllProduct() {
    List<ProductEntity> productEntities = productRepository.findAll();
    return productEntities.stream()
            .map(ModelConverter::toProductDto)
            .collect(Collectors.toList());
}

    public Optional<ProductEntity> getUuidFromProductDto(UUID uuid) {
        return productRepository.findById(uuid);
    }


//    public ProductEntity createProduct(CreateProductDto createProductDto) {
//        // Преобразование объекта CreateProductDto в объект ProductEntity
//        ProductEntity product = new ProductEntity();
//        product.setName(createProductDto.getName());
//        product.setDescription(createProductDto.getDescription());
//        product.setPrice(createProductDto.getPrice());
//
//        // Сохранение продукта в базе данных
//        return productRepository.save(product);
//    }

//    public ProductEntity createProduct(CreateProductDto request) {
//        productRepository.save(ProductEntity.builder()
//                .uuid(UUID.randomUUID())
//                .name(request.getName())
//                .description(request.getDescription())
//                .price(request.getPrice())
//                .category(request.getCategoryId())
//                .build());
//        return null;
//    }
//public void createProduct(CreateProductDto request) {
//    CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElse(null);
//    if (categoryEntity != null) {
//        ProductEntity productEntity = ProductEntity.builder()
//                .uuid(UUID.randomUUID())
//                .name(request.getName())
//                .description(request.getDescription())
//                .price(request.getPrice())
//                .category(categoryEntity)
//                .build();
//        productRepository.save(productEntity);
//    }
//}


    public ProductDto updateProduct(UpdateProductDto productDto) {
        Optional<ProductEntity> productOptional = productRepository.findById(productDto.getUuid());
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            productEntity.setName(productDto.getName());
            productEntity.setDescription(productDto.getDescription());
//            productEntity.setCategory(categoryOptional.get());
            productEntity.setPrice(productDto.getPrice());
            productEntity.setUpdatedAt(Instant.now());
            ProductEntity updatedProduct = productRepository.save(productEntity);
            return ModelConverter.toProductDto(updatedProduct);
        } else {
            return null;
        }
    }

    public boolean deleteProduct(UUID productDto) {
        Optional<ProductEntity> productOptional = productRepository.findById(productDto);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return true;
        } else {
            return false;
        }
    }
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

//    public void createProduct(CreateProductDto createProductDto) {
//        CategoryEntity categoryEntity = categoryRepository.findById(createProductDto.getCategoryId()).orElse(null);
//        if (categoryEntity != null) {
//            ProductEntity productEntity = ProductEntity.builder()
//                    .uuid(UUID.randomUUID())
//                    .name(createProductDto.getName())
//                    .description(createProductDto.getDescription())
//                    .price(createProductDto.getPrice())
//                    .category(categoryEntity)
//                    .build();
//            productRepository.save(productEntity);
//        }
//    }
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

        productRepository.save(productEntity);
    }
}
}



