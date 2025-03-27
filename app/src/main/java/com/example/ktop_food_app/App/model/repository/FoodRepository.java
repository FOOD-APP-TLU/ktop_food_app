package com.example.ktop_food_app.App.model.repository;

import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.App.model.data.local.DataGenerator;

import java.util.List;

public class FoodRepository {
    public List<Food> getFoodList() {
        return DataGenerator.generateSampleFoods();
    }

}
