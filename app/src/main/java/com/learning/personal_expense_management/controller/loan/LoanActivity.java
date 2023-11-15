package com.learning.personal_expense_management.controller.loan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.learning.personal_expense_management.R;

public class LoanActivity extends AppCompatActivity {
    MaterialButton newLoanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        newLoanBtn = findViewById(R.id.new_loan_btn);
        newLoanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoanActivity.this, NewLoanActivity.class));
            }
        });
    }
}