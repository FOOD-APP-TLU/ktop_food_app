package com.example.ktop_food_app.App.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ktop_food_app.App.controller.Adapter.CategoryAdapter;
import com.example.ktop_food_app.App.controller.Adapter.FoodAdapter;
import com.example.ktop_food_app.App.model.Data.Database.DataGenerator;
import com.example.ktop_food_app.App.model.Entity.Category;
import com.example.ktop_food_app.App.model.Entity.Food;
import com.example.ktop_food_app.App.model.Entity.User;
import com.example.ktop_food_app.App.model.Repository.CategoryRepository;
import com.example.ktop_food_app.App.model.Repository.FoodRepository;
import com.example.ktop_food_app.App.view.Activity.Auth.LoginActivity;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityNavHomeBinding;
import com.example.ktop_food_app.databinding.NavHeaderBinding;

import java.util.ArrayList;
import java.util.List;

// Lop HomeActivity quan ly giao dien chinh voi navigation drawer va load du lieu
public class HomeActivity extends AppCompatActivity {

    private ActivityNavHomeBinding binding; // Binding cho layout activity_nav_home.xml
    private NavHeaderBinding headerBinding; // Binding cho layout nav_header.xml
    private FoodRepository foodRepository; // Kho luu tru mon an
    private CategoryRepository categoryRepository; // Kho luu tru danh muc
    private FoodAdapter foodAdapter; // Adapter cho danh sach mon an
    private CategoryAdapter categoryAdapter; // Adapter cho danh sach danh muc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khoi tao View Binding
        binding = ActivityNavHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bind vao header view hien co cua NavigationView
        headerBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0));

        // Khoi tao cac kho luu tru
        foodRepository = new FoodRepository();
        categoryRepository = new CategoryRepository();

        // Thiet lap cac thanh phan giao dien va lang nghe su kien
        setupRecyclerViews();
        handleSearchButton();
        loadData();
        handleMenuButton();
        handleNavigationMenu();
        handleBackUpButton();
        handleLogoutButton();
        handleProfileImage();
        handleCartButton();
        loadProfileImage();
    }

    // Xu ly backup_icon de dong drawer
    private void handleBackUpButton() {
        headerBinding.imgBackArrow.setOnClickListener(v ->
                binding.drawerLayout.closeDrawer(GravityCompat.START));
    }

    // Xu ly nut dang xuat
    private void handleLogoutButton() {
        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Xu ly nut menu de mo drawer
    private void handleMenuButton() {
        binding.home.menuButtonIcon.setOnClickListener(v ->
                binding.drawerLayout.openDrawer(GravityCompat.START));
    }

    // Xu ly cac muc trong menu cua NavigationView
    private void handleNavigationMenu() {
        binding.navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                Toast.makeText(this, "Navigating to Edit Profile Activity", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_track_order) {
                Toast.makeText(this, "Navigating to Track Order Activity", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_order_history) {
                Toast.makeText(this, "Navigating to Order History Activity", Toast.LENGTH_SHORT).show();
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START); // Dong drawer sau khi chon
            return true;
        });
    }

    // Ham load anh user vao user image
    private void loadProfileImage() {
        List<User> userList = DataGenerator.generateSampleUsers();

        if (!userList.isEmpty()) {
            User firstUser = userList.get(0); // Lay user dau tien (vi du: Nguyen Van A)
            // Gan anh cua user vao user_icon
            binding.home.imgUser.setImageResource(firstUser.getImg());
        } else {
            // If the user list is empty, set a default image
            binding.home.imgUser.setImageResource(R.drawable.img_user);
        }
    }

    // Xu ly khi nhan vao user image
    private void handleProfileImage() {
        binding.home.imgUser.setOnClickListener(v ->
                Toast.makeText(this, "Navigating to Profile Activity", Toast.LENGTH_SHORT).show());
    }

    // Xu ly khi nhan vao cart_icon
    private void handleCartButton() {
        binding.home.cartIcon.setOnClickListener(v ->
                Toast.makeText(this, "Navigating to Cart Activity", Toast.LENGTH_SHORT).show());
    }

    private void setupRecyclerViews() {
        binding.home.recyclerViewFoods.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Truyen context (this) vao FoodAdapter
        foodAdapter = new FoodAdapter(this, new ArrayList<>());
        binding.home.recyclerViewFoods.setAdapter(foodAdapter);

        List<Category> categoryList = categoryRepository.getCategoryList();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        binding.home.gridViewCategories.setAdapter(categoryAdapter);
    }

    // Xu ly chuc nang tim kiem
    private void handleSearchButton() {
        binding.home.searchButtonIcon.setOnClickListener(v -> {
            String query = binding.home.edtSearch.getText().toString().trim();
            filterFoods(query);
        });
    }

    // Loc danh sach mon an dua tren tu khoa tim kiem
    private void filterFoods(String query) {
        List<Food> allFoods = foodRepository.getFoodList();
        List<Food> filteredList = new ArrayList<>();

        for (Food food : allFoods) {
            if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(food);
            }
        }
        foodAdapter.setFoodList(filteredList);
        foodAdapter.notifyDataSetChanged();
    }

    // Tai du lieu vao giao dien
    private void loadData() {
        List<Food> foodList = foodRepository.getFoodList();
        foodAdapter.setFoodList(foodList);
        foodAdapter.notifyDataSetChanged();
    }

    // Xu ly nut Back tren thiet bi
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}