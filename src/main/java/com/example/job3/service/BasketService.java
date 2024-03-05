package com.example.job3.service;

import com.example.job3.dto.BasketDto;
import com.example.job3.dto.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.repository.BasketRepository;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService {
    private final BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<BasketEntity> getAllBasket() {
        return basketRepository.findAll();
    }

    public BasketDto getBasketById(UUID uuid) {
        Optional<BasketEntity> basketOptional = basketRepository.findById(uuid);
        return basketOptional.map(ModelConverter::toBasketDto).orElse(null);
    }

    public BasketDto createBasket(BasketDto createBasketDto) {
        BasketEntity basketEntity = ModelConverter.toCreateBasketEntity(createBasketDto);
        basketEntity.setUuid(UUID.randomUUID());
        basketEntity.setCreatedAt(Instant.now());
        basketEntity.setUpdatedAt(Instant.now());
        BasketEntity savedBasket = basketRepository.save(basketEntity);
        return ModelConverter.toBasketDto(savedBasket);}

    public BasketDto updateBasket(UpdateBasketDto updateBasketDto) {
        Optional<BasketEntity> basketOptional = basketRepository.findById(updateBasketDto.getUuid());
        if (basketOptional.isPresent()) {
            BasketEntity basketEntity = basketOptional.get();
//            basketEntity.setProducts(updateBasketDto.getProducts());
            BasketEntity updatedBasket = basketRepository.save(basketEntity);
            return ModelConverter.toBasketDto(updatedBasket);
        } else {
            throw new IllegalArgumentException("Basket not found with UUID: " + updateBasketDto.getUuid());
        }
    }
}
