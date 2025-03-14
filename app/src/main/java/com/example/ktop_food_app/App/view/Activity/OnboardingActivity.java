package com.example.ktop_food_app.App.view.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.ktop_food_app.App.model.Data.Firebase.FirebaseActivity;
import com.example.ktop_food_app.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends FirebaseActivity {
    ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#F9C77A"));
    }

    private void setVariable() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}