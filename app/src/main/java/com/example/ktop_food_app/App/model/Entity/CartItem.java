package com.example.ktop_food_app.App.model.Entity;

public class CartItem {
    private String name;
    private long price;
    private int quantity;
    private int imageResource;

    public CartItem(String name, long price, int quantity, int imageResource) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResource = imageResource;
    }

    // Getters and setters
    public String getName() { return name; }
    public long getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getImageResource() { return imageResource; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getTotalPrice() { return price * quantity; }
}

