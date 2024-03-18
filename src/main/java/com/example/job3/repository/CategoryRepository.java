package com.example.job3.repository;

import com.example.job3.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    @Override
    @EntityGraph(attributePaths = "products")
    List<CategoryEntity> findAll();
}
