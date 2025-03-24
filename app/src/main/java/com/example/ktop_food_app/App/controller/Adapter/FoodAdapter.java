package com.example.ktop_food_app.App.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktop_food_app.App.model.Entity.Food;
import com.example.ktop_food_app.databinding.ItemFoodBinding;

import java.util.ArrayList;
import java.util.List;

// Adapter de hien thi danh sach mon an trong RecyclerView
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final Context context; // Them bien context
    // Danh sach cac mon an
    private List<Food> foodList;

    // Constructor nhan context va danh sach mon an
    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        // Neu danh sach khong null, gan vao foodList; neu null, tao danh sach rong
        this.foodList = foodList != null ? foodList : new ArrayList<>();
    }

    // Phuong thuc cap nhat danh sach mon an
    public void setFoodList(List<Food> newFoodList) {
        // Neu danh sach moi khong null, gan vao foodList; neu null, tao danh sach rong
        this.foodList = newFoodList != null ? newFoodList : new ArrayList<>();
        // Thong bao cho RecyclerView rang du lieu da thay doi de cap nhat giao dien
        notifyDataSetChanged();
    }

    // Tao ViewHolder moi cho moi item trong RecyclerView
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Su dung View Binding de inflate layout item_food.xml
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        // Tra ve ViewHolder moi voi binding va context
        return new FoodViewHolder(binding, context);
    }

    // Gan du lieu vao ViewHolder tai vi tri position
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        // Lay mon an tai vi tri position
        Food food = foodList.get(position);
        // Gan du lieu vao cac view trong ViewHolder
        holder.bind(food);
    }

    // Tra ve so luong item trong danh sach
    @Override
    public int getItemCount() {
        return foodList.size();
    }

    // ViewHolder de giu cac view cua moi item trong RecyclerView
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        // Binding cho layout item_food.xml
        private final ItemFoodBinding binding;
        private final Context context;

        // Constructor nhan binding va context
        public FoodViewHolder(@NonNull ItemFoodBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        // Phuong thuc gan du lieu vao cac view
        public void bind(Food food) {
            // Gan ten mon an
            binding.txtFoodName.setText(food.getName());
            // Gan gia mon an, dinh dang voi don vi "d"
            binding.txtFoodPrice.setText(String.format("%d d", food.getPrice()));
            // Gan thoi gian chuan bi
            binding.txtFoodTime.setText(food.getTime());
            // Gan hinh anh mon an
            binding.imgFood.setImageResource(food.getImg());

            // Xu ly su kien click tren item
//            binding.getRoot().setOnClickListener(v -> {
//                Intent intent = new Intent(context, FoodDetailActivity.class);
//                intent.putExtra("food_item", food); // Truyen toan bo doi tuong Food qua Intent
//                context.startActivity(intent);
//            });
        }
    }
}