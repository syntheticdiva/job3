package com.example.job3.dto.basket;

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
public class BasketDto {
    private UUID uuid;
    private Instant createdAt;
    private Instant updatedAt;

    // Дополнительные методы или конструкторы, если необходимо
}
