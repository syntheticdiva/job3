package com.example.job3.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.basket.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.repository.BasketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.basket.UpdateBasketDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.repository.BasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceImplTest {

    @Mock
    private BasketRepository basketRepository;

    private BasketServiceImpl basketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        basketService = new BasketServiceImpl(basketRepository);
    }

    @Test
    void testGetAllBasket() {
        // Arrange
        List<BasketEntity> expectedBaskets = new ArrayList<>();
        expectedBaskets.add(new BasketEntity());
        when(basketRepository.findAll()).thenReturn(expectedBaskets);

        // Act
        List<BasketEntity> actualBaskets = basketService.getAllBasket();

        // Assert
        assertEquals(expectedBaskets, actualBaskets);
        verify(basketRepository, times(1)).findAll();
    }

    @Test
    void testGetBasketById_BasketFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        BasketEntity expectedBasket = new BasketEntity();
        when(basketRepository.findById(uuid)).thenReturn(Optional.of(expectedBasket));

        // Act
        BasketDto actualBasket = basketService.getBasketById(uuid);

        // Assert
        assertNotNull(actualBasket);
        verify(basketRepository, times(1)).findById(uuid);
    }

    @Test
    void testGetBasketById_BasketNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(basketRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        BasketDto actualBasket = basketService.getBasketById(uuid);

        // Assert
        assertNull(actualBasket);
        verify(basketRepository, times(1)).findById(uuid);
    }

    @Test
    void testCreateBasket() {
        // Arrange
        BasketDto createBasketDto = new BasketDto();

        // Act
        basketService.createBasket(createBasketDto);

        // Assert
        verify(basketRepository, times(1)).save(any(BasketEntity.class));
    }

    @Test
    void testUpdateBasket_BasketExists() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UpdateBasketDto updateBasketDto = new UpdateBasketDto();
        updateBasketDto.setUuid(uuid);

        BasketEntity basketEntity = new BasketEntity();
        when(basketRepository.findById(uuid)).thenReturn(Optional.of(basketEntity));
        when(basketRepository.save(basketEntity)).thenReturn(basketEntity);

        // Act
        BasketDto updatedBasket = basketService.updateBasket(updateBasketDto);

        // Assert
        assertNotNull(updatedBasket);
        verify(basketRepository, times(1)).findById(uuid);
        verify(basketRepository, times(1)).save(basketEntity);
    }

    @Test
    void testUpdateBasket_BasketNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UpdateBasketDto updateBasketDto = new UpdateBasketDto();
        updateBasketDto.setUuid(uuid);

        when(basketRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> basketService.updateBasket(updateBasketDto));
        verify(basketRepository, times(1)).findById(uuid);
    }

    @Test
    void testDeleteBasket_BasketExists() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        BasketEntity basketEntity = new BasketEntity();
        when(basketRepository.findById(uuid)).thenReturn(Optional.of(basketEntity));

        // Act
        boolean deleted = basketService.deleteBasket(uuid);

        // Assert
        assertTrue(deleted);
        verify(basketRepository, times(1)).findById(uuid);
        verify(basketRepository, times(1)).delete(basketEntity);
    }

    @Test
    void testDeleteBasket_BasketNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(basketRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        boolean deleted = basketService.deleteBasket(uuid);

        // Assert
        assertFalse(deleted);
        verify(basketRepository, times(1)).findById(uuid);
        verifyNoMoreInteractions(basketRepository);
    }
}
