package com.learning.personal_expense_management.controller.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.learning.personal_expense_management.R;

import com.learning.personal_expense_management.databinding.ActivityWalletDetailBinding;
import com.learning.personal_expense_management.model.Wallet;

public class WalletDetailActivity extends AppCompatActivity {
    private ActivityWalletDetailBinding binding;
    private Wallet selectedWalletd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Wallet selectedWallet = (Wallet) intent.getSerializableExtra("selectedWallet");

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvNameWallet.setText(selectedWallet.getWalletName());
        binding.tvMoneyWallet.setText(String.valueOf(selectedWallet.getCurrentMoney())+" vnđ");

        int goalAmount = selectedWallet.getGoalAmount();
        if(goalAmount>0){
            binding.tvPretensionWallet.setText(String.valueOf(goalAmount)+" vnđ");
            //binding.tvSavingPerMonth.setText((String.valueOf(goalAmount/)));
        }
        else{
            binding.tvPretensionWallet.setVisibility(View.GONE);
            binding.tvSavingPerMonth.setVisibility(View.GONE);
        }

        binding.btnEditWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnTransactionWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnDeleteWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
