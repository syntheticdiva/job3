package com.example.job3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty ("uuid")
    private UUID uuid;
    @JsonProperty ("name")
    private String name;
    @JsonProperty ("description")
    private String description;
    @JsonProperty ("createdAt")
    private Instant createdAt;
    @JsonProperty ("updatedAt")
    private Instant updatedAt;

    // Дополнительные методы или конструкторы, если необходимо
}
