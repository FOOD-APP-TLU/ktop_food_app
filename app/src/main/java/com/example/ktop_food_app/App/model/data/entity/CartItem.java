package com.example.ktop_food_app.App.model.data.entity;


import java.io.Serializable;

public class CartItem implements Serializable {
    private String foodId;
    private String name;
    private long price;
    private int quantity;
    private String imagePath;

    public CartItem() {}

    public CartItem(String name, long price, int quantity, String imagePath) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public String getFoodId() { return foodId; }
    public void setFoodId(String foodId) { this.foodId = foodId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public long getTotalPrice() { return price * quantity; }
}