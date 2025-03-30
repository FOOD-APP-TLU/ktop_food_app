package com.example.ktop_food_app.App.model.repository;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ktop_food_app.App.model.data.entity.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {
    private final DatabaseReference foodRef;
    private final List<Food> foodList = new ArrayList<>();


    public FoodRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ktop-food-database-default-rtdb.asia-southeast1.firebasedatabase.app");
        foodRef = database.getReference("foods");
    }

    public void getFoodList(FoodCallback callback) {
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Food food = data.getValue(Food.class);
                    if (food != null) {
                        foodList.add(food);
                    }
                }
                Log.d("FirebaseData", "Dữ liệu tải về: " + foodList.size() + " món ăn.");
                callback.onSuccess(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    public interface FoodCallback {
        void onSuccess(List<Food> foodList);

        void onFailure(String errorMessage);
    }

}
