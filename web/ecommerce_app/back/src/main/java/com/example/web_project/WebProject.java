package com.example.web_project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.web_project.Entity.OrderDetail;
import com.example.web_project.Repository.OrderDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
@SpringBootApplication
public class WebProject {

    public static void main(String[] args) {
        SpringApplication.run(WebProject.class, args);

    }

}
