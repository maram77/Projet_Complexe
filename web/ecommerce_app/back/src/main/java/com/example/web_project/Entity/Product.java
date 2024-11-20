//package com.example.web_project.Entity;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonIdentityReference;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//
//import javax.persistence.*;
//
//@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productReference")
//public class Product {
//    @Id
//    @Column(length = 50, unique = true)
//    private String productReference;
//    private String name;
//    private String description;
//    @Lob
//    private byte[] image;
//    private double price;
//    private String color;
//    private  Long quantity ;
//    @ManyToOne
//    @JoinColumn(name = "brand_id")
//    @JsonIdentityReference(alwaysAsId = true)
//    private Brand brand;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    @JsonIdentityReference(alwaysAsId = true)
//    private Category category;
//
//    public String getProductReference() {
//        return productReference;
//    }
//
//    public void setProductReference(String productReference) {
//        this.productReference = productReference;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//
//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public Brand getBrand() {
//        return brand;
//    }
//
//    public Long getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Long quantity) {
//        this.quantity = quantity;
//    }
//
//    public void setBrand(Brand brand) {
//        this.brand = brand;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//}
package com.example.web_project.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productReference")
public class Product {
    @Id
    @Column(length = 50, unique = true)
    private String productReference;
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Lob
    private String image;
    private double price;
    private String color;
    private  Long quantity;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;

    public String getProductReference() {
        return productReference;
    }

    public void setProductReference(String productReference) {
        this.productReference = productReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Brand getBrand() {
        return brand;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}