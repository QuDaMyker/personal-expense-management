package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.type.DateTime;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddBinding;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class TransactionAddActivity extends AppCompatActivity {
    private ActivityTransactionAddBinding binding;
    private ActivityResultLauncher<Intent> addWalletLaunch;
    private ActivityResultLauncher<Intent> addAccountLaunch;
    private ActivityResultLauncher<Intent> addCategoryLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        setListeners();
    }

    private void init() {
        addWalletLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                }
        );

        addAccountLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                }
        );
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.clVitien.setOnClickListener(v-> {
            Intent intentAddWallet = new Intent(TransactionAddActivity.this, TransactionAddActivity_SelectionWallet.class);
            addWalletLaunch.launch(intentAddWallet);
        });

        binding.clTaikhoan.setOnClickListener(v-> {
            Intent intentAddAccount = new Intent(TransactionAddActivity.this, TransactionAddActivity_SelectionAccount.class);
            addAccountLaunch.launch(intentAddAccount);
        });


        binding.clDanhmuc.setOnClickListener(v-> {
            //Intent intentAddCategory = new Intent(TransactionAddActivity.this, )
        });

        binding.btnTransactionDay.setOnClickListener(v -> {
            openDatePickerDialog();
        });

        binding.btnTransactionTime.setOnClickListener(v-> {
            openTimePickerDialog();
        });

    }

    private void openDatePickerDialog() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(TransactionAddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.editTransactionDay.setText(dayOfMonth + " - " + month + " - " + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void openTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(TransactionAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                binding.editTransactionTime.setText(minute + " : " + hourOfDay);
            }
        }, 00, 00, true);

        timePickerDialog.show();
    }
}