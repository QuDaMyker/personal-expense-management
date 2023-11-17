package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddBinding;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class TransactionAddActivity extends AppCompatActivity {
    private ActivityTransactionAddBinding binding;
    private ActivityResultLauncher<Intent> addWalletLaunch;
    private ActivityResultLauncher<Intent> addAccountLaunch;
    private ActivityResultLauncher<Intent> addCategoryLaunch;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        setListeners();
    }

    private void init() {
        progressDialog = new ProgressDialog(TransactionAddActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        addWalletLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });

        addAccountLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });

        binding.chipThu.setChecked(true);
        binding.clRootThuchi.setVisibility(View.VISIBLE);
        binding.clRootChuyentien.setVisibility(View.GONE);
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.chipThu.setOnClickListener(v -> {
            changeChip("THU");
        });

        binding.chipChi.setOnClickListener(v -> {
            changeChip("CHI");
        });

        binding.chipChuyenTien.setOnClickListener(v -> {
            changeChip("CHUYENTIEN");
        });

        binding.clThuchiVitien.setOnClickListener(v -> {
            Intent intentAddWallet = new Intent(TransactionAddActivity.this, TransactionAddActivity_SelectionWallet.class);
            addWalletLaunch.launch(intentAddWallet);
        });

        binding.clThuchiTaikhoan.setOnClickListener(v -> {
            Intent intentAddAccount = new Intent(TransactionAddActivity.this, TransactionAddActivity_SelectionAccount.class);
            addAccountLaunch.launch(intentAddAccount);
        });


        binding.clDanhmuc.setOnClickListener(v -> {
            //Intent intentAddCategory = new Intent(TransactionAddActivity.this, )
        });

        binding.editTransactionDay.setOnClickListener(v -> {
            openDatePickerDialog("THUCHI");
        });

        binding.editTransactionTime.setOnClickListener(v -> {
            openTimePickerDialog("THUCHI");
        });

        binding.editChuyentienTransactionDay.setOnClickListener(v -> {
            openDatePickerDialog("CHUYENTIEN");
        });

        binding.editChuyentienTransactionTime.setOnClickListener(v -> {
            openTimePickerDialog("CHUYENTIEN");
        });

        binding.btnSubmit.setOnClickListener(v -> {
            progressDialog.show();
            Transaction newTransaction = new Transaction(
                    FirebaseAuth.getInstance().getUid(),
                    "idLater",
                    binding.chipThu.isChecked() ? 0 : 1,
                    Integer.parseInt(binding.editAmount.getText().toString().trim()),
                    binding.editNote.getText().toString().trim(),
                    binding.editTransactionDay.getText().toString().trim(),
                    binding.editTransactionTime.getText().toString().trim(),
                    "Vi tien",
                    "tien mat");
            String res = FireStoreService.addTransaction(newTransaction);
            progressDialog.dismiss();

            finish();
//            if (res == "success") {
//                Toast.makeText(this, "Đã thêm giao dịch", Toast.LENGTH_SHORT).show();
//                finish();
//            } else {
//                Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
//            }
        });

        binding.btnChuyentienSubmit.setOnClickListener(v -> {
            if (binding.chipChuyenTien.isChecked()) {
                progressDialog.show();
                Transaction newTransaction = new Transaction(
                        FirebaseAuth.getInstance().getUid(),
                        "idLater",
                        3,
                        Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()),
                        binding.editChuyentienNote.getText().toString().trim(),
                        binding.editChuyentienTransactionDay.getText().toString().trim(),
                        binding.editChuyentienTransactionTime.getText().toString().trim(),
                        "Tai khoan nguon",
                        "Tai Khoan dich");
                String res = FireStoreService.addTransaction(newTransaction);
                progressDialog.dismiss();
                finish();

//                if (res == "success") {
//                    Toast.makeText(this, "Đã thêm giao dịch", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
//                }
            }

        });


    }

    private void changeChip(String typeChip) {
        if (typeChip == "CHUYENTIEN") {
            binding.clRootChuyentien.setVisibility(View.VISIBLE);
            binding.clRootThuchi.setVisibility(View.GONE);
        } else {
            binding.clRootChuyentien.setVisibility(View.GONE);
            binding.clRootThuchi.setVisibility(View.VISIBLE);
        }
    }

    private void openDatePickerDialog(String typeChip) {
        MaterialDatePicker.Builder builder = MaterialDatePicker
                .Builder
                .datePicker()
                .setTitleText("Chọn ngày thông báo")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).setNegativeButtonText("Hủy").setPositiveButtonText("Chọn");

        MaterialDatePicker datePicker = builder.build();
        datePicker.show(getSupportFragmentManager(), "DatePicker");

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) selection);
                int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int selectedMonth = calendar.get(Calendar.MONTH) + 1;
                int selectedYear = calendar.get(Calendar.YEAR);

                String formattedDate = selectedDayOfMonth + "/" + selectedMonth + "/" + selectedYear;

                if (typeChip == "CHUYENTIEN") {
                    binding.editChuyentienTransactionDay.setText(formattedDate);
                } else {
                    binding.editTransactionDay.setText(formattedDate);
                }
            }
        });
    }

    private void openTimePickerDialog(String typeChip) {
        MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12).setMinute(0).setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK).setTitleText("Chọn thời gian").setNegativeButtonText("Hủy").setPositiveButtonText("Chọn");

        MaterialTimePicker timePicker = builder.build();
        timePicker.show(getSupportFragmentManager(), "timePicker");

        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String time = String.format("%02d:%02d", hour, minute);


                if (typeChip == "CHUYENTIEN") {
                    binding.editChuyentienTransactionTime.setText(time);
                } else {
                    binding.editTransactionTime.setText(time);
                }
            }
        });
    }
}