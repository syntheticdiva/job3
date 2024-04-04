package com.example.job3.service.impl;

import com.example.job3.service.OrderService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.order.CreateOrderDto;
import com.example.job3.dto.order.OrderDto;
import com.example.job3.entity.OrderEntity;
import com.example.job3.repository.OrderRepository;
import com.example.job3.utils.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void testGetAllOrder_ReturnsAllOrders() {
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(createMockOrderEntity(UUID.randomUUID()));
        orderEntities.add(createMockOrderEntity(UUID.randomUUID()));

        when(orderRepository.findAll()).thenReturn(orderEntities);

        List<OrderDto> result = orderService.getAllOrder();

        assertEquals(orderEntities.size(), result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById_ExistingOrder_ReturnsOrderDto() {
        UUID orderId = UUID.randomUUID();
        OrderEntity orderEntity = createMockOrderEntity(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        OrderDto result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderEntity.getUuid(), result.getUuid());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderById_NonExistingOrder_ReturnsNull() {
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderDto result = orderService.getOrderById(orderId);

        assertNull(result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testCreateOrder_ValidCreateOrderDto_CreatesOrderEntity() {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setStatus("Pending");

        OrderEntity savedOrderEntity = createMockOrderEntity(UUID.randomUUID());

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrderEntity);

        orderService.createOrder(createOrderDto);

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void testDeleteOrder_ExistingOrder_ReturnsTrue() {
        UUID orderId = UUID.randomUUID();
        OrderEntity orderEntity = createMockOrderEntity(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        boolean result = orderService.deleteOrder(orderId);

        assertTrue(result);
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).delete(orderEntity);
    }

    @Test
    void testDeleteOrder_NonExistingOrder_ReturnsFalse() {
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        boolean result = orderService.deleteOrder(orderId);

        assertFalse(result);
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).delete(any(OrderEntity.class));
    }

    private OrderEntity createMockOrderEntity(UUID uuid) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(uuid);
        orderEntity.setStatus("Pending");
        orderEntity.setCreatedAt(Instant.now());
        orderEntity.setUpdatedAt(Instant.now());
        return orderEntity;
    }
}
