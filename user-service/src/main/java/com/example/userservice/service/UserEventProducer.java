package com.example.userservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private static final String TOPIC = "user-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(String userId) {
        String message = "UserCreated:" + userId;
        kafkaTemplate.send(TOPIC, message);
        System.out.println("📤 Sent event to Kafka: " + message);
    }
}
