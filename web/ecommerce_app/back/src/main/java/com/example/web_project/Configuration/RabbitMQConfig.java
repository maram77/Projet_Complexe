package com.example.web_project.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue statusQueue() {
        return new Queue("statusQueue", true);
    }
}
