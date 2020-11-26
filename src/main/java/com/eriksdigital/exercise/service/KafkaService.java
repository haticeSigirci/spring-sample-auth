package com.eriksdigital.exercise.service;

import com.eriksdigital.exercise.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${order.topic}")
    private String TOPIC;

    public void sendEvent(Order order) {
        kafkaTemplate.send(TOPIC, order);
    }

}
