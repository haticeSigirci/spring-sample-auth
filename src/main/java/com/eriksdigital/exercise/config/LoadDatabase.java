package com.eriksdigital.exercise.config;

import com.eriksdigital.exercise.model.Member;
import com.eriksdigital.exercise.model.Order;
import com.eriksdigital.exercise.repository.OrderRepository;
import com.eriksdigital.exercise.repository.UserRepository;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class LoadDatabase {

    private static final Logger log = LogManager.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OrderRepository repository, UserRepository userRepository) {

        return args -> {
            Order orderCreated = new Order(1, "Order Created", 23, LocalDateTime.now());
            repository.save(orderCreated);

            Member member = new Member(1, "hatice", "$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu");
            userRepository.save(member);
            log.info("Preloading order object {}", orderCreated);
        };

    }

}
