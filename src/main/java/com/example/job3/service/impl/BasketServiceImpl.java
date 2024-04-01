package com.example.job3.service.impl;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.basket.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.repository.BasketRepository;
import com.example.job3.service.BasketService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Component
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository) {

        this.basketRepository = basketRepository;
    }

    @Override
    public List<BasketEntity> getAllBasket() {
        return basketRepository.findAll();
    }

    @Override
    public BasketDto getBasketById(UUID uuid) {
        Optional<BasketEntity> basketOptional = basketRepository.findById(uuid);
        return basketOptional.map(ModelConverter::toBasketDto).orElse(null);
    }

    @Override
    public void createBasket(BasketDto createBasketDto) {
        BasketEntity basketEntity = ModelConverter.toCreateBasketEntity(createBasketDto);
        basketEntity.setUuid(UUID.randomUUID());
        basketEntity.setCreatedAt(Instant.now());
        basketEntity.setUpdatedAt(Instant.now());
        BasketEntity savedBasket = basketRepository.save(basketEntity);
        ModelConverter.toBasketDto(savedBasket);
    }

    @Override
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

    @Override
    public boolean deleteBasket(UUID basketDto) {
        Optional<BasketEntity> basketOptional = basketRepository.findById(basketDto);
        if (basketOptional.isPresent()) {
            basketRepository.delete(basketOptional.get());
            return true;
        } else {
            return false;
        }
    }
}


