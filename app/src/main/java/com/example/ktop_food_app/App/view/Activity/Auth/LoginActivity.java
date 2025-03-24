package com.example.ktop_food_app.App.view.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ktop_food_app.App.view.Activity.HomeActivity;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityLoginBinding;

// Lop LoginActivity quan ly giao dien dang nhap
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding; // Binding cho layout activity_login.xml
    private boolean isPasswordVisible = false; // Bien kiem tra trang thai hien thi mat khau

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Kich hoat che do hien thi full man hinh

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Gan giao dien tu binding

        // Kiem tra va cap nhat trang thai nut Login
        checkActive();

        // Xu ly su kien nhan nut Login
        handleLogin();

        // Xu ly su kien nhan text Sign Up
        handleSignup();

        // Xu ly hien thi/an mat khau
        handleVisibilityToggle();

        // Xu ly su kien nhan text Forgot Password
        handleForgotPassword();

        // Xu ly TextWatcher de kiem tra du lieu theo thoi gian thuc
        handleTextWatchers();

    }

    // Kiem tra va cap nhat trang thai nut Login
    private void checkActive() {
        if (validateLogin()) {
            binding.btnLogin.setBackgroundResource(R.drawable.custom_bg_success); // Doi nen nut thanh mau thanh cong
            binding.btnLogin.setEnabled(true); // Kich hoat nut
        } else {
            binding.btnLogin.setBackgroundResource(R.drawable.custom_bg_default); // Doi nen nut ve mac dinh
            binding.btnLogin.setEnabled(false); // Vo hieu hoa nut
        }
    }

    // Xu ly su kien nhan nut Login
    private void handleLogin() {
        binding.btnLogin.setOnClickListener(v -> {
            checkActive();
            if (validateLogin()) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent); // Chuyen sang man hinh chinh
                finish(); // Dong activity dang nhap
            }
        });
    }

    // Xu ly su kien nhan text Sign Up
    private void handleSignup() {
        binding.txtSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent); // Chuyen sang man hinh dang ky
            finish(); // Dong activity dang nhap
        });
    }

    // Xu ly hien thi/an mat khau
    private void handleVisibilityToggle() {
        // Toggle for password visibility
        binding.visibilityOffIcon.setOnClickListener(v -> {
            if (isPasswordVisible) {
                binding.edtPassword.setTransformationMethod(new PasswordTransformationMethod()); // An mat khau
                binding.visibilityOffIcon.setImageResource(R.drawable.visibility_off); // Doi icon
            } else {
                binding.edtPassword.setTransformationMethod(null); // Hien mat khau
                binding.visibilityOffIcon.setImageResource(R.drawable.visibility_on); // Doi icon
            }

            binding.edtPassword.setSelection(binding.edtPassword.getText().length()); // Dat con tro cuoi dong

            isPasswordVisible = !isPasswordVisible; // Dao trang thai hien thi
        });
    }

    // Xu ly su kien nhan text Forgot Password
    private void handleForgotPassword() {
        binding.txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent); // Chuyen sang man hinh quen mat khau
        });
    }

    // Kiem tra tinh hop le cua thong tin dang nhap
    private boolean validateLogin() {
        String username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (username.isEmpty()) {
            binding.edtUsername.setError("Please enter username or email");
            return false;
        }

        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$"; // Bieu thuc chinh quy kiem tra email
        if (!username.matches(emailRegex)) {
            binding.edtUsername.setError("Invalid email format");
            return false;
        }

        if (password.isEmpty()) {
            binding.edtPassword.setError("Please enter password");
            return false;
        }

        if (password.length() < 8) {
            binding.edtPassword.setError("Password must be at least 8 characters long");
            return false;
        }

        return true; // Thong tin hop le
    }

    // Xu ly TextWatcher de kiem tra du lieu theo thoi gian thuc
    private void handleTextWatchers() {
        binding.edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive(); // Kiem tra trang thai nut khi nhap username
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive(); // Kiem tra trang thai nut khi nhap password
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}