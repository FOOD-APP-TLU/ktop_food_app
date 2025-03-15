package com.example.ktop_food_app.App.model.Data.Database;

import com.example.ktop_food_app.App.model.Entity.Category;
import com.example.ktop_food_app.App.model.Entity.Food;
import com.example.ktop_food_app.App.model.Entity.User;
import com.example.ktop_food_app.R;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<Food> generateSampleFoods() {
        List<Food> foods = new ArrayList<>();

        // Create food categories
        Category spaghetti = new Category(1, "Spaghetti");
        Category chicken = new Category(2, "Chicken");
        Category humburger = new Category(3, "Hamburger");
        Category pizza = new Category(4, "Pizza");
        Category drink = new Category(5, "Drink");
        Category bingsu = new Category(6, "Bingsu");
        Category hotdog = new Category(7, "Hotdog");
        Category more = new Category(8, "More");

        // Add food data
        foods.add(new Food(1, "Bolognese", "Italian pasta with tomato sauce and cheese", 45000, 4.8f, "15 minutes", R.drawable.bolognese, spaghetti));
        foods.add(new Food(2, "Fried Chicken", "Grilled chicken with special seasoning", 40000, 4.9f, "10 minutes", R.drawable.friedchickend, chicken));
        foods.add(new Food(3, "Beef Lasagna", "Juicy beef patty with fresh vegetables and sauce", 25000, 4.7f, "5 minutes", R.drawable.beeflasagma, humburger));
        foods.add(new Food(4, "Veggiei Pizza", "Classic hotdog with ketchup and mustard", 50000, 4.6f, "20 minutes", R.drawable.veggieipizza, pizza));
        foods.add(new Food(5, "CoCa", "Crispy hamburger with a soft bun and cheese", 30000, 4.5f, "8 minutes", R.drawable.coccadrink, drink));
        foods.add(new Food(6, "Bingsu Berry", "Korean shaved ice dessert with sweet toppings", 200000, 4.9f, "30 minutes", R.drawable.bingsuberry, bingsu));
        foods.add(new Food(7, "Mozza Hotdog", "Korean shaved ice dessert with sweet toppings", 15000, 4.3f, "10 minutes", R.drawable.mozzahotdog, hotdog));
        foods.add(new Food(8, "Potato Hotdog", "Korean shaved ice dessert with sweet toppings", 18000, 4.6f, "10 minutes", R.drawable.potatohotdog, hotdog));
        foods.add(new Food(9, "More", "Special dish with a variety of flavors", 50000, 4.8f, "12 minutes", R.drawable.fruitrice, more));

        return foods;
    }

    public static List<Category> generateSampleCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Spaghetti"));
        categories.add(new Category(2, "Chicken"));
        categories.add(new Category(3, "Hamburger"));
        categories.add(new Category(4, "Pizza"));
        categories.add(new Category(5, "Drink"));
        categories.add(new Category(6, "Bingsu"));
        categories.add(new Category(7, "Hotdog"));
        categories.add(new Category(8, "More"));
        return categories;
    }

    public static List<User> generateSampleUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(R.drawable.img_user, "Nguyen Van A", "nguyenvana@example.com", "0987654321", "123 Duong ABC, Ha Noi"));
        users.add(new User(R.drawable.img_user, "Tran Thi B", "tranthib@example.com", "0971122334", "456 Duong DEF, TP. HCM"));
        users.add(new User(R.drawable.img_user, "Le Van C", "levanc@example.com", "0912345678", "789 Duong GHI, Da Nang"));
        users.add(new User(R.drawable.img_user, "Pham Thi D", "phamthid@example.com", "0908765432", "101 Duong XYZ, Hai Phong"));
        users.add(new User(R.drawable.img_user, "Hoang Minh E", "hoangminhe@example.com", "0933334444", "202 Duong LMN, Can Tho"));

        return users;
    }
}
