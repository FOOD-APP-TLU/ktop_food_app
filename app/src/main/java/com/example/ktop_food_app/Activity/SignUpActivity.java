package com.example.ktop_food_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Handle check SignUp button click
        checkActive();
        // Handle Login Text click
        handleLogin();

        // Handle SignUp button click
        handleSignup();

        // Handle Password visibility toggle
        handleVisibilityToggle();

        // Handle TextWatchers to validate dynamic
        handleTextWatchers();

        // Handle Checkbox change
        handleCheckboxChange();
    }

    private void checkActive() {
        if (validateSignUp() && binding.checkBox.isChecked()) {
            binding.btnSignup.setBackgroundResource(R.drawable.btn_background_success);
            binding.btnSignup.setEnabled(true);
        } else {
            binding.btnSignup.setBackgroundResource(R.drawable.btn_background_default);
            binding.btnSignup.setEnabled(false);
        }
    }

    private void handleCheckboxChange() {
        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkActive();
        });
    }

    private void handleSignup() {
        binding.btnSignup.setOnClickListener(v -> {
            checkActive();
            if (validateSignUp()) {
                Toast.makeText(SignUpActivity.this, "Register account successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleLogin() {
        binding.txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleVisibilityToggle() {
        // Toggle for password visibility
        binding.imgVisibilityOff.setOnClickListener(v -> {
            if (isPasswordVisible) {
                binding.txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.imgVisibilityOff.setImageResource(R.drawable.visibility_off);
            } else {
                binding.txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.imgVisibilityOff.setImageResource(R.drawable.visibility_on);
            }
            binding.txtPassword.setSelection(binding.txtPassword.getText().length());

            isPasswordVisible = !isPasswordVisible;
        });

        // Toggle for confirm password visibility
        binding.imgConfirmVisibilityOff.setOnClickListener(v -> {
            if (isConfirmPasswordVisible) {
                binding.txtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.imgConfirmVisibilityOff.setImageResource(R.drawable.visibility_off);
            } else {
                binding.txtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.imgConfirmVisibilityOff.setImageResource(R.drawable.visibility_on);
            }
            binding.txtConfirmPassword.setSelection(binding.txtConfirmPassword.getText().length());

            isConfirmPasswordVisible = !isConfirmPasswordVisible;
        });
    }

    private boolean validateSignUp() {
        String username = binding.txtUsername.getText().toString().trim();
        String password = binding.txtPassword.getText().toString().trim();
        String confirmpassword = binding.txtConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            binding.txtUsername.setError("Please enter username or email");
            return false;
        }

        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$";
        if (!username.matches(emailRegex)) {
            binding.txtUsername.setError("Invalid email format");
            return false;
        }

        if (password.isEmpty()) {
            binding.txtPassword.setError("Please enter password");
            return false;
        }

        if (password.length() < 8) {
            binding.txtPassword.setError("Password must be at least 8 characters long");
            return false;
        }

        if (confirmpassword.isEmpty()) {
            binding.txtConfirmPassword.setError("Please enter confirm password");
            return false;
        }

        if (confirmpassword.length() < 8) {
            binding.txtConfirmPassword.setError("Confirm Password must be at least 8 characters long");
            return false;
        }

        if (!password.equals(confirmpassword)) {
            binding.txtConfirmPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }


    private void handleTextWatchers() {
        binding.txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                checkActive();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}