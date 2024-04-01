package com.example.job3.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    private UUID userId;
    private UUID basketId;
    private String status;
//    private List<ProductDto> products;
}


