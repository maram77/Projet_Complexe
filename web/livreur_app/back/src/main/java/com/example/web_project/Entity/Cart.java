package com.example.web_project.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToOne
    private User user;

    @ElementCollection
    @CollectionTable(name = "cart_product_mapping",
            joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantityMap = new HashMap<>();



    public Cart() {

    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart(User user) {
        this.user = user;
    }

    public void addProductWithQuantity(Product product, int quantity) {
        if (productQuantityMap.containsKey(product)) {
            int existingQuantity = productQuantityMap.get(product);
            productQuantityMap.put(product, existingQuantity + quantity);
        } else {
            productQuantityMap.put(product, quantity);
        }
    }

    public Map<Product, Integer> getProductQuantityMap() {
        return productQuantityMap;
    }

    public void setProductQuantityMap(Map<Product, Integer> productQuantityMap) {
        this.productQuantityMap = productQuantityMap;
    }
}
