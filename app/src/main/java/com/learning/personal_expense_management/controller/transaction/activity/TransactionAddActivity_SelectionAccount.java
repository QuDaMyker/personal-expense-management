package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.controller.transaction.adapter.ChoseAccountAdapter;
import com.learning.personal_expense_management.controller.transaction.adapter.ObjectListener;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddSelectionAccountBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.services.AccountListener;
import com.learning.personal_expense_management.services.FireStoreService;

import java.util.ArrayList;
import java.util.List;

public class TransactionAddActivity_SelectionAccount extends AppCompatActivity {
    private ActivityTransactionAddSelectionAccountBinding binding;
    private List<Account> listAccount;
    private ChoseAccountAdapter choseAccountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddSelectionAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
        setFunction();
    }

    private void init() {
        listAccount = new ArrayList<>();
        choseAccountAdapter = new ChoseAccountAdapter(getApplicationContext(), listAccount, new ObjectListener() {
            @Override
            public void onClick(Object o) {
                Account account = (Account) o;
                Intent intent = new Intent(TransactionAddActivity_SelectionAccount.this, RootActivity.class);
                intent.putExtra("ownerId", account.getOwnerId());
                intent.putExtra("id", account.getId());
                intent.putExtra("accountType", account.getAccountType());
                intent.putExtra("cardName", account.getCardName());
                intent.putExtra("cardNumber", account.getCardNumber());
                intent.putExtra("expirationDate", account.getExpirationDate());
                intent.putExtra("currentBalance", account.getCurrentBalance());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        binding.rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rcv.setAdapter(choseAccountAdapter);
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v-> {
            finish();
        });
    }

    private void setFunction(){
        fetchListAccount(FirebaseAuth.getInstance().getUid());
    }

    private void fetchListAccount(String uid) {
        FireStoreService.getAccount(uid, new AccountListener() {
            @Override
            public void onAccountsLoaded(List<Account> accounts) {
                listAccount.clear();
                listAccount = accounts;

                choseAccountAdapter.setList(listAccount);
                choseAccountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                listAccount.clear();
            }
        });
    }

}