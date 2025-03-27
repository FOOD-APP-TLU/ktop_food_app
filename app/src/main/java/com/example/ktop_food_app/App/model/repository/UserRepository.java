package com.example.ktop_food_app.App.model.repository;

import com.example.ktop_food_app.App.model.data.entity.User;
import com.example.ktop_food_app.App.model.data.local.DataGenerator;

import java.util.List;

public class UserRepository {
    public List<User> getUserList() {
        return DataGenerator.generateSampleUsers();
    }
}
