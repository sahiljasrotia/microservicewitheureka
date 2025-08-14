package com.example.orderservice.events;



import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-event", groupId = "order-service-group")
    public void onOrderCreated(@Payload PaymentCompletedEvent event) {
        log.info("Received PaymentCompletedEvent: {}", event);
        orderService.handleOrderCreated(event);
    }
}
