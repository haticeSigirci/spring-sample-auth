package com.eriksdigital.exercise.repository;

import com.eriksdigital.exercise.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class OrderRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Order findOrderById(Long id) {
        return mongoTemplate.findOne(query(where("id").is(id)), Order.class);
    }

    public Order save(Order order) {
        return mongoTemplate.save(order);
    }

    public void delete(Long id) {
        mongoTemplate.remove(query(where("id").is(id)), Order.class);
    }
}
