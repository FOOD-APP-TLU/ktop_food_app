package com.example.ktop_food_app.App.model.data.entity;

import java.io.Serializable;

public class Food implements Serializable {
    private String FoodId;
    private String Title;
    private String Description;
    private int Price;
    private float Star;
    private String TimeValue;
    private String ImagePath;
    private int CategoryId;
    private boolean BestFood;

    public Food() {
    }

    public Food(String foodId, String title, String description, int price, float star, String timeValue, int categoryId, String imagePath, boolean bestFood) {
        FoodId = foodId;
        Title = title;
        Description = description;
        Price = price;
        Star = star;
        TimeValue = timeValue;
        CategoryId = categoryId;
        ImagePath = imagePath;
        BestFood = bestFood;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getStar() {
        return Star;
    }

    public void setStar(float star) {
        Star = star;
    }

    public String getTimeValue() {
        return TimeValue;
    }

    public void setTimeValue(String timeValue) {
        TimeValue = timeValue;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public boolean isBestFood() {
        return BestFood;
    }

    public void setBestFood(boolean bestFood) {
        BestFood = bestFood;
    }
}
