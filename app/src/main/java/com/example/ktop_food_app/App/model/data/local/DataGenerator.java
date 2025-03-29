package com.example.ktop_food_app.App.model.data.local;

import com.example.ktop_food_app.App.model.data.entity.Category;
import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.App.model.data.entity.User;
import com.example.ktop_food_app.R;

import java.util.ArrayList;
import java.util.List;

// Lop tao du lieu mau de kiem tra ung dung
public class DataGenerator {
    public static List<Category> generateSampleCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Spaghetti", R.drawable.spaghetti_icon));
        categories.add(new Category(2, "Chicken", R.drawable.chicken_icon));
        categories.add(new Category(3, "Hamburger", R.drawable.hamburger_icon));
        categories.add(new Category(4, "Pizza", R.drawable.pizza_icon));
        categories.add(new Category(5, "Drink", R.drawable.drink_icon));
        categories.add(new Category(6, "Bingsu", R.drawable.bingsu_icon));
        categories.add(new Category(7, "Hotdog", R.drawable.hotdog_icon));
        categories.add(new Category(8, "More", R.drawable.more_icon));
        return categories;
    }
}