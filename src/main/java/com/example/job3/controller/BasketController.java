package com.example.job3.controller;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.basket.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.service.impl.BasketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketServiceImpl basketService;

    @Autowired
    public BasketController(BasketServiceImpl basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/all")
    public List<BasketEntity> getAllBasket() {
        return basketService.getAllBasket();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BasketDto> getBasketById(@PathVariable UUID uuid) {
        BasketDto basketDto = basketService.getBasketById(uuid);
        if (basketDto != null) {
            return ResponseEntity.ok(basketDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createBasket(@RequestBody BasketDto basketDto) {
        basketService.createBasket(basketDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<BasketDto> updateBasket(@PathVariable UUID uuid, @RequestBody UpdateBasketDto basketDto) {
        BasketDto updateBasket = basketService.updateBasket(basketDto);
        if (updateBasket != null) {
            return ResponseEntity.ok(updateBasket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
