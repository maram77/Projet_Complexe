package com.example.web_project.Controller;

import com.example.web_project.Entity.Delivery;
import com.example.web_project.Entity.OrderDetail;
import com.example.web_project.Repository.OrderDetailRepository;
import com.example.web_project.Service.TwilioSmsService;
import com.example.web_project.Service.order.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/checkout")
public class CheckoutController {
    @Autowired
    private OrderDetailService orderDetailService;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private TwilioSmsService twilioSmsService;

    @PostMapping("/process")
    public ResponseEntity<OrderDetail> checkout(@RequestParam Long userId, @RequestParam Long cartId, @RequestBody Delivery delivery) {
        OrderDetail orderDetail = orderDetailService.checkout(userId, cartId, delivery);
        if (orderDetail != null) {
            // Send SMS to the user
            String userPhoneNumber = delivery.getContactNumber();
            String message = "Your order has been successfully placed. Thank you!";
            twilioSmsService.sendSms(userPhoneNumber, message);

            return ResponseEntity.status(HttpStatus.CREATED).body(orderDetail);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/orders/{userId}")
    public List<OrderDetail> getOrdersByUserId(@PathVariable Long userId) {
        return orderDetailService.getOrdersByUserId(userId);
    }
    @GetMapping("/orders")
    public List<OrderDetail> getAllOrders() {
        return orderDetailService.getAllOrders();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetail) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetail);
        if (updatedOrderDetail != null) {
            return ResponseEntity.ok(updatedOrderDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}