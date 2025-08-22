package com.example.storeproject.entity.order;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String customerName;
    private LocalDateTime createdAt;
    private String status;
    private double total;

    public Order() {
    }

    public Order(int id, String customerName, LocalDateTime createdAt, String status, double total) {
        this.id = id;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.status = status;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
