package com.example.storeproject.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Cart {
    private int cartId;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartDetail> cartDetails;

    public Cart() {
    }

    public Cart(int cartId, int userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public double getTotalPrice() {
        if (cartDetails == null) return 0;
        return cartDetails.stream()
                .mapToDouble(cd -> cd.getPrice() * cd.getQuantity())
                .sum();
    }
}
