package com.example.ktop_food_app.App.model.Repository;

import com.example.ktop_food_app.App.model.Data.Database.DataGenerator;
import com.example.ktop_food_app.App.model.Entity.User;

import java.util.List;

public class UserRepository {
    public List<User> getUserList() {
        return DataGenerator.generateSampleUsers();
    }
}
