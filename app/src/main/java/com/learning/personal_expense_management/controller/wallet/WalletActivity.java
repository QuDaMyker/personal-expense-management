package com.learning.personal_expense_management.controller.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.controller.login.LoginActivity;
import com.learning.personal_expense_management.controller.wallet.adapter.OnItemClickListener;
import com.learning.personal_expense_management.controller.wallet.adapter.WalletAdapter;
import com.learning.personal_expense_management.databinding.ActivityWalletBinding;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.WalletListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityWalletBinding binding;
    private Wallet temp_wallet = new Wallet("bDkadbr5yzTe208V8CYq5Ifk98q1", "tF0hdXd6aztDgski0wjU", "Dulich", false, 0, false, 0, "2024-4-4", 1);
    private List<Wallet> walletList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        walletList = new ArrayList<>();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.newWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, NewWalletActivity.class);
                intent.putExtra("isEdit",false);
                startActivity(intent);
            }
        });
        FireStoreService.getWallet(FirebaseAuth.getInstance().getUid(), new WalletListener() {
            @Override
            public void onWalletsLoaded(List<Wallet> wallets) {
                walletList=wallets;
                binding.rvListWallet.setLayoutManager(new GridLayoutManager(binding.rvListWallet.getContext(), 2));
                WalletAdapter walletAdapter = new WalletAdapter(wallets, (OnItemClickListener) binding.rvListWallet.getContext());
                walletAdapter.notifyDataSetChanged();
                binding.rvListWallet.setAdapter(walletAdapter);
            }
            @Override
            public void onError(String errorMessage) {
                Log.e("VIEWHOLDER", "JOIN");
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Wallet selectedWallet = walletList.get(position);
        Intent intent = new Intent(WalletActivity.this, WalletDetailActivity.class);
        intent.putExtra("selectedWallet",(Serializable) selectedWallet);
        startActivity(intent);
    }

}
