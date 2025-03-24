package com.example.ktop_food_app.App.view.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivitySignUpBinding;

// Lop SignUpActivity quan ly giao dien dang ky
public class SignUpActivity extends AppCompatActivity {
    private boolean isConfirmPasswordVisible = false; // Bien kiem tra trang thai hien thi xac nhan mat khau
    private ActivitySignUpBinding binding; // Binding cho layout activity_sign_up.xml
    private boolean isPasswordVisible = false; // Bien kiem tra trang thai hien thi mat khau

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Kich hoat che do hien thi full man hinh

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Gan giao dien tu binding

        // Kiem tra va cap nhat trang thai nut SignUp
        checkActive();
        // Xu ly su kien nhan text Login
        handleLogin();

        // Xu ly su kien nhan nut SignUp
        handleSignup();

        // Xu ly hien thi/an mat khau
        handleVisibilityToggle();

        // Xu ly TextWatcher de kiem tra du lieu theo thoi gian thuc
        handleTextWatchers();

        // Xu ly su kien thay doi trang thai checkbox
        handleCheckboxChange();
    }

    // Kiem tra va cap nhat trang thai nut SignUp
    private void checkActive() {
        if (validateSignUp() && binding.checkBox.isChecked()) {
            binding.btnSignup.setBackgroundResource(R.drawable.custom_bg_success); // Doi nen nut thanh mau thanh cong
            binding.btnSignup.setEnabled(true); // Kich hoat nut
        } else {
            binding.btnSignup.setBackgroundResource(R.drawable.custom_bg_default); // Doi nen nut ve mac dinh
            binding.btnSignup.setEnabled(false); // Vo hieu hoa nut
        }
    }

    // Xu ly su kien thay doi trang thai checkbox
    private void handleCheckboxChange() {
        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkActive(); // Kiem tra trang thai nut khi checkbox thay doi
        });
    }

    // Xu ly su kien nhan nut SignUp
    private void handleSignup() {
        binding.btnSignup.setOnClickListener(v -> {
            checkActive();
            if (validateSignUp()) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent); // Chuyen sang man hinh dang nhap
                finish(); // Dong activity dang ky
            }
        });
    }

    // Xu ly su kien nhan text Login
    private void handleLogin() {
        binding.txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent); // Chuyen sang man hinh dang nhap
            finish(); // Dong activity dang ky
        });
    }

    // Xu ly hien thi/an mat khau va xac nhan mat khau
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

        // Toggle for confirm password visibility
        binding.confirmVisibilityOffIcon.setOnClickListener(v -> {
            if (isConfirmPasswordVisible) {
                binding.edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod()); // An xac nhan mat khau
                binding.confirmVisibilityOffIcon.setImageResource(R.drawable.visibility_off); // Doi icon
            } else {
                binding.edtConfirmPassword.setTransformationMethod(null); // Hien xac nhan mat khau
                binding.confirmVisibilityOffIcon.setImageResource(R.drawable.visibility_on); // Doi icon
            }

            binding.edtConfirmPassword.setSelection(binding.edtConfirmPassword.getText().length()); // Dat con tro cuoi dong

            isConfirmPasswordVisible = !isConfirmPasswordVisible; // Dao trang thai hien thi
        });
    }

    // Kiem tra tinh hop le cua thong tin dang ky
    private boolean validateSignUp() {
        String username = binding.edtUserName.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirmpassword = binding.edtConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            binding.edtUserName.setError("Please enter username or email");
            return false;
        }

        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$"; // Bieu thuc chinh quy kiem tra email
        if (!username.matches(emailRegex)) {
            binding.edtUserName.setError("Invalid email format");
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

        if (confirmpassword.isEmpty()) {
            binding.edtConfirmPassword.setError("Please enter confirm password");
            return false;
        }

        if (confirmpassword.length() < 8) {
            binding.edtConfirmPassword.setError("Confirm Password must be at least 8 characters long");
            return false;
        }

        if (!password.equals(confirmpassword)) {
            binding.edtConfirmPassword.setError("Passwords do not match");
            return false;
        }

        return true; // Thong tin hop le
    }

    // Xu ly TextWatcher de kiem tra du lieu theo thoi gian thuc
    private void handleTextWatchers() {
        binding.edtUserName.addTextChangedListener(new TextWatcher() {
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

        binding.edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive(); // Kiem tra trang thai nut khi nhap xac nhan mat khau
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}