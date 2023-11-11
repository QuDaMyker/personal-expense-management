package com.learning.personal_expense_management.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.activity.wallet.WalletActivity;

public class HomeFragment extends Fragment {

    Button btnWalletAccount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnWalletAccount= view.findViewById(R.id.btn_walletaccount);
        btnWalletAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}