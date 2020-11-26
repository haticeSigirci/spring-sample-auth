package com.eriksdigital.exercise.controller;

import com.eriksdigital.exercise.exception.OrderNotFoundException;
import com.eriksdigital.exercise.model.Order;
import com.eriksdigital.exercise.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping(value = "/orders/{id}")
    Order getOneOrder(@PathVariable (value = "id") Long id) {
        return service.getOrder(id).orElseThrow(() -> {
            logger.error("Order could not found {} ", id);
            return new OrderNotFoundException(id);
            }
        );
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    Order newOrder(@RequestBody Order order) {
        return service.createOrder(order);
    }

    @PutMapping("/orders/{id}")
    Order replaceOrder(@RequestBody Order order, @PathVariable Long id) {
        return service.updateOrder(order, id);
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
    }

}
