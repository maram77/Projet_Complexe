package com.example.web_project.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idBrand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBrand;
    @Column(unique = true)
    private String brandName;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.MERGE)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Product> products;

    public Long getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Long idBrand) {
        this.idBrand = idBrand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }
}
