package com.example.ktop_food_app.App.view.Activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ktop_food_app.App.model.Entity.Food;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityFoodDetailBinding;

public class FoodDetailActivity extends AppCompatActivity {
    private ActivityFoodDetailBinding binding;
    private Food food;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng View Binding
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận dữ liệu từ Intent
        food = (Food) getIntent().getSerializableExtra("food");

        // Hiển thị dữ liệu món ăn
        if (food != null) {
            binding.imgFoodDetail.setImageResource(food.getImg());
            binding.txtFoodDetailName.setText(food.getName());
            binding.txtFoodDetailPrice.setText(food.getPrice() + " đ");
            binding.txtFoodDetailDescription.setText(food.getDescription());
            binding.txtFoodDetailRating.setText(String.valueOf(food.getRate()));
            binding.txtFoodDetailTime.setText(food.getTime());
            updateTotalPrice();
        }

        // Xử lý tăng số lượng
        binding.btnIncrease.setOnClickListener(v -> {
            quantity++;
            binding.txtQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        });

        // Xử lý giảm số lượng
        binding.btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                binding.txtQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        // Xử lý thêm vào giỏ hàng
        binding.addToCart.setOnClickListener(v -> {
            // Code xử lý thêm vào giỏ hàng
        });
    }

    private void updateTotalPrice() {
        binding.txtTotalPrice.setText((food.getPrice() * quantity) + " đ");
    }
}
