package com.example.job3.dto.product;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID uuid;
    private String name;
    private String description;
    private Long price;
    private Instant createdAt;
    private Instant updatedAt;
    private String categoryId;
    @Setter
    @Getter
    private int score;

    // Дополнительные методы или конструкторы, если необходимо
}