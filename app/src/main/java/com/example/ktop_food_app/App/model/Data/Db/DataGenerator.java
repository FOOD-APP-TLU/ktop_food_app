package com.example.ktop_food_app.App.model.Data.Db;

import com.example.ktop_food_app.App.model.Entities.Category;
import com.example.ktop_food_app.App.model.Entities.Food;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<Food> generateSampleFoods() {
        List<Food> foods = new ArrayList<>();

        // Create food categories
        Category spaghetti = new Category(1, "Spaghetti");
        Category chicken = new Category(2, "Chicken");
        Category humburger = new Category(3, "Hamburger");
        Category frenchfries = new Category(4, "French fries");
        Category drink = new Category(5, "Drink");
        Category bingsu = new Category(6, "Bingsu");
        Category hotdog = new Category(7, "Hotdog");
        Category more = new Category(8, "More");


        // Add food data
        foods.add(new Food(1, "Spaghetti", "Italian pasta with tomato sauce and cheese", 45000, 4.8f, "15 minutes", "https://example.com/spaghetti.jpg", spaghetti));
        foods.add(new Food(2, "Chicken", "Grilled chicken with special seasoning", 40000, 4.9f, "10 minutes", "https://example.com/chicken.jpg", chicken));
        foods.add(new Food(3, "Hamburger", "Juicy beef patty with fresh vegetables and sauce", 25000, 4.7f, "5 minutes", "https://example.com/hamburger.jpg", humburger));
        foods.add(new Food(4, "French fries", "Classic hotdog with ketchup and mustard", 50000, 4.6f, "20 minutes", "https://example.com/hotdog.jpg", frenchfries));
        foods.add(new Food(5, "Drink", "Crispy hamburger with a soft bun and cheese", 30000, 4.5f, "8 minutes", "https://example.com/hamburger2.jpg", drink));
        foods.add(new Food(6, "Bingsu", "Korean shaved ice dessert with sweet toppings", 200000, 4.9f, "30 minutes", "https://example.com/bingsu.jpg", bingsu));
        foods.add(new Food(7, "Hotdog", "Korean shaved ice dessert with sweet toppings", 15000, 4.3f, "10 minutes", "https://example.com/bingsu.jpg", hotdog));
        foods.add(new Food(8, "More", "Special dish with a variety of flavors", 50000, 4.8f, "12 minutes", "https://example.com/more.jpg", more));

        return foods;
    }

    public static List<Category> generateSampleCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Spaghetti"));
        categories.add(new Category(2, "Chicken"));
        categories.add(new Category(3, "Hamburger"));
        categories.add(new Category(4, "French fries"));
        categories.add(new Category(5, "Drink"));
        categories.add(new Category(6, "Bingsu"));
        categories.add(new Category(7, "Hotdog"));
        categories.add(new Category(8, "More"));
        return categories;
    }
}
