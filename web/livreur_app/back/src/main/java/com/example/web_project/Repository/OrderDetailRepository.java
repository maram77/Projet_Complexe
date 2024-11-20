package com.example.web_project.Repository;

import com.example.web_project.Entity.Cart;
import com.example.web_project.Entity.OrderDetail;
import com.example.web_project.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository  extends CrudRepository<OrderDetail, Long > {
    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);

    List<OrderDetail> findByUserId(Long userId);
}
