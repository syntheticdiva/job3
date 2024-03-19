package com.example.job3.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    private String name;
    private String description;
    private Long price;
    private String categoryId;
    @JsonIgnore
    private UUID uuid;


    @JsonIgnore
    public UUID getId() { return uuid;
    }
}
