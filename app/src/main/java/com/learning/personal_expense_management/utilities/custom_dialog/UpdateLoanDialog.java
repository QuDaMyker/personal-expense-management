package com.learning.personal_expense_management.utilities.custom_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Loan;

import java.util.List;

public class UpdateLoanDialog extends Dialog implements
        android.view.View.OnClickListener {
    public Activity c;
    public Loan selectedLoan;

    public UpdateLoanDialog(Activity a, Loan selectedLoan) {
        super(a);
        this.c = a;
        this.selectedLoan = selectedLoan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_update_loan);
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
