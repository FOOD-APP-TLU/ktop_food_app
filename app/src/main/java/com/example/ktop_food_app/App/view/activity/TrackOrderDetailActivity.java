package com.example.ktop_food_app.App.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ktop_food_app.App.model.data.entity.Order;
import com.example.ktop_food_app.App.view.adapter.TrackOrderDetailAdapter;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityTrackOrderDetailBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TrackOrderDetailActivity extends AppCompatActivity {

    private ActivityTrackOrderDetailBinding binding;
    private TrackOrderDetailAdapter itemAdapter;
    private Order order;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityTrackOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat("#,###", symbols);

        // Gọi các hàm
        if (!loadOrderData()) {
            return;
        }
        displayOrderDetails();
        setupRecyclerView();
        setupListeners();
        updateCancelButtonState(order.getStatus());
    }

    // Hàm lấy dữ liệu đơn hàng từ Intent
    private boolean loadOrderData() {
        order = (Order) getIntent().getSerializableExtra("order");
        if (order == null) {
            Toast.makeText(this, "Không thể hiển thị chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
        return true;
    }

    // Hàm hiển thị chi tiết đơn hàng
    private void displayOrderDetails() {
        binding.txtOrderId.setText(order.getOrderId());
        binding.txtOrderStatus.setText(order.getStatus());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault());
        String orderTime;
        try {
            orderTime = dateFormat.format(new Date(order.getCreatedAt()));
        } catch (Exception e) {
            Log.e("TrackOrderDetail", "Error formatting date: " + e.getMessage());
            orderTime = "N/A";
        }
        binding.txtOrderTime.setText(orderTime);

        String paymentMethod = order.getPaymentMethod();
        if ("COD".equalsIgnoreCase(paymentMethod)) {
            binding.paymentMethod.setText("Cash on Delivery");
            binding.paymentIcon.setImageResource(R.drawable.ic_cod);
        } else if ("Bank".equalsIgnoreCase(paymentMethod)) {
            binding.paymentMethod.setText(paymentMethod);
            binding.paymentIcon.setImageResource(R.drawable.ic_bank);
        } else {
            binding.paymentMethod.setText(paymentMethod);
            binding.paymentIcon.setVisibility(View.GONE);
        }

        binding.txtTotalAmount.setText(decimalFormat.format(order.getTotalPrice()) + " d");
        binding.txtTotalPriceOfItem.setText(decimalFormat.format(order.getTotalPrice()) + " d");
        binding.txtDiscount.setText(decimalFormat.format(order.getDiscount()) + " d");
        binding.txtTotalPayment.setText(decimalFormat.format(order.getTotalPrice() - order.getDiscount()) + " d");

    }

    // Hàm thiết lập RecyclerView
    private void setupRecyclerView() {
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            itemAdapter = new TrackOrderDetailAdapter(this, order.getItems());
            binding.recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewItems.setAdapter(itemAdapter);
        } else {
            Log.w("TrackOrderDetail", "Order has no items to display");
            Toast.makeText(this, "Đơn hàng không có sản phẩm!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCancelButtonState(String status) {
        if ("Pending".equalsIgnoreCase(status)) {
            binding.cancelOrderButton.setEnabled(true);
            binding.cancelOrderButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bg_btn_enabled_true));
            binding.orderReceivedButton.setEnabled(false);
            binding.orderReceivedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bg_btn_enabled_false));
        } else if ("Shipping".equalsIgnoreCase(status)) {
            binding.cancelOrderButton.setEnabled(false);
            binding.cancelOrderButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bg_btn_enabled_false));
        }
    }

    // Hàm thiết lập sự kiện cho nút back
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        DatabaseReference ordersRef = FirebaseDatabase.getInstance("https://ktop-food-database-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("orders")
                .child(order.getOrderId());

        // Nút xác nhận đã nhận hàng
        binding.orderReceivedButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Order Confirmation")
                    .setMessage("Have you received your order?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ordersRef.child("status").setValue("completed")
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Order marked as received!", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error updating order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Nút hủy đơn hàng
        binding.cancelOrderButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Cancel Order")
                    .setMessage("Are you sure you want to cancel this order?")
                    .setPositiveButton("Cancel Order", (dialog, which) -> {
                        ordersRef.child("status").setValue("cancelled")
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Order has been cancelled.", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error updating order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
