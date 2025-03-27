package com.example.ktop_food_app.App.model.data.entity;

public class CartItem {
    private final String name;
    private final long price;
    private final int imageResource;
    private int quantity;

    public CartItem(String name, long price, int quantity, int imageResource) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResource = imageResource;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageResource() {
        return imageResource;
    }

    public long getTotalPrice() {
        return price * quantity;
    }
}

