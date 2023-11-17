package com.learning.personal_expense_management.controller.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.wallet.WalletActivity;
import com.learning.personal_expense_management.controller.home.adapter.home.HomeRecentlyActivityAdapter;
import com.learning.personal_expense_management.controller.home.adapter.home.HomeTargetAdapter;
import com.learning.personal_expense_management.controller.home.adapter.home.ObjectListener;
import com.learning.personal_expense_management.databinding.FragmentHomeBinding;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.TransactionListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private List<Wallet> listTarget;
    private List<Transaction> listRecently;
    private HomeTargetAdapter homeTargetAdapter;
    private HomeRecentlyActivityAdapter homeRecentlyActivityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        init();
        setListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getListRecentlyActivity();
    }

    private void init() {
        listTarget = new ArrayList<>();
        listRecently = new ArrayList<>();
        getListRecentlyActivity();

        homeTargetAdapter = new HomeTargetAdapter(getContext(), listTarget, new ObjectListener() {
            @Override
            public void onClick(Object o) {
                Wallet wallet = (Wallet) o;
            }
        });

    }

    private void setListeners() {
        binding.btnWalletaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getListTarget() {

    }

    private void getListRecentlyActivity() {
        FireStoreService.getTransaction(FirebaseAuth.getInstance().getUid(), new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                listRecently = transactions;

                if (listRecently.isEmpty()) {
                    binding.tvRevHoatdonggandayNull.setVisibility(View.VISIBLE);
                    binding.revHoatdongganday.setVisibility(View.GONE);
                } else {
                    binding.tvRevHoatdonggandayNull.setVisibility(View.GONE);
                    binding.revHoatdongganday.setVisibility(View.VISIBLE);
                }

                homeRecentlyActivityAdapter = new HomeRecentlyActivityAdapter(getContext(), listRecently, new ObjectListener() {
                    @Override
                    public void onClick(Object o) {
                        Transaction transaction = (Transaction) o;
                        Toast.makeText(getContext(), transaction.getId(), Toast.LENGTH_SHORT).show();
                    }
                });


                binding.revHoatdongganday.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.revHoatdongganday.setAdapter(homeRecentlyActivityAdapter);
                homeRecentlyActivityAdapter.notifyDataSetChanged();
            }


            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


}