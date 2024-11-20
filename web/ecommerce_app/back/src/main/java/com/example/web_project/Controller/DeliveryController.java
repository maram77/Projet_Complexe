package com.example.web_project.Controller;

import com.example.web_project.Entity.Delivery;
import com.example.web_project.Service.delivery.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/all")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }




}
