package com.example.ktop_food_app.App.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktop_food_app.App.model.data.entity.CartItem;
import com.example.ktop_food_app.App.model.data.entity.Order;
import com.example.ktop_food_app.App.model.data.remote.FirebasePaymentData;
import com.example.ktop_food_app.App.model.repository.PaymentRepository;
import com.example.ktop_food_app.App.view.adapter.PaymentAdapter;
import com.example.ktop_food_app.App.viewmodel.PaymentViewModel;
import com.example.ktop_food_app.App.zalopayapi.Api.CreateOrder;
import com.example.ktop_food_app.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
    private TextView addressTextView, usernameTextView, totalAmountTextView, paymentDetailsAmountTextView,
            discountAmountTextView, totalPaymentDetailsTextView, totalPaymentAmountTextView;
    private RecyclerView recyclerViewItems;
    private LinearLayout bankOption, cashOption;
    private ImageView bankCheckmark, cashCheckmark, backArrow;
    private Button paymentButton;
    private EditText voucherCodeInput;
    private TextView enterCodeLabel;
    private PaymentAdapter adapter;
    private List<CartItem> cartItems;
    private String userId;
    private String selectedPaymentMethod = "COD";
    private PaymentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Cho phép thực thi mạng trên luồng chính (chỉ dùng cho demo)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Khởi tạo ZaloPay SDK
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        // Khởi tạo các view
        addressTextView = findViewById(R.id.address_text_view);
        usernameTextView = findViewById(R.id.username_text_view);
        totalAmountTextView = findViewById(R.id.total_amount_text_view);
        paymentDetailsAmountTextView = findViewById(R.id.payment_details_amount_text_view);
        discountAmountTextView = findViewById(R.id.discount_amount_text_view);
        totalPaymentDetailsTextView = findViewById(R.id.total_payment_details_text_view);
        totalPaymentAmountTextView = findViewById(R.id.total_payment_amount_text_view);
        recyclerViewItems = findViewById(R.id.recycler_view_items);
        bankOption = findViewById(R.id.bank_option);
        cashOption = findViewById(R.id.cash_option);
        bankCheckmark = findViewById(R.id.bank_checkmark);
        cashCheckmark = findViewById(R.id.cash_checkmark);
        backArrow = findViewById(R.id.back_arrow);
        paymentButton = findViewById(R.id.payment_button);
        voucherCodeInput = findViewById(R.id.voucher_code_input);
        enterCodeLabel = findViewById(R.id.enter_code_label);

        // Khởi tạo RecyclerView và adapter
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(new ArrayList<>());
        recyclerViewItems.setAdapter(adapter);

        // Khởi tạo PaymentRepository và PaymentViewModel
        FirebasePaymentData firebasePaymentData = new FirebasePaymentData();
        PaymentRepository repository = new PaymentRepository(firebasePaymentData);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                return (T) new PaymentViewModel(repository);
            }
        }).get(PaymentViewModel.class);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        cartItems = intent.getParcelableArrayListExtra("cartItems");
        userId = intent.getStringExtra("userId");

        // Kiểm tra giỏ hàng
        if (cartItems != null && !cartItems.isEmpty()) {
            viewModel.loadUserInfo(userId);
            viewModel.displayCartItems(cartItems);
        } else {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Quan sát LiveData
        viewModel.getAddress().observe(this, address -> addressTextView.setText(address));
        viewModel.getUsername().observe(this, username -> usernameTextView.setText(username));
        viewModel.getTotalPrice().observe(this, price -> {
            totalAmountTextView.setText(String.format("%,.0f đ", price));
            paymentDetailsAmountTextView.setText(String.format("%,.0f đ", price));
        });
        viewModel.getDiscount().observe(this, discount -> discountAmountTextView.setText(String.format("%,.0f đ", discount)));
        viewModel.getFinalPrice().observe(this, finalPrice -> {
            totalPaymentDetailsTextView.setText(String.format("%,.0f đ", finalPrice));
            totalPaymentAmountTextView.setText(String.format("%,.0f đ", finalPrice));
        });
        viewModel.getPaymentItems().observe(this, adapter::updateItems);
        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null) Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
        viewModel.getPaymentSuccess().observe(this, success -> {
            if (success != null && success) finish();
        });

        // Thiết lập sự kiện click
        backArrow.setOnClickListener(v -> finish());
        bankOption.setOnClickListener(v -> {
            selectedPaymentMethod = "Banking(Zalo Pay)";
            bankCheckmark.setVisibility(View.VISIBLE);
            cashCheckmark.setVisibility(View.GONE);
        });
        cashOption.setOnClickListener(v -> {
            selectedPaymentMethod = "COD";
            cashCheckmark.setVisibility(View.VISIBLE);
            bankCheckmark.setVisibility(View.GONE);
        });
        paymentButton.setOnClickListener(v -> showPaymentConfirmationDialog());
        enterCodeLabel.setOnClickListener(v -> {
            String voucherCode = voucherCodeInput.getText().toString().trim();
            viewModel.applyVoucherCode(voucherCode);
        });
    }

    private void showPaymentConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thanh toán");

        Double finalAmount = viewModel.getFinalPrice().getValue();
        if (finalAmount == null) finalAmount = 0.0;

        String message = String.format("Bạn có chắc chắn muốn thanh toán %,.0fđ bằng %s không?",
                finalAmount, selectedPaymentMethod);
        builder.setMessage(message);

        Double finalAmount1 = finalAmount;
        builder.setPositiveButton("Có", (dialog, which) -> {
            if ("Banking(Zalo Pay)".equals(selectedPaymentMethod)) {
                processZaloPayPayment(String.valueOf(finalAmount1.longValue()));
            } else {
                processPayment();
            }
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        builder.create().show();
    }

    private void processZaloPayPayment(String amount) {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("return_code");
//            Toast.makeText(this, "return_code: " + code, Toast.LENGTH_SHORT).show();

            if (code.equals("1")) {
                String zpTransToken = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(this, zpTransToken, "zaloapp://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String transactionId, String transToken, String appTransID) {
                        runOnUiThread(() -> {
                            new AlertDialog.Builder(PaymentActivity.this)
                                    .setTitle("Thanh toán thành công")
//                                    .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                    .setPositiveButton("OK", (dialog, which) -> processPayment())
                                    .setNegativeButton("Hủy", null)
                                    .show();
                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(PaymentActivity.this)
                                .setTitle("Hủy thanh toán")
//                                .setMessage(String.format("zpTransToken: %s", zpTransToken))
                                .setPositiveButton("OK", (dialog, which) -> {})
                                .setNegativeButton("Hủy", null)
                                .show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(PaymentActivity.this)
                                .setTitle("Thanh toán thất bại")
//                                .setMessage(String.format("ZaloPayErrorCode: %s - TransToken: %s", zaloPayError.toString(), zpTransToken))
                                .setPositiveButton("OK", (dialog, which) -> {})
                                .setNegativeButton("Hủy", null)
                                .show();
                    }
                });
            } else {
                Toast.makeText(this, "Tạo đơn hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ZaloPayError", "Lỗi khi tạo đơn hàng: " + e.getMessage());
            Toast.makeText(this, "Lỗi hệ thống, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
    }

    private void processPayment() {
        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống, vui lòng chọn món ăn trước!", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId = "ORDER" + UUID.randomUUID().toString().substring(0, 8);
        Double totalPrice = viewModel.getTotalPrice().getValue();
        Double discount = viewModel.getDiscount().getValue();
        Double finalPrice = viewModel.getFinalPrice().getValue();

        if (totalPrice == null || discount == null || finalPrice == null) {
            Toast.makeText(this, "Lỗi khi tính toán tổng giá trị đơn hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        double actualDiscount = Math.min(discount, totalPrice);
        Order order = new Order(
                addressTextView.getText().toString(),
                System.currentTimeMillis(),
                actualDiscount,
                cartItems,
                orderId,
                selectedPaymentMethod,
                "pending",
                finalPrice,
                userId
        );

        viewModel.processPayment(order, userId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}