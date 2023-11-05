package com.learning.personal_expense_management.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.fragment.AddFragment;
import com.learning.personal_expense_management.controller.fragment.HomeFragment;
import com.learning.personal_expense_management.controller.fragment.PersonalFragment;
import com.learning.personal_expense_management.controller.fragment.StatisticFragment;
import com.learning.personal_expense_management.controller.activity.transaction.fragment.TransactionFragment;
import com.learning.personal_expense_management.databinding.ActivityRootBinding;

public class RootActivity extends AppCompatActivity {
    private ActivityRootBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRootBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    @SuppressLint("NonConstantResourceId")
    private void init() {
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
                    replaceFragment(new AddFragment());
                    break;
                }
                case R.id.btn_statistic: {
                    replaceFragment(new StatisticFragment());
                    break;
                }
                case R.id.btn_personal:{
                    replaceFragment(new PersonalFragment());
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
}