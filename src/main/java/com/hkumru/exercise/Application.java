package com.hkumru.exercise;

import com.hkumru.exercise.security.AuthConfig;
import com.hkumru.exercise.security.CustomAuthorizationServerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableResourceServer
@Import({AuthConfig.class, CustomAuthorizationServerConfigurer.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
