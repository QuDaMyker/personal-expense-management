package com.learning.personal_expense_management.controller.loan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityAddAccountBinding;
import com.learning.personal_expense_management.databinding.ActivityLoanBinding;


public class LoanActivity extends AppCompatActivity {
    private ActivityLoanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.newLoanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoanActivity.this, NewLoanActivity.class));
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}