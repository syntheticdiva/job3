package com.example.job3.service;


import com.example.job3.dto.CreateProductDto;
import com.example.job3.dto.CreateUserDto;
import com.example.job3.dto.ProductDto;
import com.example.job3.dto.UpdateProductDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.entity.UserEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    //    public ProductDto getProductById(UUID uuid) {
//        Optional<ProductEntity> productOptional = productRepository.findById(uuid);
//        return productOptional.map(productEntity -> ProductMapper.INSTANCE.toProductDto(productEntity.getUuid())).orElse(null);
//    }
//    public Optional<ProductEntity> getUuidFromProductDto(UUID uuid){
//        var a = productRepository.findById(uuid);
//        return a;
//    }
    public Optional<ProductEntity> getUuidFromProductDto(UUID uuid) {
        return productRepository.findById(uuid);
    }


    //    public void createProduct (CreateProductDto request){
//        productRepository.save(ProductEntity.builder()
//                .uuid(UUID.randomUUID())
//                .name(request.getName())
//                .description(request.getDescription())
//                .category(request.getCategoryId())
//                .price(request.getPrice())
//                .build());
//    }
//    public void createProduct(CreateProductDto request) {
//        CategoryEntity category = categoryRepository.findByUuid(request.getCategoryUuid()).orElse(null);
//        if (category != null) {
//            productRepository.save(ProductEntity.builder()
//                    .uuid(UUID.randomUUID())
//                    .name(request.getName())
//                    .description(request.getDescription())
////                .category(category)
//                    .price(request.getPrice())
//                    .build());
//        } else {
//
//        }
//    }
    public void createProduct(CreateProductDto request) {
        productRepository.save(ProductEntity.builder()
                .uuid(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build());
    }


    public ProductDto updateProduct(UpdateProductDto productDto) {
        Optional<ProductEntity> productOptional = productRepository.findById(productDto.getId());
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
}
//    private ProductDto mapProductToDto(ProductEntity productEntity) {
//        return ProductDto.builder()
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .category(productEntity.getCategory())
//                .price(productEntity.getPrice())
//                .build();
//    }
//    private ProductDto mapEntityToDto(ProductEntity productEntity) {
//        return ProductDto.builder()
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .category(productEntity.getCategory())
//                .price(productEntity.getPrice())
//                .build();
//    }


