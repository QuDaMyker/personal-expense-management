package com.learning.personal_expense_management.utilities.custom_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.services.FireStoreService;

import java.util.List;

public class UpdateLoanDialog extends Dialog implements
        android.view.View.OnClickListener {
    public Activity c;
    public Loan selectedLoan;
    public TextInputEditText paidEdt;
    public Button btnOk;
    public CheckBox paidAllCb;

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

        paidEdt = findViewById(R.id.paidEdt);
        btnOk = findViewById(R.id.btn_ok);
        paidAllCb = findViewById(R.id.paidAllCb);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireStoreService.paidLoan(selectedLoan.getId(), Long.parseLong(paidEdt.getText().toString()), paidAllCb.isChecked());
                Toast.makeText(c, "Cập nhật trả tiền khoản vay thành công", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        paidAllCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paidEdt.setEnabled(false);
                }
                else{
                    paidEdt.setEnabled(true);
                }
            }
        });
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
