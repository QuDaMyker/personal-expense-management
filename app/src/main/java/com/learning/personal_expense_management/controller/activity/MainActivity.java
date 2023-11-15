package com.learning.personal_expense_management.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.fragment.NewCategoryFragment;
import com.learning.personal_expense_management.controller.fragment.StatisticFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    NewCategoryFragment fragment = new NewCategoryFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();

    }


}