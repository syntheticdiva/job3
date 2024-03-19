package com.example.job3.service;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.basket.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;

import java.util.List;
import java.util.UUID;

public interface BasketService {
    List<BasketEntity> getAllBasket();

    BasketDto getBasketById(UUID uuid);

    BasketDto createBasket(BasketDto createBasketDto);

    BasketDto updateBasket(UpdateBasketDto updateBasketDto);
}
