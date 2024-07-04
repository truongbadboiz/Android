package com.example.baiitaplonandroid.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baiitaplonandroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
FirebaseAuth mAuth;
public String TAG="uliver";
FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mAuth= FirebaseAuth.getInstance();
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}