package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddSelectionAccountBinding;

public class TransactionAddActivity_SelectionAccount extends AppCompatActivity {
    private ActivityTransactionAddSelectionAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddSelectionAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }

    private void init() {

    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v-> {
            finish();
        });
    }

}