package com.example.storeproject.entity;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private Integer orderId;
    private String method;
    private LocalDateTime paidAt;
    private double price;
    private String paymentStatus;

    public Payment() {
    }

    public Payment(int paymentId, Integer orderId, String method, LocalDateTime paidAt, double price, String paymentStatus) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.method = method;
        this.paidAt = paidAt;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
