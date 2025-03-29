package com.example.ktop_food_app.App.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.ktop_food_app.App.model.data.entity.Category;
import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.App.model.repository.CategoryRepository;
import com.example.ktop_food_app.App.model.repository.FoodRepository;
import com.example.ktop_food_app.App.view.activity.Auth.LoginActivity;
import com.example.ktop_food_app.App.view.adapter.CategoryAdapter;
import com.example.ktop_food_app.App.view.adapter.FoodAdapter;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityNavHomeBinding;
import com.example.ktop_food_app.databinding.NavHeaderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityNavHomeBinding binding;
    private NavHeaderBinding headerBinding;
    private FoodRepository foodRepository;
    private CategoryRepository categoryRepository;
    private FoodAdapter foodAdapter;
    private CategoryAdapter categoryAdapter;
    private FirebaseAuth mAuth;
    private List<Food> allFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityNavHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        headerBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0));

        foodRepository = new FoodRepository();
        categoryRepository = new CategoryRepository();

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

    // Cập nhật hàm loadProfileImage để lấy URL avatar từ Realtime Database và dùng Glide
    private void loadProfileImage() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Nếu user chưa đăng nhập, chuyển về LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Lấy UID của user hiện tại
        String uid = currentUser.getUid();
        String databaseUrl = "https://ktop-food-database-default-rtdb.asia-southeast1.firebasedatabase.app";
        DatabaseReference userRef = FirebaseDatabase.getInstance(databaseUrl)
                .getReference("users")
                .child(uid)
                .child("profile")
                .child("avatar");

        // Đọc dữ liệu từ Realtime Database
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String avatarUrl = dataSnapshot.getValue(String.class);
                if (avatarUrl != null && !avatarUrl.isEmpty()) {
                    // Sử dụng Glide để tải ảnh từ URL vào ImageView
                    Glide.with(HomeActivity.this)
                            .load(avatarUrl)
                            .placeholder(R.drawable.img_user) // Ảnh mặc định khi đang tải
                            .error(R.drawable.img_user)       // Ảnh mặc định nếu lỗi
                            .into(binding.home.imgUser);
                } else {
                    // Nếu không có avatar, dùng ảnh mặc định
                    binding.home.imgUser.setImageResource(R.drawable.img_user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu không đọc được dữ liệu
                Toast.makeText(HomeActivity.this, "Lỗi tải avatar: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                binding.home.imgUser.setImageResource(R.drawable.img_user);
            }
        });
    }

    private void handleBackUpButton() {
        headerBinding.imgBackArrow.setOnClickListener(v ->
                binding.drawerLayout.closeDrawer(GravityCompat.START));
    }

    private void handleLogoutButton() {
        binding.logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void handleMenuButton() {
        binding.home.menuButtonIcon.setOnClickListener(v ->
                binding.drawerLayout.openDrawer(GravityCompat.START));
    }

    private void handleNavigationMenu() {
        binding.navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.nav_track_order) {
                Toast.makeText(this, "Navigating to Track Order Activity", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_order_history) {
                Toast.makeText(this, "Navigating to Order History Activity", Toast.LENGTH_SHORT).show();
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void handleProfileImage() {
        binding.home.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleCartButton() {
        binding.home.cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerViews() {
        binding.home.recyclerViewFoods.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        foodAdapter = new FoodAdapter(this, new ArrayList<>());
        binding.home.recyclerViewFoods.setAdapter(foodAdapter);

        List<Category> categoryList = categoryRepository.getCategoryList();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        binding.home.gridViewCategories.setAdapter(categoryAdapter);
    }

    private void handleSearchButton() {
        binding.home.searchButtonIcon.setOnClickListener(v -> {
            String query = binding.home.edtSearch.getText().toString().trim();
            filterFoods(query);
        });
    }

    private void filterFoods(String query) {
        List<Food> filteredList = new ArrayList<>();
        for (Food food : allFoods) {
            if (food.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(food);
            }
        }
        foodAdapter.setFoodList(filteredList);
        foodAdapter.notifyDataSetChanged();
    }


    private void loadData() {
        foodRepository.getFoodList(new FoodRepository.FoodCallback() {
            @Override
            public void onSuccess(List<Food> foodList) {
                allFoods.clear();
                allFoods.addAll(foodList);
                foodAdapter.setFoodList(allFoods);
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(HomeActivity.this, "Lỗi tải dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}