package com.example.paymentservice.service.impl;

import com.example.paymentservice.events.OrderCreatedEvent;
import com.example.paymentservice.events.PaymentCompletedEvent;
import com.example.paymentservice.kafka.PaymentEventsProducer;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.repo.PaymentRepo;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentEventsProducer producer;

    @Transactional
    public void handleOrderCreated(OrderCreatedEvent evt) {
        // Idempotency: make sure only one Payment exists per orderId
        Payment payment = paymentRepo.findByOrderId(evt.orderId())
                .orElseGet(() -> {
                    Payment p = new Payment();
                    p.setOrderId(evt.orderId());
                    p.setUserId(evt.userId());
                    p.setAmount(evt.amount());
                    p.setProvider("MOCK");
                    p.setStatus(PaymentStatus.INITIATED);
                    try {
                        return paymentRepo.save(p);
                    } catch (DataIntegrityViolationException e) {
                        // In case of race, load the existing one
                        return paymentRepo.findByOrderId(evt.orderId()).orElseThrow();
                    }
                });

        if (payment.getStatus() == PaymentStatus.SUCCEEDED) {
            log.info("Payment already succeeded for orderId={}; skip.", evt.orderId());
            return;
        }
        if (payment.getStatus() == PaymentStatus.FAILED) {
            log.info("Payment already failed for orderId={}; skip re-processing unless business allows retries.", evt.orderId());
            return;
        }

        // ==== MOCK CHARGE ====
        boolean success = ThreadLocalRandom.current().nextInt(100) < 90; // 90% success
        if (success) {
            payment.setStatus(PaymentStatus.SUCCEEDED);
            payment.setFailureReason(null);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("MOCK_CARD_DECLINED");
        }
        paymentRepo.save(payment);

        // Emit result event
        PaymentCompletedEvent out = new PaymentCompletedEvent(
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getProvider(),
                payment.getFailureReason()
        );
        producer.publish(out);
    }

    @Override
    public ResponseEntity<Payment> getPaymentByOrder(Long orderId) {
       return paymentRepo.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

