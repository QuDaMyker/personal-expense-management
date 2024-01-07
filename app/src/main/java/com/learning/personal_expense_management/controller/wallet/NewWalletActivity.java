package com.learning.personal_expense_management.controller.wallet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityNewWalletBinding;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewWalletActivity extends AppCompatActivity {
    private ActivityNewWalletBinding binding;
    private Wallet inputWallet = new Wallet();
    private List<String> items_period = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.switchWarning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SwitchLineWarning();
            }
        });

        binding.switchPretension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SwitchLinePretension();
            }
        });

        binding.etDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDateSpickerDialog();
            }
        });

        binding.etDeadline.setInputType(InputType.TYPE_CLASS_DATETIME);

        binding.btnNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewWallet();
            }
        });

    }

    private void NewWallet(){
        inputWallet.setOwnerId(FirebaseAuth.getInstance().getUid());
        inputWallet.setWalletName(binding.etNameWallet.getText().toString());
        inputWallet.setSavingsDeadline(binding.etDeadline.getText().toString());
        inputWallet.setFrequency(Integer.parseInt(binding.etPeriod.getText().toString()));

        if(binding.switchWarning.isChecked()){
            inputWallet.setLowBalanceAlert(true);
            inputWallet.setMinimumBalance(Integer.parseInt(binding.etMinimunMoneyWallet.getText().toString()));
        }
        else {
            inputWallet.setLowBalanceAlert(false);
        }

        if(binding.switchPretension.isChecked()){
            inputWallet.setGoalSavingsEnabled(true);
            inputWallet.setGoalAmount(Integer.parseInt(binding.etPretensionMoneyWallet.getText().toString()));
        }
        else{
            inputWallet.setGoalSavingsEnabled(false);
        }

        try {
            String res = FireStoreService.addWallet(inputWallet);
            Toast.makeText(this, "Thêm ví tiền thành công!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WalletActivity.class);
            startActivity(intent);
        }
        catch (Exception e){}

    }

    private void SwitchLineWarning(){
        if (binding.switchWarning.isChecked()) {
            binding.lineWarning.setVisibility(View.VISIBLE);
        }
        else {
            binding.lineWarning.setVisibility(View.GONE);
        }
    }

    private void SwitchLinePretension(){
        if (binding.switchPretension.isChecked()) {
            binding.linePretension.setVisibility(View.VISIBLE);
        }
        else {
            binding.linePretension.setVisibility(View.GONE);
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
                        binding.etDeadline.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
}
