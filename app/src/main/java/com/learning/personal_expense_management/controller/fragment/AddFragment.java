package com.learning.personal_expense_management.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.transaction.activity.TransactionAddActivity;
import com.learning.personal_expense_management.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        startActivity(new Intent(getContext(), TransactionAddActivity.class));
        return view;
    }
}