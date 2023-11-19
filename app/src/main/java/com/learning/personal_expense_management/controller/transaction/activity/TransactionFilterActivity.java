package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;
import com.learning.personal_expense_management.databinding.LayoutDialogDetailTransactionBinding;

import java.util.List;

public class TransactionFilterActivity extends AppCompatActivity {
    private ActivityTransactionFilterBinding binding;
    private String resultTransactionType = "NULL";
    private String resultFilter = "NULL";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }

    private void init() {

    }

    private void setListeners() {

        binding.chipThu.setOnClickListener(v -> {
            resultTransactionType = "THU";
            clearTransactionTypeChecked();
            binding.chipThu.setChecked(true);
        });

        binding.chipChi.setOnClickListener(v -> {
            resultTransactionType = "CHI";
            clearTransactionTypeChecked();
            binding.chipChi.setChecked(true);
        });

        binding.chipChuyentien.setOnClickListener(v -> {
            resultTransactionType = "CHUYENTIEN";
            clearTransactionTypeChecked();
            binding.chipChuyentien.setChecked(true);
        });

        binding.chipCaonhat.setOnClickListener(v -> {
            resultFilter = "CAONHAT";
            ClearSortChecked();
            binding.chipCaonhat.setChecked(true);
        });

        binding.chipThapnhat.setOnClickListener(v -> {
            resultFilter = "THAPNHAT";
            ClearSortChecked();
            binding.chipThapnhat.setChecked(true);
        });

        binding.chipMoinhat.setOnClickListener(v -> {
            resultFilter = "MOINHAT";
            ClearSortChecked();
            binding.chipMoinhat.setChecked(true);
        });

        binding.chipCunhat.setOnClickListener(v -> {
            resultFilter = "CUNHAT";
            ClearSortChecked();
            binding.chipCunhat.setChecked(true);
        });

        binding.btnDeleteFilter.setOnClickListener(v -> {
            resultFilter = "NULL";
            resultTransactionType = "NULL";
            clearTransactionTypeChecked();
            ClearSortChecked();
        });

        binding.btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RootActivity.class);
            intent.putExtra("resultFilter", resultFilter);
            intent.putExtra("resultTransactionType", resultTransactionType);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void clearTransactionTypeChecked() {
        binding.chipThu.setChecked(false);
        binding.chipChi.setChecked(false);
        binding.chipChuyentien.setChecked(false);

    }

    private void ClearSortChecked() {
        binding.chipCaonhat.setChecked(false);
        binding.chipThapnhat.setChecked(false);
        binding.chipMoinhat.setChecked(false);
        binding.chipCunhat.setChecked(false);
    }


}