package com.example.userservice.service;

import com.example.userservice.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final UserService userService;

    @KafkaListener(topics = "order-event", groupId = "user-service-group")
    public void consumeOrderEvent(OrderCreatedEvent event) {
        log.info("Received Order Event in User Service: {}", event);

        // Example action: deduct balance, update loyalty points, etc.
         userService.updateUserBalance(event.userId(), event.amount());
    }
}
