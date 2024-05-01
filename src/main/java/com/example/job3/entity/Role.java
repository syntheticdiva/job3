package com.example.job3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@Getter
@Setter
public class Role {
    @Id
    private UUID uuid;
    private String roleName;

    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public Role(UUID uuid, String roleName) {
        this.uuid = uuid;
        this.roleName = roleName.toString();
    }

    public String getRoleName() {
        return roleName;
    }
}
