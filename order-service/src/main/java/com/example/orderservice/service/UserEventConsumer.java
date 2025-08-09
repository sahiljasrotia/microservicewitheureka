package com.example.orderservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    @KafkaListener(topics = "user-events", groupId = "order-service-group")
    public void consume(String message) {
        System.out.println("📥 Received event: " + message);

        if (message.startsWith("UserCreated:")) {
            String userId = message.split(":")[1];
            // Example action: create initial order for new user
            System.out.println("📦 Creating welcome order for user: " + userId);
        }
    }
}
