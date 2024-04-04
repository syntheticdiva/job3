package com.example.job3.controller;


import com.example.job3.dto.product.CreateProductDto;
import com.example.job3.dto.product.ProductDto;
import com.example.job3.dto.product.UpdateProductDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;

import com.example.job3.service.impl.ProductServiceImpl;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService, CategoryRepository categoryRepository, ProductRepository productRepository) {

        this.productService = productService;
    }
    @GetMapping("/all")
    public List<ProductDto> getAllProduct() {
        return productService.getAllProduct(); }

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(
        @RequestParam ("name") String name,
        @RequestParam ("description") String description,
        @RequestParam ("price") Long price,
        @RequestParam ("categoryId") UUID categoryId) {

     CreateProductDto productDto = CreateProductDto.builder()
             .name(name)
             .description(description)
             .price(price)
             .categoryId(categoryId)
             .build();
     productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDto> getProductUuid(@PathVariable("uuid") UUID productId) {
        Optional<ProductEntity> productEntityOptional = productService.getUuidFromProductDto(productId);
        if (productEntityOptional.isPresent()) {
            var productDto = ModelConverter.toProductDto(productEntityOptional.get());
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable ("uuid") UUID uuid,
            @RequestParam ("name") String name,
            @RequestParam ("description") String description,
            @RequestParam ("price") Long price){
    UpdateProductDto productDto = UpdateProductDto.builder()
            .uuid(uuid)
            .name(name)
            .description(description)
            .price(price)
            .build();
    ProductDto updateProduct = productService.updateProduct(productDto);
        if (updateProduct != null) {
            return ResponseEntity.ok(updateProduct);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable UUID uuid) {
        boolean deleted = productService.deleteProduct(uuid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/byCategory/{categoryId}")
    public List<ProductEntity> getProductsByCategory(@PathVariable UUID categoryId) {
        CategoryEntity category = new CategoryEntity();
        category.setUuid(categoryId);
        return productService.getProductsByCategory(category.getUuid());
    }
    @GetMapping("/byscore")
    public List<ProductDto> getProductsByScore() {
        return productService.getProductsByScore();
    }

}

