package com.example.ktop_food_app.App.controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.ktop_food_app.App.model.Entity.Category;
import com.example.ktop_food_app.App.view.Activity.FoodListActivity;
import com.example.ktop_food_app.databinding.ItemCategoryBinding;

import java.util.List;

// Adapter de hien thi danh sach danh muc trong GridView
public class CategoryAdapter extends ArrayAdapter<Category> {

    private final Context context;
    private final List<Category> categoryList;

    // Constructor nhan context va danh sach danh muc
    public CategoryAdapter(Context context, List<Category> categoryList) {
        super(context, 0, categoryList); // 0 la resource ID, se xu ly trong getView
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemCategoryBinding binding; // Binding cho layout item_category.xml

        // Neu convertView la null, inflate layout voi View Binding
        if (convertView == null) {
            binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
            convertView = binding.getRoot();
        } else {
            // Neu convertView da ton tai, bind lai de tai su dung
            binding = ItemCategoryBinding.bind(convertView);
        }

        // Lay danh muc tai vi tri
        Category category = categoryList.get(position);

        // Gan du lieu vao cac view thong qua binding
        binding.txtCategory.setText(category.getName());
        binding.imgCategory.setImageResource(category.getImg());

//        // Xu ly su kien click tren item
        binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodListActivity.class);
            intent.putExtra("category", category); // Truyen doi tuong Category qua Intent
            context.startActivity(intent);
        });

        binding.getRoot().setPadding(30, 0, 0, 0); // Them padding ben trai 30px cho layout goc
        return convertView;
    }
}