package com.example.job3.repository;

import com.example.job3.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
