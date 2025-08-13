package com.example.userservice.service;

import com.example.userservice.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private static final String TOPIC = "user-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(User user) {

        String message = "UserCreated:" + user.toString();
        kafkaTemplate.send(TOPIC, message);
        System.out.println("📤 Sent event to Kafka: " + message);
    }
}
