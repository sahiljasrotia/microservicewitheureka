package com.example.paymentservice.service;

import com.example.paymentservice.events.OrderCreatedEvent;
import com.example.paymentservice.model.Payment;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    void handleOrderCreated(OrderCreatedEvent event);

    ResponseEntity<Payment> getPaymentByOrder(Long orderId);
}
