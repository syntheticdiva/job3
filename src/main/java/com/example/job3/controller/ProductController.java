package com.example.job3.controller;


import com.example.job3.dto.CreateProductDto;
import com.example.job3.dto.ProductDto;
import com.example.job3.dto.UpdateProductDto;
import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import com.example.job3.repository.CategoryRepository;
import com.example.job3.repository.ProductRepository;

import com.example.job3.service.ProductService;
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
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;




    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository, ProductRepository productRepository) {

        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    @GetMapping("/all")
    public List<ProductDto> getAllProduct() {
        return productService.getAllProduct(); }

//    @PostMapping("/create")
//    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDto productDto) {
//        productService.createProduct(productDto);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
@PostMapping("/create")
public ResponseEntity<Void> createProduct(@RequestBody CreateProductDto createProductDto) {
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    } else {
        return ResponseEntity.notFound().build();
    }


}
    @GetMapping("/{id}/uuid")
    public ResponseEntity<ProductDto> getProductUuid(@PathVariable("id") UUID productId) {
        Optional<ProductEntity> productEntityOptional = productService.getUuidFromProductDto(productId);
        if (productEntityOptional.isPresent()) {
            var productDto = ModelConverter.toProductDto(productEntityOptional.get());
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID uuid, @RequestBody UpdateProductDto productDto){
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

}

