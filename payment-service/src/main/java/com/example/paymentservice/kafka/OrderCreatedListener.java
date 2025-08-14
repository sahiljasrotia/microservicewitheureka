package com.example.paymentservice.kafka;



import com.example.paymentservice.events.OrderCreatedEvent;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order-event", groupId = "payment-service-group")
    public void onOrderCreated(@Payload OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);
        paymentService.handleOrderCreated(event);
    }
}
