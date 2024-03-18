package com.example.job3.repository;

import com.example.job3.entity.CategoryEntity;
import com.example.job3.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategory(CategoryEntity category);
    List<ProductEntity> findAll();
}

