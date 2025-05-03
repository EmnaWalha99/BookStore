package com.example.Bookstore.dto;

public class CartDto {
    private int quantity;

    public CartDto() {}

    public CartDto(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
