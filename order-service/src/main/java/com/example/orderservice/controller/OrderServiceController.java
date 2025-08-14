package com.example.orderservice.controller;

import com.example.orderservice.dto.ReqOrderDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderServiceController {

    private final OrderService orderService;

    @GetMapping("/info")
    public String orderInfo() {
        return "Order info from OrderService";
    }




    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody ReqOrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderDto));
    }
}
