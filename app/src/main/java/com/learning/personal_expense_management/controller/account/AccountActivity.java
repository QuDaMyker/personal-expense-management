package com.learning.personal_expense_management.controller.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityAccountBinding;
import com.learning.personal_expense_management.databinding.ActivityLoginBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.services.AccountListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.utilities.Enum;
import com.learning.personal_expense_management.utilities.custom_dialog.ChooseAccountDialog;
import com.learning.personal_expense_management.utilities.custom_dialog.ChooseAccountTypeDialog;

import java.util.List;


public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, AddAccountActivity.class));
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.accountPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireStoreService.getAccount(FirebaseAuth.getInstance().getUid(), new AccountListener() {
                    @Override
                    public void onAccountsLoaded(List<Account> accounts) {
                        ChooseAccountDialog cdd = new ChooseAccountDialog(AccountActivity.this, accounts);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();
                        cdd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        cdd.getWindow().setGravity(Gravity.CENTER);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });

            }
        });
    }
}