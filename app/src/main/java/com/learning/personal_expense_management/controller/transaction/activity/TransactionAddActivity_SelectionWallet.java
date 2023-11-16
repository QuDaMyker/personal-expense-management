package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddSelectionWalletBinding;

public class TransactionAddActivity_SelectionWallet extends AppCompatActivity {
    private ActivityTransactionAddSelectionWalletBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddSelectionWalletBinding.inflate(getLayoutInflater());
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