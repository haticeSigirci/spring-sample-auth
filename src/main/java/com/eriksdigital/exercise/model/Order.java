package com.eriksdigital.exercise.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Document(collection = "order")
public class Order {

    @Id
    private long id;

    private String status; //status of order could be enum todo

    private double totalPrice;

    private LocalDateTime orderDate;

}
