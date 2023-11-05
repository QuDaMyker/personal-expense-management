package com.learning.personal_expense_management.controller.activity.transaction.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;
import com.learning.personal_expense_management.databinding.LayoutDialogDetailTransactionBinding;

public class TransactionFilterActivity extends AppCompatActivity {
    private ActivityTransactionFilterBinding binding;

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
        binding.btnSubmit.setOnClickListener(v -> {
            openDialog(Gravity.CENTER);
        });
    }

    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
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
            Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
        });

        imgBtnDelete.setOnClickListener(v -> {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        });

        btnOK.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();


    }
}