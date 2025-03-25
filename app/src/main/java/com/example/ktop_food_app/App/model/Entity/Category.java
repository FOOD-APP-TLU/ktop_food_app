package com.example.ktop_food_app.App.model.Entity;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private int img;

    public Category(int id, String name, int img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}