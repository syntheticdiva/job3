package com.example.job3.repository;

import com.example.job3.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RolesEntity, UUID> {
    // Другие методы, если необходимо
}
