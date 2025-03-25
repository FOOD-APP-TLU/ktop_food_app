package com.example.ktop_food_app.App.model.Repository;

import com.example.ktop_food_app.App.model.Data.Firebase.FirebaseAuthDataSource;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private FirebaseAuthDataSource authDataSource;

    // Constructor nhận FirebaseAuthDataSource
    public AuthRepository(FirebaseAuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    // Kiểm tra user hiện tại
    public FirebaseUser getCurrentUser() {
        return authDataSource.getCurrentUser();
    }

    // Đăng nhập
    public Task signInWithEmailAndPassword(String email, String password) {
        return authDataSource.signInWithEmailAndPassword(email, password);
    }

    // Đăng ký
    public Task createUserWithEmailAndPassword(String email, String password) {
        return authDataSource.createUserWithEmailAndPassword(email, password);
    }

    // Gửi email đặt lại mật khẩu
    public Task sendPasswordResetEmail(String email) {
        return authDataSource.sendPasswordResetEmail(email);
    }

    // Lưu thông tin user vào Realtime Database
    public Task saveUserToDatabase(String userId, String email) {
        return authDataSource.saveUserToDatabase(userId, email);
    }
}