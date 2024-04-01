package com.example.job3.controller;

import com.example.job3.dto.order.CreateOrderDto;
import com.example.job3.dto.order.OrderDto;
import com.example.job3.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
@GetMapping("/all")
public List<OrderDto> getAllOrder() {
        return orderService.getAllOrder();
    }
@PostMapping("/create")
public ResponseEntity<Void> createOrder(
        @RequestParam("userId") UUID userId,
        @RequestParam("status") String status){

    CreateOrderDto orderDto = CreateOrderDto.builder()
            .userId(userId)
            .status(status)
            .build();
    orderService.createOrder(orderDto);
    return new ResponseEntity<>(HttpStatus.CREATED);}


@GetMapping("/{uuid}")
    public ResponseEntity<OrderDto> getOrderUuid(@PathVariable UUID uuid){
    OrderDto orderDto = orderService.getOrderById(uuid);
    if(orderDto != null) {
        return ResponseEntity.ok(orderDto);
    } else {
        return ResponseEntity.notFound().build();
    }

}
@DeleteMapping("/delete/{uuid}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable UUID uuid){
        boolean deleted = orderService.deleteOrder(uuid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        }
}



