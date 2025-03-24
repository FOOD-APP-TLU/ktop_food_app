package com.example.ktop_food_app.App.view.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityForgotPasswordBinding;

// Lop ForgotPasswordActivity quan ly giao dien quen mat khau
public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Kich hoat che do hien thi full man hinh
        setContentView(R.layout.activity_forgot_password);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Xu ly khi nhan vao icon Back
        handleBackIcon();

        // Kiem tra va cap nhat trang thai nut Send code
        checkActive();

        // Xu ly khi nhan nut Send code
        handleSendCode();

        // Xu ly TextWatcher de kiem tra du lieu nhap vao theo thoi gian thuc
        handleTextWatchers();
    }

    // Xu ly su kien nhan nut Back
    private void handleBackIcon() {
        binding.btnBackIcon.setOnClickListener(v -> {
            finish(); // Thoat khoi activity
        });
    }

    // Kiem tra va cap nhat trang thai nut Send code
    private void checkActive() {
        if (validateSendCode()) {
            binding.btnSendCode.setBackgroundResource(R.drawable.custom_bg_success); // Doi nen thanh cong
            binding.btnSendCode.setEnabled(true); // Kich hoat nut
        } else {
            binding.btnSendCode.setBackgroundResource(R.drawable.custom_bg_default); // Nen mac dinh
            binding.btnSendCode.setEnabled(false); // Vo hieu hoa nut
        }
    }

    // Xu ly su kien nhan nut Send code
    private void handleSendCode() {
        binding.btnSendCode.setOnClickListener(v -> {
            binding.progressBar.setVisibility(android.view.View.VISIBLE); // Hien thanh tien trinh
            new Handler().postDelayed(() -> {
                binding.progressBar.setVisibility(android.view.View.GONE); // An thanh tien trinh
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent); // Chuyen sang man hinh dang nhap
                finish(); // Dong activity
            }, 1000); // Tre 1 giay
        });
    }

    // Kiem tra tinh hop le cua email
    private boolean validateSendCode() {
        String email = binding.txtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            binding.txtEmail.setError("Please enter username or email");
            return false;
        }

        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$"; // Bieu thuc chinh quy kiem tra email
        if (!email.matches(emailRegex)) {
            binding.txtEmail.setError("Invalid email format");
            return false;
        }

        return true; // Email hop le
    }

    // Xu ly TextWatcher de kiem tra theo thoi gian thuc
    private void handleTextWatchers() {
        binding.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive(); // Kiem tra trang thai nut khi nhap
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}