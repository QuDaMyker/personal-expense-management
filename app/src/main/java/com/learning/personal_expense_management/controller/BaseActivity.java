package com.learning.personal_expense_management.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.learning.personal_expense_management.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}