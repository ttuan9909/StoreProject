package com.example.storeproject.entity;

import java.time.LocalDate;

public class Discount {
    private int discountId;
    private String discountName;
    private String description;
    private Integer percentOff;
    private LocalDate startDate;
    private LocalDate endDate;

    public Discount() {
    }

    public Discount(int discountId, String discountName, String description, Integer percentOff, LocalDate startDate, LocalDate endDate) {
        this.discountId = discountId;
        this.discountName = discountName;
        this.description = description;
        this.percentOff = percentOff;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(Integer percentOff) {
        this.percentOff = percentOff;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
