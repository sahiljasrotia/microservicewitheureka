package com.example.paymentservice.events;

import com.example.paymentservice.model.PaymentStatus;

public record PaymentCompletedEvent(
        Long orderId,
        Long userId,
        Double amount,
        String currency,
        PaymentStatus status,
        String provider,
        String reason // null when success, error code/message when failed
) {}
