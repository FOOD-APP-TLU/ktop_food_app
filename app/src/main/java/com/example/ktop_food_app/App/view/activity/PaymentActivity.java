package com.example.ktop_food_app.App.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.ktop_food_app.App.model.data.entity.PaymentItem;

import com.example.ktop_food_app.App.model.data.remote.FirebasePaymentData;
import com.example.ktop_food_app.App.model.repository.PaymentRepository;
import com.example.ktop_food_app.App.view.adapter.PaymentAdapter;
import com.example.ktop_food_app.App.viewmodel.PaymentViewModel;
import com.example.ktop_food_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Lớp PaymentActivity hiển thị giao diện thanh toán và xử lý tương tác người dùng
public class PaymentActivity extends AppCompatActivity {
    // Các TextView để hiển thị thông tin địa chỉ, tên người dùng, tổng tiền, giảm giá, và số tiền cuối cùng
    private TextView addressTextView, usernameTextView, totalAmountTextView, paymentDetailsAmountTextView, discountAmountTextView,
            totalPaymentDetailsTextView, totalPaymentAmountTextView;
    // RecyclerView để hiển thị danh sách món ăn trong giỏ hàng
    private RecyclerView recyclerViewItems;
    // LinearLayout để chọn phương thức thanh toán (Bank hoặc COD)
    private LinearLayout bankOption, cashOption;
    // ImageView để hiển thị dấu tích khi chọn phương thức thanh toán
    private ImageView bankCheckmark, cashCheckmark;
    // ImageView cho nút quay lại
    private ImageView backArrow;
    // Button để thực hiện thanh toán
    private Button paymentButton;
    // EditText để nhập mã giảm giá
    private EditText voucherCodeInput;
    // TextView để kích hoạt áp dụng mã giảm giá
    private TextView enterCodeLabel;
    // Adapter cho RecyclerView
    private PaymentAdapter adapter;
    // Danh sách món ăn trong giỏ hàng
    private List<CartItem> cartItems;
    // ID của người dùng
    private String userId;
    // Phương thức thanh toán được chọn (mặc định là COD)
    private String selectedPaymentMethod = "COD";
    // ViewModel để quản lý dữ liệu và logic
    private PaymentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Khởi tạo các view từ layout
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

        // Kiểm tra giỏ hàng và hiển thị dữ liệu
        if (cartItems != null && !cartItems.isEmpty()) {
            viewModel.loadUserInfo(userId); // Lấy thông tin người dùng
            viewModel.displayCartItems(cartItems); // Hiển thị giỏ hàng
        } else {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            finish(); // Thoát activity nếu giỏ hàng trống
        }

        // Quan sát LiveData từ ViewModel để cập nhật giao diện
        viewModel.getAddress().observe(this, address -> {
            addressTextView.setText(address); // Cập nhật địa chỉ
        });

        viewModel.getUsername().observe(this, username -> {
            usernameTextView.setText(username); // Cập nhật tên người dùng
        });

        viewModel.getTotalPrice().observe(this, price -> {
            totalAmountTextView.setText(String.format("%,.0f đ", price)); // Hiển thị tổng tiền
            paymentDetailsAmountTextView.setText(String.format("%,.0f đ", price)); // Hiển thị chi tiết tổng tiền
            Double discount = viewModel.getDiscount().getValue();
            if (discount != null) {
                double finalAmount = price - discount;
                totalPaymentDetailsTextView.setText(String.format("%,.0f đ", finalAmount)); // Tổng tiền sau giảm giá
                totalPaymentAmountTextView.setText(String.format("%,.0f đ", finalAmount)); // Tổng tiền thanh toán
            }
        });

        viewModel.getDiscount().observe(this, discount -> {
            discountAmountTextView.setText(String.format("%,.0f đ", discount)); // Hiển thị số tiền giảm giá
        });

        viewModel.getPaymentItems().observe(this, items -> {
            adapter.updateItems(items); // Cập nhật danh sách món ăn trong RecyclerView
        });

        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // Hiển thị thông báo lỗi
            }
        });

        viewModel.getPaymentSuccess().observe(this, success -> {
            if (success != null && success) {
                finish(); // Kết thúc activity nếu thanh toán thành công
            }
        });

        // Thiết lập sự kiện click cho các nút
        backArrow.setOnClickListener(v -> finish()); // Quay lại màn hình trước

        bankOption.setOnClickListener(v -> {
            selectedPaymentMethod = "Banking"; // Chọn phương thức thanh toán Banking
            bankCheckmark.setVisibility(View.VISIBLE);
            cashCheckmark.setVisibility(View.GONE);
        });

        cashOption.setOnClickListener(v -> {
            selectedPaymentMethod = "COD"; // Chọn phương thức thanh toán COD
            cashCheckmark.setVisibility(View.VISIBLE);
            bankCheckmark.setVisibility(View.GONE);
        });

        paymentButton.setOnClickListener(v -> showPaymentConfirmationDialog()); // Hiển thị popup xác nhận thanh toán

        enterCodeLabel.setOnClickListener(v -> {
            String voucherCode = voucherCodeInput.getText().toString().trim();
            viewModel.applyVoucherCode(voucherCode); // Áp dụng mã giảm giá
        });
    }

    // Phương thức hiển thị popup xác nhận thanh toán
    private void showPaymentConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Confirmation");

        // Lấy tổng tiền và giảm giá từ ViewModel
        Double totalPrice = viewModel.getTotalPrice().getValue();
        Double discount = viewModel.getDiscount().getValue();
        double finalAmount = (totalPrice != null && discount != null) ? totalPrice - discount : 0;

        // Tạo thông báo với phương thức thanh toán và tổng tiền
        String message = String.format("Are you sure you want to pay for the %,.0fđ order using %s ",
                 finalAmount,selectedPaymentMethod);
        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                processPayment(); // Tiến hành thanh toán nếu chọn "Yes"
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng popup nếu chọn "No"
            }
        });
        builder.setCancelable(false); // Không cho phép đóng popup bằng cách nhấn ra ngoài
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Phương thức xử lý thanh toán
    private void processPayment() {
        // Kiểm tra nếu giỏ hàng rỗng
        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống, vui lòng chọn món ăn trước!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo ID đơn hàng
        String orderId = "ORDER" + UUID.randomUUID().toString().substring(0, 8);
        Double totalPrice = viewModel.getTotalPrice().getValue();
        Double discount = viewModel.getDiscount().getValue();
        // Kiểm tra nếu tổng tiền hoặc giảm giá không hợp lệ
        if (totalPrice == null || discount == null) {
            Toast.makeText(this, "Lỗi khi tính toán tổng giá trị đơn hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Order với thông tin đơn hàng
        Order order = new Order(
                addressTextView.getText().toString(),
                System.currentTimeMillis(),
                discount,
                cartItems,
                orderId,
                selectedPaymentMethod,
                "pending",
                totalPrice - discount,
                userId
        );

        // Gọi ViewModel để xử lý thanh toán
        viewModel.processPayment(order, userId);
    }
}