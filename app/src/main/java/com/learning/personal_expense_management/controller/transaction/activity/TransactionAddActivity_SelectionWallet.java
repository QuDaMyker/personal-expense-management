package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.controller.transaction.adapter.ChoseWalletAdapter;
import com.learning.personal_expense_management.controller.transaction.adapter.ObjectListener;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddSelectionWalletBinding;
import com.learning.personal_expense_management.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class TransactionAddActivity_SelectionWallet extends AppCompatActivity {
    private ActivityTransactionAddSelectionWalletBinding binding;
    private List<Wallet> listWallet;
    private ChoseWalletAdapter choseWalletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddSelectionWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        setListeners();
        setFunction();
    }

    private void init() {
        listWallet = new ArrayList<>();
        choseWalletAdapter = new ChoseWalletAdapter(getApplicationContext(), listWallet, new ObjectListener() {
            @Override
            public void onClick(Object o) {
                Wallet wallet = (Wallet) o;
                Intent intent = new Intent(TransactionAddActivity_SelectionWallet.this, RootActivity.class);
                intent.putExtra("ownerId", wallet.getOwnerId());
                intent.putExtra("id", wallet.getId());
                intent.putExtra("walletName", wallet.getWalletName());
                intent.putExtra("lowBalanceAlert", wallet.isLowBalanceAlert());
                intent.putExtra("minimumBalance", wallet.getMinimumBalance());
                intent.putExtra("goalSavingsEnabled", wallet.isGoalSavingsEnabled());
                intent.putExtra("goalAmount", wallet.getGoalAmount());
                intent.putExtra("savingsDeadline", wallet.getSavingsDeadline());
                intent.putExtra("frequency", wallet.getFrequency());
                intent.putExtra("currentMoney", wallet.getCurrentMoney());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        binding.gridView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        binding.gridView.setAdapter(choseWalletAdapter);
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v-> {
            finish();
        });
    }
    private void setFunction() {
        fetchListWallets(FirebaseAuth.getInstance().getUid());
    }

    private void fetchListWallets(String uid) {

    }
}