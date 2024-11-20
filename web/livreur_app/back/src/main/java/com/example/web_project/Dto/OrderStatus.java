package com.example.web_project.Dto;

import com.example.web_project.Entity.OrderDetail;
import lombok.Data;

@Data
public class OrderStatus {
    private OrderDetail order;
    private String status;
}

