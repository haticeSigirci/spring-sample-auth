package com.eriksdigital.exercise.service;

import com.eriksdigital.exercise.model.Order;
import com.eriksdigital.exercise.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final OrderRepository repository;

    private final KafkaService kafkaService;

    OrderService(OrderRepository repository, KafkaService kafkaService) {
        this.repository = repository;
        this.kafkaService = kafkaService;
    }
    public Optional<Order> getOrder(Long id) {
        return repository.findOrderById(id) == null ? Optional.empty() : Optional.of(repository.findOrderById(id));
    }

    public Order createOrder(Order newOrder) {
        logger.info("Order Created with id: {}", newOrder.getId());
        kafkaService.sendEvent(newOrder);
        return repository.save(newOrder);
    }

    public Order updateOrder(Order updateOrder, Long id) {
        kafkaService.sendEvent(updateOrder);
        return getOrder(id)
                .map(order -> {
                    logger.info("Order Updated with id: {}", updateOrder.getId());
                    order.setStatus(updateOrder.getStatus());
                    order.setOrderDate(updateOrder.getOrderDate());
                    order.setTotalPrice(updateOrder.getTotalPrice());
                    return repository.save(order);
                }).orElseGet(() -> {
                    logger.info("Order Created with id: {}", updateOrder.getId());
                    updateOrder.setId(id);
                     return repository.save(updateOrder);
                });
    }

    public void deleteOrder(Long id) {
        logger.info("Order Deleted with id: {}", id);
        repository.delete(id);
    }
}
