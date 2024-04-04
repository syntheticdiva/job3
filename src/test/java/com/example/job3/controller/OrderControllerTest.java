package com.example.job3.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.order.CreateOrderDto;
import com.example.job3.dto.order.OrderDto;
import com.example.job3.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderController;

    public OrderControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrder() {
        // Arrange
        List<OrderDto> expectedOrders = new ArrayList<>();
        // Здесь добавьте логику для заполнения expectedOrders с ожидаемыми значениями

        when(orderService.getAllOrder()).thenReturn(expectedOrders);

        // Act
        List<OrderDto> actualOrders = orderController.getAllOrder();

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(orderService, times(1)).getAllOrder();
    }

    @Test
    void testCreateOrder() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String status = "pending";

        CreateOrderDto orderDto = CreateOrderDto.builder()
                .userId(userId)
                .status(status)
                .build();

        // Act
        ResponseEntity<Void> responseEntity = orderController.createOrder(userId, status);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(orderService, times(1)).createOrder(orderDto);
    }

    @Test
    void testGetOrderUuid_ExistingOrder() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        OrderDto expectedOrder = new OrderDto();
        // Здесь добавьте логику для заполнения expectedOrder с ожидаемыми значениями

        when(orderService.getOrderById(uuid)).thenReturn(expectedOrder);

        // Act
        ResponseEntity<OrderDto> responseEntity = orderController.getOrderUuid(uuid);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedOrder, responseEntity.getBody());
        verify(orderService, times(1)).getOrderById(uuid);
    }

    @Test
    void testGetOrderUuid_NonExistingOrder() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(orderService.getOrderById(uuid)).thenReturn(null);

        // Act
        ResponseEntity<OrderDto> responseEntity = orderController.getOrderUuid(uuid);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
        verify(orderService, times(1)).getOrderById(uuid);
    }

    @Test
    void testDeleteOrder_ExistingOrder() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(orderService.deleteOrder(uuid)).thenReturn(true);

        // Act
        ResponseEntity<OrderDto> responseEntity = orderController.deleteOrder(uuid);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
        verify(orderService, times(1)).deleteOrder(uuid);
    }
}