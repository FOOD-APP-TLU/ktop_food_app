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
import com.example.ktop_food_app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Handle check Login button click
        checkActive();

        // Handle Login Button click
        handleLogin();

        // Handle Sign Up Text click
        handleSignup();

        // Handle password visibility toggle
        handleVisibilityToggle();

        // Handle TextWatchers to validate dynamic
        handleTextWatchers();

    }

    private void checkActive() {
        if (validateLogin()) {
            binding.btnLogin.setBackgroundResource(R.drawable.btn_background_success);
            binding.btnLogin.setEnabled(true);
        } else {
            binding.btnLogin.setBackgroundResource(R.drawable.btn_background_default);
            binding.btnLogin.setEnabled(false);
        }
    }

    private void handleLogin() {
        binding.btnLogin.setOnClickListener(v -> {
            checkActive();
            if (validateLogin()) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSignup() {
        binding.txtSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleVisibilityToggle() {
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
    }

    private boolean validateLogin() {
        String username = binding.txtUsername.getText().toString().trim();
        String password = binding.txtPassword.getText().toString().trim();

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
    }
}
