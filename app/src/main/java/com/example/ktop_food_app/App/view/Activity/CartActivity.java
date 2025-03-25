package com.example.ktop_food_app.App.view.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktop_food_app.App.controller.Adapter.CartAdapter;
import com.example.ktop_food_app.App.model.Entity.CartItem;
import com.example.ktop_food_app.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnTotalPriceChangedListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private Button placeOrderButton;
    private TextView totalPriceTextView;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize UI components
        recyclerView = findViewById(R.id.cart_recycler_view);
        placeOrderButton = findViewById(R.id.place_order_button);
        btnBack = findViewById(R.id.back_button);

        // Initialize cart data
        initCartItems();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);


        // Calculate and display total
//        updateTotalPrice();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Set up order button
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle order placement
                placeOrder();
            }
        });
        cartAdapter.setOnItemRemovedListener(new CartAdapter.OnItemRemovedListener() {
            @Override
            public void onItemRemoved(int position) {
                // Bạn có thể thêm xử lý bổ sung ở đây nếu cần
                // Ví dụ: hiển thị thông báo "Đã xóa món hàng"
            }
        });
    }

    private void initCartItems() {
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Chicken Drumsticks", 80000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("Cheeseburger", 60000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("CoCa CoLa", 12000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("Fanta orange", 12000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("Fanta orange", 12000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("Fanta orange", 12000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("Fanta orange", 12000, 1, R.drawable.mozzahotdog));
        cartItems.add(new CartItem("Fanta orange", 12000, 1, R.drawable.mozzahotdog));
    }

//    private void updateTotalPrice() {
//        long total = 0;
//        for (CartItem item : cartItems) {
//            total += item.getPrice() * item.getQuantity();
//        }

    // Update total display
//        if (totalPriceTextView != null) {
//            totalPriceTextView.setText(String.format("%,d đ", total));
//        }
//    }

    @Override
    public void onTotalPriceChanged(long totalPrice) {
        if (totalPriceTextView != null) {
            totalPriceTextView.setText(String.format("%,d đ", totalPrice));
        }
    }
    private void placeOrder() {
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
    }

}