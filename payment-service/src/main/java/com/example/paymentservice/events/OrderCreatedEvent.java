package com.example.paymentservice.events;

public record OrderCreatedEvent(Long orderId, Long userId, double amount) {
}
