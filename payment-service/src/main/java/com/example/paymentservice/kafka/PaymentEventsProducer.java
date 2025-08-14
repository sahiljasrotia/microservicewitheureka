package com.example.paymentservice.kafka;


import com.example.paymentservice.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventsProducer {

    private static final String TOPIC = "payment-event";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(PaymentCompletedEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.orderId()), event)
                .whenComplete((res, ex) -> {
                    if (ex != null) log.error("Failed to publish PaymentCompletedEvent {}", event, ex);
                    else log.info("Published PaymentCompletedEvent to {} partition {} offset {}",
                            TOPIC,
                            res.getRecordMetadata().partition(),
                            res.getRecordMetadata().offset());
                });
    }
}
