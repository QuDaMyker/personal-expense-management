package com.learning.personal_expense_management.controller.activity.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.learning.personal_expense_management.R;

public class AccountActivity extends AppCompatActivity {

    MaterialButton addAccountBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        addAccountBtn = findViewById(R.id.add_account_btn);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, AddAccountActivity.class));
            }
        });
    }
}