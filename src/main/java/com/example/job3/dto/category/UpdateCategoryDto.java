package com.example.job3.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDto {
    @JsonIgnore
    private UUID uuid;
    private String name;
    private String description;
}
