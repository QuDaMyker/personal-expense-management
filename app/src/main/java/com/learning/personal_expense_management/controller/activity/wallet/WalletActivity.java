package com.learning.personal_expense_management.controller.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.learning.personal_expense_management.R;

public class WalletActivity extends AppCompatActivity {
    MaterialButton newWalletBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        newWalletBtn = findViewById(R.id.new_loan_btn);
        newWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalletActivity.this, NewWalletActivity.class));
            }
        });
    }
}
