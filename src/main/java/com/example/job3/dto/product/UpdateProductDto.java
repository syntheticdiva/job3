package com.example.job3.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @Setter
    @Getter
    @JsonIgnore
    private UUID uuid;

    public UpdateProductDto(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonIgnore
    public UUID getId() { return uuid;
    }


}
