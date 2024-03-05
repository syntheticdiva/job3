package com.example.job3.controller;


import com.example.job3.dto.CreateProductDto;
import com.example.job3.dto.ProductDto;
import com.example.job3.dto.UpdateProductDto;
import com.example.job3.entity.ProductEntity;
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


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductEntity> getAllProduct() { return productService.getAllProduct(); }

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDto productDto) {
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
}

