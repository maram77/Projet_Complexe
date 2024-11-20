package com.example.web_project.Dto;

import com.example.web_project.Entity.Product;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductWithQuantityDto {

    private Product product;
    private int quantity;
    // Default constructor
    public ProductWithQuantityDto() {
    }

    // Constructor with arguments
    public ProductWithQuantityDto(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
