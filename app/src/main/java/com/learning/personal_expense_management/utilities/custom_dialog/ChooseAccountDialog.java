package com.learning.personal_expense_management.utilities.custom_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.services.AccountListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.utilities.Enum;

import java.util.ArrayList;
import java.util.List;

public class ChooseAccountDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public RecyclerView recyclerView;
    public AccountAdapter accountAdapter;
    public List<Account> accountList = new ArrayList<>();

    public static interface MyDialogListener
    {
        public void userSelectedAValue(Enum.AccountType value);
        public void userCanceled();
    }

    MyDialogListener listener ;
    public MyDialogListener getListener() {
        return listener;
    }

    public void setListener(MyDialogListener listener) {
        this.listener = listener;
    }

    public ChooseAccountDialog(Activity a, List<Account> accounts) {
        super(a);
        this.c = a;
        this.accountList = accounts;
        Log.e("ChooseAccountDialog", "ACCOUNT LENGTH: " + accounts.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_account);
        recyclerView = findViewById(R.id.account_recycler_view);
        accountAdapter = new AccountAdapter(accountList);
        recyclerView.setAdapter(accountAdapter);
        Log.e("onCreate", "ACCOUNT LENGTH: " + accountList.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
        dismiss();
    }
}
