package com.example.job3.repository;

import com.example.job3.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketRepository extends JpaRepository<BasketEntity, UUID> {
}

