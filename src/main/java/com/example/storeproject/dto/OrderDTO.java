package com.example.storeproject.dto;

import java.time.LocalDateTime;

public class OrderDTO {
    private int orderId;
    private int userId;
    private String customerName;
    private LocalDateTime orderDate;
    private String orderStatus;
    private double totalPrice;

    public OrderDTO() {}

    public OrderDTO(int orderId, int userId, String customerName, LocalDateTime orderDate, String orderStatus, double totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    // Getter & Setter
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
