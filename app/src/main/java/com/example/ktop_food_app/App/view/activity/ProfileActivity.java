package com.example.ktop_food_app.App.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ktop_food_app.App.model.data.entity.User;
import com.example.ktop_food_app.App.model.repository.UserRepository;
import com.example.ktop_food_app.R;
import com.example.ktop_food_app.databinding.ActivityProfileBinding;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepository(); // Khởi tạo repository

        LoadUserData(); // Tải dữ liệu người dùng
        setEditProfileButtonDefault(); // Đặt nút Edit Profile về trạng thái mặc định
        handleTextWatchers(); // Xử lý sự kiện thay đổi text
        handleBackIcon(); // Xử lý nút Back
        handleEditProfileButton(); // Xử lý nút Edit Profile
    }

    // Tải và hiển thị dữ liệu người dùng
    private void LoadUserData() {
        List<User> users = userRepository.getUserList();
        if (!users.isEmpty()) {
            User user = users.get(0);
            binding.imgUser.setImageResource(user.getImg());
            binding.edtUsername.setText(user.getName());
            binding.edtEmail.setText(user.getEmail());
            binding.edtPhone.setText(user.getPhone());
            binding.edtAddress.setText(user.getAddress());
        } else {
            Toast.makeText(this, "No user data available", Toast.LENGTH_SHORT).show();
        }
    }

    // Đặt trạng thái mặc định cho nút Edit Profile
    private void setEditProfileButtonDefault() {
        binding.btnEditProfile.setBackgroundResource(R.drawable.custom_bg_default);
        binding.btnEditProfile.setEnabled(false);
    }

    // Xử lý nút Back
    private void handleBackIcon() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    // Xử lý nút Edit Profile
    private void handleEditProfileButton() {
        binding.btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Save successful", Toast.LENGTH_SHORT).show();
            setEditProfileButtonDefault();
        });
    }

    // Kiểm tra và cập nhật trạng thái nút
    private void checkActive() {
        if (validateEditProfile()) {
            binding.btnEditProfile.setBackgroundResource(R.drawable.custom_bg_success);
            binding.btnEditProfile.setEnabled(true);
        } else {
            binding.btnEditProfile.setBackgroundResource(R.drawable.custom_bg_default);
            binding.btnEditProfile.setEnabled(false);
        }
    }

    // Xác thực dữ liệu nhập
    private boolean validateEditProfile() {
        String username = binding.edtUsername.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String phone = binding.edtPhone.getText().toString().trim();
        String address = binding.edtAddress.getText().toString().trim();

        if (username.isEmpty()) {
            binding.edtUsername.setError("Please enter username");
            return false;
        }
        String emailRegex = "^[a-z0-9._%+-]+@[a-z.-]+\\.[a-z]{2,}$";
        if (!email.matches(emailRegex)) {
            binding.edtEmail.setError("Invalid email format");
            return false;
        }
        if (email.isEmpty()) {
            binding.edtEmail.setError("Please enter email");
            return false;
        }
        if (phone.isEmpty()) {
            binding.edtPhone.setError("Please enter phone");
            return false;
        }
        if (address.isEmpty()) {
            binding.edtAddress.setError("Please enter address");
            return false;
        }
        return true;
    }

    // Xử lý sự kiện thay đổi text
    private void handleTextWatchers() {
        binding.edtUsername.addTextChangedListener(new TextWatcher() {
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

        binding.edtEmail.addTextChangedListener(new TextWatcher() {
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

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
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

        binding.edtAddress.addTextChangedListener(new TextWatcher() {
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