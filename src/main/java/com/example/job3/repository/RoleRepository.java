package com.example.job3.repository;




import com.example.job3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(Role.RoleName roleName);
}
