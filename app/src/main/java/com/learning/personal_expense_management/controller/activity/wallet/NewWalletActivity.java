package com.learning.personal_expense_management.controller.activity.wallet;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.learning.personal_expense_management.R;
public class NewWalletActivity extends AppCompatActivity {

    Switch switchWarning;
    Switch switchPretension;
    LinearLayout lineWarning;
    LinearLayout linePretension;
    TextInputEditText editTextDeadline;
    TextInputEditText editTextNameWallet;
//    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wallet);
//        context = getApplicationContext();

        lineWarning = findViewById(R.id.line_warning);
        switchWarning = findViewById(R.id.switch_warning);
        switchWarning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchWarning.isChecked()) {
                    lineWarning.setVisibility(View.VISIBLE);
                }
                else {
                    lineWarning.setVisibility(View.GONE);
                }
            }
        });

        linePretension = findViewById(R.id.line_pretension);
        switchPretension = findViewById(R.id.switch_pretension);
        switchPretension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchPretension.isChecked()) {
                    linePretension.setVisibility(View.VISIBLE);
                }
                else {
                    linePretension.setVisibility(View.GONE);
                }
            }
        });

    }
}
