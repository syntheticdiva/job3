package com.example.job3.service.impl;

import com.example.job3.dto.order.CreateOrderDto;
import com.example.job3.dto.order.OrderDto;
import com.example.job3.entity.OrderEntity;
import com.example.job3.repository.OrderRepository;
import com.example.job3.service.OrderService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl (OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @Override
    public List<OrderDto> getAllOrder(){
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDto> orderDto = orderEntities.stream()
                .map(ModelConverter::toOrderDto)
                .collect(Collectors.toList());
        return orderDto;
    }
    @Override
    public OrderDto getOrderById(UUID uuid){
        Optional<OrderEntity> orderOptional = orderRepository.findById(uuid);
        return orderOptional.map(ModelConverter::toOrderDto).orElse(null);
    }
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
    @Override
    public void createOrder(CreateOrderDto createOrderDto) {
        OrderEntity orderEntity = ModelConverter.toCreateOrderEntity(createOrderDto);
        orderEntity.setUuid(UUID.randomUUID());
        orderEntity.setStatus(createOrderDto.getStatus());
        orderEntity.setCreatedAt(Instant.now());
        orderEntity.setUpdatedAt(Instant.now());
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        ModelConverter.toOrderDto(savedOrder);
    }

    @Override
    public void createOrder(OrderDto createOrderDto) {

    }

    @Override
    public boolean deleteOrder(UUID orderDto) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(orderDto);
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            return true;
        } else {
            return false;
        }
    }
}