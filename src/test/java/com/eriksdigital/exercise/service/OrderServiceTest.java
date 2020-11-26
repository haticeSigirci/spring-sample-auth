package com.eriksdigital.exercise.service;

import com.eriksdigital.exercise.model.Order;
import com.eriksdigital.exercise.repository.OrderRepository;
import com.eriksdigital.exercise.util.SampleProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;

public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    KafkaService kafkaService;

    private OrderService orderService;

    private Order order;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository, kafkaService);
        order = SampleProvider.createSampleOrder();
    }

    @Test
    public void createOrder() {
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.createOrder(order);
        assertEquals(result, order);
    }

    @Test
    public void getOrder() {
        Mockito.when(orderRepository.findOrderById(1L)).thenReturn(order);
        Order result = orderService.getOrder(1L).get();
        assertEquals(result, order);
    }

}
