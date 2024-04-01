package com.example.job3.service;

import com.example.job3.dto.order.CreateOrderDto;
import com.example.job3.dto.order.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> getAllOrder();

    OrderDto getOrderById(UUID uuid);

    //    @Override
//    public void createOrder(CreateOrderDto createOrderDto) {
//        Optional<BasketEntity> basketEntity = basketRepository.findById(createOrderDto.getBasketId());
//        OrderEntity orderEntity = ModelConverter.toCreateOrderEntity(createOrderDto);
//        orderEntity.setUuid(UUID.randomUUID());
//        orderEntity.setStatus(createOrderDto.getStatus());
//        orderEntity.setCreatedAt(Instant.now());
//        orderEntity.setUpdatedAt(Instant.now());
//        basketEntity.calculateTotalAmount();
//        orderEntity.setTotalAmountFromBasket(basketEntity);
//        OrderEntity savedOrder = orderRepository.save(orderEntity);
//        ModelConverter.toOrderDto(savedOrder);
//    }
    void createOrder(CreateOrderDto createOrderDto);

    void createOrder(OrderDto createOrderDto);

    boolean deleteOrder(UUID orderDto);
}