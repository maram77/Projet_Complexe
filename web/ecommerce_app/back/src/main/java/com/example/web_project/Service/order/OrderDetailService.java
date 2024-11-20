package com.example.web_project.Service.order;

import com.example.web_project.Entity.Cart;
import com.example.web_project.Entity.Delivery;
import com.example.web_project.Entity.OrderDetail;
import com.example.web_project.Entity.Product;
import com.example.web_project.Repository.CartRepository;
import com.example.web_project.Repository.DeliveryRepository;
import com.example.web_project.Repository.OrderDetailRepository;
import com.example.web_project.Repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    ProductRepository productRepository;

    public OrderDetail checkout(Long userId, Long cartId, Delivery delivery) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (!optionalCart.isPresent()) {
            return null;
        }
        Cart cart = optionalCart.get();

        if (!cart.getUser().getId().equals(userId)) {
            return null;
        }

        if (delivery == null || delivery.getAddress() == null || delivery.getContactNumber() == null) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUser(cart.getUser());
        orderDetail.setOrderAmount(calculateOrderAmount(cart));
        orderDetail.setOrderStatus("Pending");

        Delivery savedDelivery = deliveryRepository.save(delivery);
        orderDetail.setDelivery(savedDelivery);

        orderDetail = orderDetailRepository.save(orderDetail);

        for (Map.Entry<Product, Integer> entry : cart.getProductQuantityMap().entrySet()) {
            Product product = entry.getKey();
            Integer quantityOrdered = entry.getValue();
            Long remainingStock = product.getQuantity() - quantityOrdered;
            product.setQuantity(remainingStock);
            productRepository.save(product);
        }

        cart.getProductQuantityMap().clear();
        cartRepository.save(cart);

        return orderDetail;
    }

    private Double calculateOrderAmount(Cart cart) {
        double totalAmount = 0.0;
        for (Map.Entry<Product, Integer> entry : cart.getProductQuantityMap().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }
    public List<OrderDetail> getAllOrders() {
        Iterable<OrderDetail> iterable = orderDetailRepository.findAll();
        List<OrderDetail> orderDetails = new ArrayList<>();
        iterable.forEach(orderDetails::add);
        return orderDetails;
    }
    public List<OrderDetail> getOrdersByUserId(Long userId) {
        return orderDetailRepository.findByUserId(userId);
    }

    public OrderDetail updateOrderDetail(Long id, OrderDetail updatedOrderDetail) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElse(null);
        if (existingOrderDetail == null) {
            return null;
        }
        existingOrderDetail.setOrderStatus(updatedOrderDetail.getOrderStatus());
        return orderDetailRepository.save(existingOrderDetail);
    }
    @RabbitListener(queues = "statusQueue")
    public void updateOrderStatus( String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<OrderDetail> mapType = new TypeReference<OrderDetail>() {};
        OrderDetail order = objectMapper.readValue(message, mapType);
        OrderDetail existingOrder = orderDetailRepository.findById(order.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getOrderId()));
        existingOrder.setOrderStatus(order.getOrderStatus());
        orderDetailRepository.save(existingOrder);
    }
}