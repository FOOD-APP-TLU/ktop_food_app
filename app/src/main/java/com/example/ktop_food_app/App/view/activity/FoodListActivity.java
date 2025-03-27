package com.example.ktop_food_app.App.view.activity;

// Import thu vien can thiet

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktop_food_app.App.model.data.entity.Category;
import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.App.model.data.local.DataGenerator;
import com.example.ktop_food_app.App.view.adapter.FoodAdapter;
import com.example.ktop_food_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Lop FoodListActivity ke thua AppCompatActivity
public class FoodListActivity extends AppCompatActivity {
    private RecyclerView recyclerView; // RecyclerView de hien thi danh sach mon an
    private FoodAdapter foodAdapter; // Adapter quan ly du lieu RecyclerView
    private Category category; // Danh muc duoc chon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list); // Gan layout cho activity

        recyclerView = findViewById(R.id.recyclerView); // Lay RecyclerView tu layout
        TextView title = findViewById(R.id.txtCategoryTitle); // Lay TextView de hien thi ten danh muc

        // Nhan du lieu danh muc tu Intent
        Object serializableExtra = getIntent().getSerializableExtra("category");
        category = (Category) serializableExtra; // Ep kieu thanh Category

        title.setText(category.getName()); // Hien thi ten danh muc

        // Loc danh sach mon an theo danh muc
        List<Food> filteredFoods = new ArrayList<>(); // Tao danh sach rong

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API >= 24, dung Stream de loc
            filteredFoods = DataGenerator.generateSampleFoods().stream()
                    .filter(food -> food.getCategory() != null && food.getCategory().getName().equals(category.getName()))
                    .collect(Collectors.toList());
        } else { // API < 24, dung vong lap de loc
            for (Food food : DataGenerator.generateSampleFoods()) {
                if (food.getCategory() != null && food.getCategory().getName().equals(category.getName())) {
                    filteredFoods.add(food);
                }
            }
        }

        // Nut back
        ImageView btnBack = findViewById(R.id.btnBack); // Lay nut back
        btnBack.setOnClickListener(v -> finish()); // Click de quay lai man hinh truoc

        // Cau hinh RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Thiet lap luoi 2 cot
        foodAdapter = new FoodAdapter(this, filteredFoods); // Khoi tao adapter
        recyclerView.setAdapter(foodAdapter); // Gan adapter cho RecyclerView

        // Thay doi layout_width cua item
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(android.view.View view) { // Khi item duoc gan vao
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT; // Dat width thanh match_parent
                view.setLayoutParams(layoutParams);
            }

            @Override
            public void onChildViewDetachedFromWindow(android.view.View view) { // Khi item bi go bo
                // Khong xu ly
            }
        });

        // Them khoang cach giua cac item
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));
    }

    // Lop tao khoang cach giua cac item
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spanCount; // So cot
        private final int spacing; // Khoang cach (dp)
        private final boolean includeEdge; // Tinh ca vien ngoai

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) { // Khoi tao
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull android.graphics.Rect outRect, @NonNull android.view.View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) { // Tinh khoang cach
            int position = parent.getChildAdapterPosition(view); // Lay vi tri item
            int column = position % spanCount; // Tinh cot

            if (includeEdge) { // Tinh ca vien ngoai
                outRect.left = spacing - column * spacing / spanCount; // Khoang cach trai
                outRect.right = (column + 1) * spacing / spanCount; // Khoang cach phai
                if (position < spanCount) outRect.top = spacing; // Khoang cach tren (hang dau)
                outRect.bottom = spacing; // Khoang cach duoi
            } else { // Khong tinh vien ngoai
                outRect.left = column * spacing / spanCount; // Khoang cach trai
                outRect.right = spacing - (column + 1) * spacing / spanCount; // Khoang cach phai
                if (position >= spanCount) outRect.top = spacing; // Khoang cach tren (tu hang 2)
            }
        }
    }
}