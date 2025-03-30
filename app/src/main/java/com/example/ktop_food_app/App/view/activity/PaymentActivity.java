package com.example.ktop_food_app.App.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ktop_food_app.App.model.data.entity.CartItem;
import com.example.ktop_food_app.App.model.data.entity.Food;
import com.example.ktop_food_app.App.model.data.entity.Order;
import com.example.ktop_food_app.App.model.data.entity.PaymentItem;
import com.example.ktop_food_app.App.view.adapter.PaymentAdapter;
import com.example.ktop_food_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {
    private TextView addressTextView, totalAmountTextView, paymentDetailsAmountTextView, discountAmountTextView,
            totalPaymentDetailsTextView, totalPaymentAmountTextView;
    private RecyclerView recyclerViewItems;
    private RadioGroup paymentMethodGroup;
    private Button paymentButton;
    private EditText voucherCodeInput;
    private TextView enterCodeLabel;
    private PaymentAdapter adapter;
    private List<PaymentItem> paymentItems;
    private List<CartItem> cartItems;
    private List<Food> foods;
    private DatabaseReference databaseReference;
    private String userId;
    private double totalPrice = 0.0;
    private double discount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Khởi tạo các view với ID
        addressTextView = findViewById(R.id.address_text_view);
        totalAmountTextView = findViewById(R.id.total_amount_text_view);
        paymentDetailsAmountTextView = findViewById(R.id.payment_details_amount_text_view);
        discountAmountTextView = findViewById(R.id.discount_amount_text_view);
        totalPaymentDetailsTextView = findViewById(R.id.total_payment_details_text_view);
        totalPaymentAmountTextView = findViewById(R.id.total_payment_amount_text_view);
        recyclerViewItems = findViewById(R.id.recycler_view_items);
        paymentMethodGroup = findViewById(R.id.payment_method_group);
        paymentButton = findViewById(R.id.payment_button);
        voucherCodeInput = findViewById(R.id.voucher_code_input);
        enterCodeLabel = findViewById(R.id.enter_code_label);

        // Khởi tạo RecyclerView
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        paymentItems = new ArrayList<>();
        adapter = new PaymentAdapter(paymentItems);
        recyclerViewItems.setAdapter(adapter);

        // Khởi tạo Firebase với URL cụ thể
        databaseReference = FirebaseDatabase.getInstance("https://ktop-food-database-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        cartItems = intent.getParcelableArrayListExtra("cartItems");
        userId = intent.getStringExtra("userId");

        // Lấy thông tin người dùng và hiển thị giỏ hàng
        loadUserInfo();
        if (cartItems != null && !cartItems.isEmpty()) {
            displayCartItemsFromIntent(); // Hiển thị dữ liệu từ Intent thay vì tải lại từ Firebase
            loadCartAndFoods(); // Tải thêm dữ liệu món ăn nếu cần
        } else {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Xử lý sự kiện nút thanh toán
        paymentButton.setOnClickListener(v -> processPayment());

        // Xử lý sự kiện nhập mã giảm giá
        enterCodeLabel.setOnClickListener(v -> applyVoucherCode());
    }

    private void loadUserInfo() {
        databaseReference.child("users").child(userId).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String address = dataSnapshot.child("address").getValue(String.class);
                    if (address != null && !address.isEmpty()) {
                        addressTextView.setText(address);
                    } else {
                        addressTextView.setText("Chưa có địa chỉ, vui lòng cập nhật!");
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PaymentActivity.this, "Lỗi khi tải thông tin: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCartAndFoods() {
        databaseReference.child("foods").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foods = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food = snapshot.getValue(Food.class);
                    if (food != null) {
                        foods.add(food);
                    }
                }
                displayCartItems();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PaymentActivity.this, "Lỗi khi tải danh sách món ăn: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayCartItemsFromIntent() {
        paymentItems.clear();
        totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            double itemTotalPrice = cartItem.getPrice() * cartItem.getQuantity();
            totalPrice += itemTotalPrice;
            PaymentItem paymentItem = new PaymentItem(
                    cartItem.getName(),
                    cartItem.getPrice(),
                    cartItem.getQuantity(),
                    itemTotalPrice,
                    cartItem.getImagePath()
            );
            paymentItems.add(paymentItem);
        }
        adapter.notifyDataSetChanged();
        updatePaymentDetails();
    }

    private void displayCartItems() {
        paymentItems.clear();
        totalPrice = 0.0;
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem cartItem : cartItems) {
                for (Food food : foods) {
                    if (food.getFoodId().equals(cartItem.getFoodId())) {
                        double itemTotalPrice = food.getPrice() * cartItem.getQuantity();
                        totalPrice += itemTotalPrice;
                        PaymentItem paymentItem = new PaymentItem(
                                food.getTitle(),
                                food.getPrice(),
                                cartItem.getQuantity(),
                                itemTotalPrice,
                                food.getImagePath()
                        );
                        paymentItems.add(paymentItem);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
        updatePaymentDetails();
    }

    private void updatePaymentDetails() {
        totalAmountTextView.setText(String.format("%,.0f đ", totalPrice));
        paymentDetailsAmountTextView.setText(String.format("%,.0f đ", totalPrice));
        discountAmountTextView.setText(String.format("%,.0f đ", discount));
        totalPaymentDetailsTextView.setText(String.format("%,.0f đ", totalPrice - discount));
        totalPaymentAmountTextView.setText(String.format("%,.0f đ", totalPrice - discount));
    }

    private void applyVoucherCode() {
        String voucherCode = voucherCodeInput.getText().toString().trim();
        if (voucherCode.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã giảm giá!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (voucherCode.equals("DISCOUNT5000")) {
            discount = 5000.0;
            Toast.makeText(this, "Áp dụng mã giảm giá thành công!", Toast.LENGTH_SHORT).show();
        } else {
            discount = 0.0;
            Toast.makeText(this, "Mã giảm giá không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
        updatePaymentDetails();
    }

    private void processPayment() {
        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống, vui lòng chọn món ăn trước!", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = paymentMethodGroup.getCheckedRadioButtonId();
        String paymentMethod = selectedId == R.id.radio_bank ? "Bank" : "COD";

        String orderId = "ORDER" + UUID.randomUUID().toString().substring(0, 8);
        Order order = new Order(
                addressTextView.getText().toString(),
                System.currentTimeMillis(),
                discount,
                cartItems,
                orderId,
                paymentMethod,
                "pending",
                totalPrice - discount,
                userId
        );

        databaseReference.child("orders").child(orderId).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    databaseReference.child("users").child(userId).child("cart").removeValue();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi đặt hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}