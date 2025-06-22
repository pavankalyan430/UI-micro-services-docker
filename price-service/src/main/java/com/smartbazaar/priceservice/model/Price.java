package com.smartbazaar.priceservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Price {

    @Id
    private Long id;
    private String product;
    private double amount;

    public Price() {}

    public Price(Long id, String product, double amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
