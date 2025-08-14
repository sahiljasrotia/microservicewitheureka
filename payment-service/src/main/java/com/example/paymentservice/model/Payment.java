package com.example.paymentservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "uk_order", columnList = "orderId", unique = true)
})
@Getter @Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;      // unique for idempotency
    private Long userId;
    private Double amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String provider;   // e.g., MOCK, RAZORPAY, STRIPE
    private String failureReason;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @Version
    private Long version;

    @PrePersist
    void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }
    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }


}

