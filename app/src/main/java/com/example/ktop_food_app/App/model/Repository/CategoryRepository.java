package com.example.ktop_food_app.App.model.Repository;

import com.example.ktop_food_app.App.model.Data.Database.DataGenerator;
import com.example.ktop_food_app.App.model.Entity.Category;

import java.util.List;

public class CategoryRepository {
    public List<Category> getCategoryList() {
        return DataGenerator.generateSampleCategories();
    }
}
