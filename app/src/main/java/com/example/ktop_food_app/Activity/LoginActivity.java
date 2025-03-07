package com.example.ktop_food_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

        // Handle Login Button click
        handleLogin();

        // Handle Sign Up Text click
        handleSignup();

        // Handle password visibility toggle
        handleVisibilityToggle();
    }

    private void handleLogin() {
        binding.btnLogin.setOnClickListener(v -> {
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
        String username = binding.txtEmail.getText().toString().trim();
        String password = binding.txtPassword.getText().toString().trim();

        if (username.isEmpty()) {
            binding.txtEmail.setError("Please enter username or email");
            binding.txtEmail.requestFocus();
            return false;
        }

        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$";
        if (!username.matches(emailRegex)) {
            binding.txtEmail.setError("Invalid email format");
            binding.txtEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            binding.txtPassword.setError("Please enter password");
            binding.txtPassword.requestFocus();
            return false;
        }

        if (password.length() < 8) {
            binding.txtPassword.setError("Password must be at least 8 characters long");
            binding.txtPassword.requestFocus();
            return false;
        }

        return true;
    }
}
