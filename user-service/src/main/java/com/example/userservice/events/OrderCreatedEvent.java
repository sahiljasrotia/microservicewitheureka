package com.example.userservice.events;

public record OrderCreatedEvent(Long orderId, Long userId, double amount) {
}
