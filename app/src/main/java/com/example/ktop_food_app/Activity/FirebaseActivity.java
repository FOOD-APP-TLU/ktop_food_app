package com.example.ktop_food_app.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.ktop_food_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}
