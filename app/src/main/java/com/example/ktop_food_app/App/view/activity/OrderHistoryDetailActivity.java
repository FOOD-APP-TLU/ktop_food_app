package com.example.ktop_food_app.App.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ktop_food_app.App.model.data.entity.Order;
import com.example.ktop_food_app.App.view.adapter.OrderHistoryDetailAdapter;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityOrderHistoryDetailBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderHistoryDetailActivity extends AppCompatActivity {

    private ActivityOrderHistoryDetailBinding binding;
    private OrderHistoryDetailAdapter itemAdapter;
    private Order order;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityOrderHistoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat("#,###d", symbols);

        // Gọi các hàm
        if (!loadOrderData()) {
            return;
        }
        displayOrderDetails();
        setupRecyclerView();
        setupListeners();
    }

    // Hàm lấy dữ liệu đơn hàng từ Intent
    private boolean loadOrderData() {
        order = (Order) getIntent().getSerializableExtra("order");
        if (order == null) {
            Log.e("OrderHistoryDetail", "Order is null, cannot display details");
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
            Log.e("OrderHistoryDetail", "Error formatting date: " + e.getMessage());
            orderTime = "N/A";
        }
        binding.txtOrderTime.setText(orderTime);

        String paymentMethod = order.getPaymentMethod();
        if ("COD".equalsIgnoreCase(paymentMethod)) {
            binding.paymentMethod.setText("Cash on Delivery");
            binding.paymentIcon.setImageResource(R.drawable.ic_cod);
        } else if ("Banking(ZaloPay)".equalsIgnoreCase(paymentMethod)) {
            binding.paymentMethod.setText(paymentMethod);
            binding.paymentIcon.setImageResource(R.drawable.ic_bank);
        } else {
            binding.paymentMethod.setText(paymentMethod);
            binding.paymentIcon.setVisibility(View.GONE);
        }

        double subtotal = order.getTotalPrice() - order.getDiscount();
        binding.txtTotalAmount.setText(decimalFormat.format(order.getTotalPrice()));
        binding.txtTotalPriceItemsAmount.setText(decimalFormat.format(order.getTotalPrice()));
        binding.txtDiscountAmount.setText(decimalFormat.format(order.getDiscount()));
        binding.txtTotalPaymentDetails.setText(decimalFormat.format(subtotal));
    }

    // Hàm thiết lập RecyclerView
    private void setupRecyclerView() {
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            itemAdapter = new OrderHistoryDetailAdapter(this, order.getItems());
            binding.recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewItems.setAdapter(itemAdapter);
        } else {
            Log.w("OrderHistoryDetail", "Order has no items to display");
            Toast.makeText(this, "Đơn hàng không có sản phẩm!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm thiết lập sự kiện cho nút
    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());
    }
}