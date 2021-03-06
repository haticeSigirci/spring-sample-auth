package com.hkumru.exercise.controller;

import com.hkumru.exercise.Application;
import com.hkumru.exercise.model.Order;
import com.hkumru.exercise.service.KafkaService;
import com.hkumru.exercise.service.OrderService;
import com.hkumru.exercise.util.SampleProvider;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private OrderService orderService;

    private KafkaService kafkaService;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String ORDER = "{" + "\"id\":\"1\"," + "\"status\":\"ORDER_CREATED\","
            + "\"totalPrice\":\"40.45\"," + "\"orderDate\":\"2018-07-14T17:45:55.9483536\"}";

    private static String accessToken;
    private static HttpHeaders headers;

    @Before
    @SneakyThrows
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilter(filterChainProxy).build();
        accessToken = SampleProvider.obtainAccessToken(mockMvc);
        headers = SampleProvider.setTokenToHeader();
        orderService = mock(OrderService.class);
        kafkaService = mock(KafkaService.class);
    }

    @Test
    @SneakyThrows
    public void getOrderWhenThereIsOrderT() {

        Optional<Order> order = Optional.of(SampleProvider.createSampleOrder());

        doNothing().when(kafkaService).sendEvent(order.get());
        when(orderService.getOrder(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/{id}", 1L)
                .header("Authorization", "Bearer " + accessToken)
                .headers(headers))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @SneakyThrows
    public void createOrderWhenOrderIsValid() {

        Order order = SampleProvider.createSampleOrder();
        doNothing().when(kafkaService).sendEvent(order);
        when(orderService.createOrder(order)).thenReturn(order);

        mockMvc
                .perform(post("/orders")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(contentType)
                        .content(ORDER)
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @SneakyThrows
    public void updateOrderWhenOrderIsValid() {

        Order order = SampleProvider.createSampleOrder();
        doNothing().when(kafkaService).sendEvent(order);

        mockMvc.perform(put("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ORDER)
                        .header("Authorization", "Bearer " + accessToken)
                        .headers(headers))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void deleteOrderWhenDeleteOrderRequest() {
        mockMvc
                .perform(delete("/orders/1")
                        .header("Authorization", "Bearer " + accessToken)
                        .headers(headers))
                .andExpect(status().isOk());
    }

}
