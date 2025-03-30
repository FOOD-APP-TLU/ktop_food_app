package com.example.ktop_food_app.App.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ktop_food_app.App.model.data.entity.CartItem;
import com.example.ktop_food_app.App.model.data.remote.FirebaseAuthData;
import com.example.ktop_food_app.App.model.repository.AuthRepository;
import com.example.ktop_food_app.App.view.adapter.CartAdapter;
import com.example.ktop_food_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnTotalPriceChangedListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private Button placeOrderButton;
    private TextView totalPriceTextView;
    private ImageView btnBack;
    private DatabaseReference cartRef;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize UI components
        recyclerView = findViewById(R.id.cart_recycler_view);
        placeOrderButton = findViewById(R.id.place_order_button);
        btnBack = findViewById(R.id.back_button);

        // Initialize Firebase access through repository
        authRepository = new AuthRepository(new FirebaseAuthData());
        cartRef = authRepository.getDatabaseReference()
                .child("users")
                .child(authRepository.getCurrentUser().getUid())
                .child("cart").child("items");

        // Initialize cart data
        cartItems = new ArrayList<>();
        setupRecyclerView();
        loadCartFromFirebase();

        // Set up listeners
        btnBack.setOnClickListener(v -> finish());
        placeOrderButton.setOnClickListener(v -> placeOrder());

        cartAdapter.setOnItemRemovedListener(position -> removeItemFromFirebase(position));
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setAdapter(cartAdapter);
    }

    private void loadCartFromFirebase() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartItem item = snapshot.getValue(CartItem.class);
                    if (item != null) {
                        cartItems.add(item);
                    }
                }
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CartActivity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeItemFromFirebase(int position) {
        String foodId = cartItems.get(position).getFoodId();
        cartRef.child(foodId).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(CartActivity.this, "Đã xóa món hàng", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(CartActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateTotalPrice() {
        long total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        if (totalPriceTextView != null) {
            totalPriceTextView.setText(String.format("%,d đ", total));
        }
    }

    @Override
    public void onTotalPriceChanged(long totalPrice) {
        if (totalPriceTextView != null) {
            totalPriceTextView.setText(String.format("%,d đ", totalPrice));
        }
    }

    private void placeOrder() {
        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống, vui lòng thêm món ăn trước!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển sang PaymentActivity và truyền dữ liệu giỏ hàng
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        intent.putParcelableArrayListExtra("cartItems", new ArrayList<>(cartItems)); // Truyền danh sách cartItems
        intent.putExtra("userId", authRepository.getCurrentUser().getUid()); // Truyền userId
        startActivity(intent);
    }
}