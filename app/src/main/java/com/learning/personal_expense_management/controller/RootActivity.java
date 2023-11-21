package com.learning.personal_expense_management.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.fragment.AddFragment;
import com.learning.personal_expense_management.controller.home.fragment.HomeFragment;
import com.learning.personal_expense_management.controller.fragment.ProfileFragment;
import com.learning.personal_expense_management.controller.fragment.StatisticFragment;
import com.learning.personal_expense_management.controller.transaction.activity.TransactionAddActivity;
import com.learning.personal_expense_management.controller.transaction.fragment.TransactionFragment;
import com.learning.personal_expense_management.databinding.ActivityRootBinding;

public class RootActivity extends AppCompatActivity implements DialogListener{
    private ActivityRootBinding binding;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRootBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    @SuppressLint("NonConstantResourceId")
    private void init() {
        progressDialog = new ProgressDialog(RootActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        setDefaultFragment();
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.btn_home: {
                    replaceFragment(new HomeFragment());
                    break;
                }
                case R.id.btn_transaction: {
                    replaceFragment(new TransactionFragment());
                    break;
                }
                case R.id.btn_add: {
                    //replaceFragment(new AddFragment());
                    startActivity(new Intent(RootActivity.this, TransactionAddActivity.class));
                    break;
                }
                case R.id.btn_statistic: {
                    replaceFragment(new StatisticFragment());
                    break;
                }
                case R.id.btn_personal: {
                    replaceFragment(new ProfileFragment());
                    break;
                }
            }
            return true;
        });

    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }
}