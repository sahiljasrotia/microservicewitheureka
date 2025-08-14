package com.example.orderservice.service;

import com.example.orderservice.dto.ReqOrderDto;
import com.example.orderservice.events.OrderCreatedEvent;
import com.example.orderservice.events.PaymentCompletedEvent;
import com.example.orderservice.model.Order;

public interface OrderService {
    public Order placeOrder(ReqOrderDto orderDto);

    void handleOrderCreated(PaymentCompletedEvent event);
}
