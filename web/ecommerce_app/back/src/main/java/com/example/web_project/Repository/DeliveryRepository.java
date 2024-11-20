package com.example.web_project.Repository;

import com.example.web_project.Entity.Delivery;
import com.example.web_project.Entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long > {
}
