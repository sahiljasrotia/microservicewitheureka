package com.example.orderservice.events;



public record PaymentCompletedEvent(
        Long orderId,
        Long userId,
        Double amount,
        String currency,
        PaymentStatus status,
        String provider,
        String reason // null when success, error code/message when failed
) {}
