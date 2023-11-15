package com.learning.personal_expense_management.controller.transaction.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.transaction.activity.TransactionAddActivity_SelectionWallet;
import com.learning.personal_expense_management.controller.transaction.activity.TransactionFilterActivity;
import com.learning.personal_expense_management.databinding.FragmentTransactionBinding;

public class TransactionFragment extends Fragment {
    private FragmentTransactionBinding binding;
    private int currentYear;
    private int currentMonth;
    private ActivityResultLauncher<Intent> launcherFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_transaction, container, false);
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        init();
        setListeners();


        return view;
    }

    public void init() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        setYear(currentYear);
        setMonth(currentMonth);

        launcherFilter = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String resultFilter = intent.getStringExtra("resultFilter");
                            Toast.makeText(getContext(), resultFilter, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void setListeners() {
        binding.imgFilter.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TransactionAddActivity_SelectionWallet.class);
            launcherFilter.launch(new Intent(getContext(), TransactionFilterActivity.class));
        });

        binding.imgPreYear.setOnClickListener(v -> {
            int showYear = Integer.parseInt(binding.tvYear.getText().toString());
            setYear(--showYear);
        });

        binding.imgNextYear.setOnClickListener(v -> {
            int showYear = Integer.parseInt(binding.tvYear.getText().toString());
            setYear(++showYear);
        });

        binding.imgPreMonth.setOnClickListener(v -> {
            String s = binding.tvMonth.getText().toString();
            String ss = s.replaceAll("\\D+", "");
            Log.d("m", ss);

            try {
                int showMonth = Integer.parseInt(ss);
                showMonth--;

                if (showMonth < 1) {
                    showMonth = 12;
                }

                setMonth(showMonth);
            } catch (NumberFormatException e) {

            }
        });

        binding.imgNextMonth.setOnClickListener(v -> {
            String s = binding.tvMonth.getText().toString();
            String ss = s.replaceAll("\\D+", "");

            try {
                int showMonth = Integer.parseInt(ss);
                showMonth++;

                if (showMonth > 12) {
                    showMonth = 1;
                }

                setMonth(showMonth);
            } catch (NumberFormatException e) {

            }
        });
    }

    private void setYear(int year) {
        binding.tvYear.setText(String.valueOf(year));
    }

    private void setMonth(int month) {
        int previousMonth = month - 1;
        int nextMonth = month + 1;

        if (previousMonth < 1) {
            previousMonth = 12;
        }

        if (nextMonth > 12) {
            nextMonth = 1;
        }

        binding.tvMonthPre.setText("Tháng " + previousMonth);
        binding.tvMonth.setText("Tháng " + month);
        binding.tvMonthNext.setText("Tháng " + nextMonth);
    }

    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_detail_transaction);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


//        LayoutDialogDetailTransactionBinding dialogBinding = LayoutDialogDetailTransactionBinding.inflate(getLayoutInflater());
//
//        dialogBinding.imgEdit.setOnClickListener(v -> {
//            Toast.makeText(this, "Img Edit", Toast.LENGTH_SHORT).show();
//        });
//
//        dialogBinding.imgDelete.setOnClickListener(v-> {
//            Toast.makeText(this, "Img Delete", Toast.LENGTH_SHORT).show();
//        });
//
//        dialogBinding.btnOk.setOnClickListener(v -> {
//            dialog.dismiss();
//        });

        ImageButton imgBtnEdit = dialog.findViewById(R.id.imgBtn_edit);
        ImageButton imgBtnDelete = dialog.findViewById(R.id.imgBtn_delete);
        Button btnOK = dialog.findViewById(R.id.btn_ok);

        imgBtnEdit.setOnClickListener(v -> {
            Toast.makeText(getContext(), "edit", Toast.LENGTH_SHORT).show();
        });

        imgBtnDelete.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
        });

        btnOK.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    String getData() {
        String result = "Something when wrong";

        return result;
    }

}