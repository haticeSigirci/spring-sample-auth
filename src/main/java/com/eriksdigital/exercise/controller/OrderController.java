package com.eriksdigital.exercise.controller;

import com.eriksdigital.exercise.exception.OrderNotFoundException;
import com.eriksdigital.exercise.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import com.eriksdigital.exercise.service.OrderService;

@RestController
public class OrderController {

    //todo could be changeable as orderdto

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    @RequestMapping(value = "/orders", method = {RequestMethod.GET, RequestMethod.POST})
    Order getOneOrder(@RequestParam(value = "id") Long id) {
        return service.getOrder(id).orElseThrow(() -> {
            logger.error("Order could not found {} ", id);
            return new OrderNotFoundException(id);
            }
        );
    }

    @PostMapping("/orders")
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
