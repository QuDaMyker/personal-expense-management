package com.learning.personal_expense_management.controller.loan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityLoanBinding;
import com.learning.personal_expense_management.databinding.ActivityNewLoanBinding;


public class NewLoanActivity extends AppCompatActivity {

    private ActivityNewLoanBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}