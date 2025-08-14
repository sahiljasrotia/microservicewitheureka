package com.example.orderservice.service.impl;

import com.example.orderservice.dto.ReqOrderDto;
import com.example.orderservice.events.OrderCreatedEvent;
import com.example.orderservice.events.PaymentCompletedEvent;
import com.example.orderservice.events.PaymentStatus;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repo.OrderRepo;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Order placeOrder(ReqOrderDto orderDto) {
        Order order =new Order();
        BeanUtils.copyProperties(orderDto,order);

        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepo.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getPrice() * savedOrder.getQuantity()
        );
        kafkaTemplate.send("order-event",event);
        log.info("Order created event sent: {}", event);

        return savedOrder;
    }

    @Override
    public void handleOrderCreated(PaymentCompletedEvent event) {
        orderRepo.findById(event.orderId())
                .map(order -> {
                    OrderStatus newStatus = switch (event.status()) {
                        case SUCCEEDED -> OrderStatus.CONFIRMED;
                        case FAILED -> OrderStatus.CANCELED;
                        default -> order.getStatus(); // keep existing if other status
                    };
                    order.setStatus(newStatus);
                    return orderRepo.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
