package com.example.job3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private UUID uuid;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    // Дополнительные методы или конструкторы, если необходимо
}
