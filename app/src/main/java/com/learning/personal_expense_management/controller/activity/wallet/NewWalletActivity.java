package com.learning.personal_expense_management.controller.activity.wallet;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.learning.personal_expense_management.R;

import java.util.Calendar;

public class NewWalletActivity extends AppCompatActivity {

    MaterialSwitch switchWarning;
    MaterialSwitch switchPretension;
    LinearLayout lineWarning;
    LinearLayout linePretension;
    ImageButton btnBack;
    TextInputEditText editTextDeadline;
    MaterialButton btnNewWallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wallet);

        btnBack = findViewById(R.id.btn_Back);
        lineWarning = findViewById(R.id.line_warning);
        linePretension = findViewById(R.id.line_pretension);
        switchWarning = findViewById(R.id.switch_warning);
        switchPretension = findViewById(R.id.switch_pretension);
        editTextDeadline = findViewById(R.id.et_deadline);
        btnNewWallet = findViewById(R.id.btn_new_wallet);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        switchWarning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SwitchLineWarning();
            }
        });

        switchPretension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SwitchLinePretension();
            }
        });

        editTextDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDateSpickerDialog();
            }
        });
        editTextDeadline.setInputType(InputType.TYPE_NULL);
    }

    private void SwitchLineWarning(){
        if (switchWarning.isChecked()) {
            lineWarning.setVisibility(View.VISIBLE);
        }
        else {
            lineWarning.setVisibility(View.GONE);
        }
    }

    private void SwitchLinePretension(){
        if (switchPretension.isChecked()) {
            linePretension.setVisibility(View.VISIBLE);
        }
        else {
            linePretension.setVisibility(View.GONE);
        }
    }

    private void ShowDateSpickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, R.style.Theme_DatePickerDialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editTextDeadline.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
}
