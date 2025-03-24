package com.example.ktop_food_app.App.model.Repository;

import com.example.ktop_food_app.App.model.Data.Database.DataGenerator;
import com.example.ktop_food_app.App.model.Entity.Food;

import java.util.List;

public class FoodRepository {
    public List<Food> getFoodList() {
        return DataGenerator.generateSampleFoods();
    }

}
