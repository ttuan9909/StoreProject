package com.example.storeproject.entity;

import java.util.Date;

public class Order {
    private int orderId;
    private Integer userId;
    private Date orderDate;
    private String orderStatus;
    private double totalPrice;
    private Integer discountId;

    public Order() {
    }

    public Order(int orderId, Integer userId, Date orderDate, String orderStatus, double totalPrice, Integer discountId) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discountId = discountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
}
