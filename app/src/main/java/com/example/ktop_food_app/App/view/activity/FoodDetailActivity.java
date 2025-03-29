package com.example.ktop_food_app.App.view.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.databinding.ActivityFoodDetailBinding;

public class FoodDetailActivity extends AppCompatActivity {
    private ActivityFoodDetailBinding binding;
    private Food food;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getFoodFromIntent();
        setupUI();
        setupListeners();
    }

    private void getFoodFromIntent() {
        food = (Food) getIntent().getSerializableExtra("food");
    }

    private void setupUI() {
        if (food != null) {
            Glide.with(this).load(food.getImagePath()).into(binding.imgFoodDetail);
            binding.txtFoodDetailName.setText(food.getTitle());
            binding.txtFoodDetailPrice.setText(food.getPrice() + " đ");
            binding.txtFoodDetailDescription.setText(food.getDescription());
            binding.txtFoodDetailRating.setText(String.valueOf(food.getStar()));
            binding.txtFoodDetailTime.setText(food.getTimeValue());
            binding.ratingDetail.setRating(food.getStar());
            updateTotalPrice();
        }
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnIncrease.setOnClickListener(v -> increaseQuantity());
        binding.btnDecrease.setOnClickListener(v -> decreaseQuantity());

        binding.addToCart.setOnClickListener(v -> addToCart());
    }

    private void increaseQuantity() {
        quantity++;
        binding.txtQuantity.setText(String.valueOf(quantity));
        updateTotalPrice();
    }

    private void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
            binding.txtQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        binding.txtTotalPrice.setText((food.getPrice() * quantity) + " đ");
    }

    private void addToCart() {
        Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
        // Thêm xử lý thêm vào giỏ hàng nếu cần
    }
}
