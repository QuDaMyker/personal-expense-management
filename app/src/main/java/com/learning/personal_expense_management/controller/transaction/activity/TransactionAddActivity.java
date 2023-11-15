package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddBinding;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;

public class TransactionAddActivity extends AppCompatActivity {
    private ActivityTransactionAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }

    private void init() {

    }

    private void setListeners() {

    }
}