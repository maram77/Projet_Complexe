package com.example.web_project;
import com.example.web_project.Service.order.OrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.web_project.Entity.OrderDetail;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
@SpringBootApplication
public class WebProject  {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebProject.class, args);

    }



}
