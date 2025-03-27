package com.example.ktop_food_app.App.model.repository;

import com.example.ktop_food_app.App.model.data.entity.Category;
import com.example.ktop_food_app.App.model.data.local.DataGenerator;

import java.util.List;

public class CategoryRepository {
    public List<Category> getCategoryList() {
        return DataGenerator.generateSampleCategories();
    }
}
