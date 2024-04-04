package com.example.job3.controller;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.basket.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.service.impl.BasketServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class BasketControllerTest {
    @Mock
    private BasketServiceImpl basketService;

    @InjectMocks
    private BasketController basketController;

    @Test
    public void testGetAllBasket() {
        BasketEntity basket1 = new BasketEntity();
        BasketEntity basket2 = new BasketEntity();
        List<BasketEntity> expectedBaskets = Arrays.asList(basket1, basket2);

        when(basketService.getAllBasket()).thenReturn(expectedBaskets);

        List<BasketEntity> actualBaskets = basketController.getAllBasket();

        assertEquals(expectedBaskets, actualBaskets);
    }

    @Test
    public void testGetBasketById() {
        UUID uuid = UUID.randomUUID();
        BasketDto expectedBasketDto = new BasketDto();

        when(basketService.getBasketById(any())).thenReturn(expectedBasketDto);

        ResponseEntity<BasketDto> response = basketController.getBasketById(uuid);

        assertEquals(expectedBasketDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateBasket() {
        BasketDto basketDto = new BasketDto();

        doNothing().when(basketService).createBasket(any());

        ResponseEntity<Void> response = basketController.createBasket(basketDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateBasket() {
        UUID uuid = UUID.randomUUID();
        UpdateBasketDto basketDto = new UpdateBasketDto();
        BasketDto expectedBasketDto = new BasketDto();

        when(basketService.updateBasket(any())).thenReturn(expectedBasketDto);

        ResponseEntity<BasketDto> response = basketController.updateBasket(uuid, basketDto);

        assertEquals(expectedBasketDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteBasket() {
        UUID uuid = UUID.randomUUID();

        when(basketService.deleteBasket(any())).thenReturn(true);

        ResponseEntity<BasketDto> response = basketController.deleteBasket(uuid);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
