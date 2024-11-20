package com.example.web_project.Service.delivery;

import com.example.web_project.Entity.Delivery;
import com.example.web_project.Repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    public List<Delivery> getAllDeliveries() {
        Iterable<Delivery> iterable = deliveryRepository.findAll();
        List<Delivery> deliveries = new ArrayList<>();
        iterable.forEach(deliveries::add);
        return deliveries;
    }


}