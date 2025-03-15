package com.example.ktop_food_app.App.model.Entity;

public class Food {
    private final int id;
    private final String name;
    private final String description;
    private final int price;
    private final float rate;
    private final String time;
    private final int img;
    private final Category category;

    public Food(int id, String name, String description, int price, float rate, String time, int img, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rate = rate;
        this.time = time;
        this.img = img;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public float getRate() {
        return rate;
    }

    public String getTime() {
        return time;
    }

    public int getImg() {
        return img;
    }

    public Category getCategory() {
        return category;
    }
}
