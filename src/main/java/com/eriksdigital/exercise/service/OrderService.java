package com.eriksdigital.exercise.service;

import com.eriksdigital.exercise.model.Order;
import com.eriksdigital.exercise.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repository;

    OrderService(OrderRepository repository) {
        this.repository = repository;
    }
    public Optional<Order> getOrder(Long id) {
        return repository.findOrderById(id) == null ? Optional.empty() : Optional.of(repository.findOrderById(id));
    }

    public Order createOrder(Order newOrder) {
        return repository.save(newOrder);
    }

    public Order updateOrder(Order updateOrder, Long id) {
        return getOrder(id)
                .map(order -> {
                    order.setStatus(updateOrder.getStatus());
                    order.setOrderDate(updateOrder.getOrderDate());
                    order.setTotalPrice(updateOrder.getTotalPrice());
                    return repository.save(order);
                }).orElseGet(() -> {
                     updateOrder.setId(id);
                     return repository.save(updateOrder);
                });
    }

    public void deleteOrder(Long id) {
        repository.delete(id);
    }
}
