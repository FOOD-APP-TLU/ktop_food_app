package com.example.ktop_food_app.App.model.data.local;

import com.example.ktop_food_app.App.model.data.entity.Category;
import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.App.model.data.entity.User;
import com.example.ktop_food_app.R;

import java.util.ArrayList;
import java.util.List;

// Lop tao du lieu mau de kiem tra ung dung
public class DataGenerator {

    // Tao danh sach mon an mau
//    public static List<Food> generateSampleFoods() {
//        List<Food> foods = new ArrayList<>();
//        // Tao cac danh muc mon an voi tham so img
//        Category spaghetti = new Category(1, "Spaghetti", R.drawable.spaghetti_icon);
//        Category chicken = new Category(2, "Chicken", R.drawable.chicken_icon);
//        Category humburger = new Category(3, "Hamburger", R.drawable.hamburger_icon);
//        Category pizza = new Category(4, "Pizza", R.drawable.pizza_icon);
//        Category drink = new Category(5, "Drink", R.drawable.drink_icon);
//        Category bingsu = new Category(6, "Bingsu", R.drawable.bingsu_icon);
//        Category hotdog = new Category(7, "Hotdog", R.drawable.hotdog_icon);
//        Category more = new Category(8, "More", R.drawable.more_icon);
//
//        // Them du lieu mon an
//        foods.add(new Food(1, "Bolognese", "Italian pasta with tomato sauce and cheese Italian pasta with tomato sauce and cheese Italian pasta with tomato sauce and cheese", 45000, 4.8f, "10-15min", R.drawable.bolognese, spaghetti));
//        foods.add(new Food(2, "Fried Chicken", "Grilled chicken with special seasoning", 40000, 4.9f, "15-20min", R.drawable.friedchickend, chicken));
//        foods.add(new Food(3, "Beef Lasagna", "Juicy beef patty with fresh vegetables and sauce", 25000, 4.7f, "10-15min", R.drawable.beeflasagma, humburger));
//        foods.add(new Food(4, "Veggiei Pizza", "Classic hotdog with ketchup and mustard", 50000, 4.6f, "10-15min", R.drawable.veggieipizza, pizza));
//        foods.add(new Food(5, "CoCa", "Crispy hamburger with a soft bun and cheese", 30000, 4.5f, "20-25min", R.drawable.coccadrink, drink));
//        foods.add(new Food(6, "Bingsu Berry", "Korean shaved ice dessert with sweet toppings", 200000, 4.9f, "15-20min", R.drawable.bingsuberry, bingsu));
//        foods.add(new Food(7, "Mozza Hotdog", "Korean shaved ice dessert with sweet toppings", 15000, 4.3f, "10-15min", R.drawable.mozzahotdog, hotdog));
//        foods.add(new Food(8, "Potato Hotdog", "Korean shaved ice dessert with sweet toppings", 18000, 4.6f, "10-15min", R.drawable.potatohotdog, hotdog));
//        foods.add(new Food(9, "More", "Special dish with a variety of flavors", 50000, 4.8f, "10-15min", R.drawable.fruitrice, more));
//
//        return foods;
//    }

    // Tao danh sach danh muc mau
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

    // Tao danh sach nguoi dung mau
    // Tao danh sach nguoi dung mau
    public static List<User> generateSampleUsers() {
        List<User> users = new ArrayList<>();
        // Them truong id cho tung user
        users.add(new User(1, R.drawable.user_img_otter, "Vi Hong Minh", "vihongminh5504@gmail.com", "0374218961", "Khu 2, Hoang Cuong, Thanh Ba, Phu Tho"));
        users.add(new User(2, R.drawable.img_user, "Tran Thi B", "tranthib@example.com", "0971122334", "456 Duong DEF, TP. HCM"));
        users.add(new User(3, R.drawable.img_user, "Le Van C", "levanc@example.com", "0912345678", "789 Duong GHI, Da Nang"));
        users.add(new User(4, R.drawable.img_user, "Pham Thi D", "phamthid@example.com", "0908765432", "101 Duong XYZ, Hai Phong"));
        users.add(new User(5, R.drawable.img_user, "Hoang Minh E", "hoangminhe@example.com", "0933334444", "202 Duong LMN, Can Tho"));
        return users;
    }
}