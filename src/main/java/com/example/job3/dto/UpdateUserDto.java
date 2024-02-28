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
public class UpdateUserDto {
    private UUID uuid;
    private String name;
    private String surname;
    private Short age;
    private Instant createdAt;
    private Instant updatedAt;

    public UUID getId() {
        return null;
    }

    // Дополнительные методы или конструкторы, если необходимо
}