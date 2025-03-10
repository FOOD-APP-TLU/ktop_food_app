package com.example.ktop_food_app.App.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handle Back icon click
        handleBackIcon();

        //Handle check Send code button click
        checkActive();

        // Handle Send code button click
        handleSendCode();

        // Handle TextWatchers to validate dynamic
        handleTextWatchers();
    }

    private void handleBackIcon() {
        binding.btnBackIcon.setOnClickListener(v -> {
            finish();
        });
    }

    private void checkActive() {
        if (validateSendCode()) {
            binding.btnSendCode.setBackgroundResource(R.drawable.btn_background_success);
            binding.btnSendCode.setEnabled(true);
        } else {
            binding.btnSendCode.setBackgroundResource(R.drawable.btn_background_default);
            binding.btnSendCode.setEnabled(false);
        }
    }

    private void handleSendCode() {
        binding.btnSendCode.setOnClickListener(v -> {
            binding.progressBar.setVisibility(android.view.View.VISIBLE);
            new Handler().postDelayed(() -> {
                binding.progressBar.setVisibility(android.view.View.GONE);
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, 1000);
        });
    }


    private boolean validateSendCode() {
        String email = binding.txtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            binding.txtEmail.setError("Please enter username or email");
            return false;
        }

        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$";
        if (!email.matches(emailRegex)) {
            binding.txtEmail.setError("Invalid email format");
            return false;
        }

        return true;
    }

    private void handleTextWatchers() {
        binding.txtEmail.addTextChangedListener(new TextWatcher() {
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