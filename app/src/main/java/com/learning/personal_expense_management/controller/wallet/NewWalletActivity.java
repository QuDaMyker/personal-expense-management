package com.learning.personal_expense_management.controller.wallet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityNewWalletBinding;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewWalletActivity extends AppCompatActivity {
    private ActivityNewWalletBinding binding;
    private Wallet inputWallet = new Wallet();
    private List<String> items_period = new ArrayList<>();
    private Wallet editWallet;
    private boolean isEdit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        isEdit = (boolean) intent.getBooleanExtra("isEdit",false);

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

        binding.etDeadline.setKeyListener(null);

        binding.btnNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInfor()) {
                    NewWallet();
                }
                else{
                    showDialog();
                }
            }
        });

        if(isEdit){
            editWallet = (Wallet) intent.getSerializableExtra("selectedWallet");

            binding.tvTitle.setText("Cập nhật ví tiền");
            binding.btnNewWallet.setText("Cập nhật ví tiền");
            binding.etNameWallet.setText(editWallet.getWalletName());
            binding.etDeadline.setText(editWallet.getSavingsDeadline());

            boolean isGoal = editWallet.isGoalSavingsEnabled();
            binding.switchPretension.setChecked(isGoal);
            if(isGoal){
                binding.etPretensionMoneyWallet.setText(String.valueOf(editWallet.getGoalAmount()));
            }

            boolean isLow = editWallet.isLowBalanceAlert();
            binding.switchWarning.setChecked(isLow);
            if(isLow){
                binding.etMinimunMoneyWallet.setText(String.valueOf(editWallet.getMinimumBalance()));
            }

            if (binding.switchWarning.isChecked()){
                binding.tvRemindWarning.setVisibility(View.GONE);}
            binding.tvRemindDeadline.setVisibility(View.GONE);
            if (binding.switchPretension.isChecked()){
                binding.tvRemindPretension.setVisibility(View.GONE);}
            binding.tvRemindNameWallet.setVisibility(View.GONE);
        }

        binding.etNameWallet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()){
                    binding.tvRemindNameWallet.setVisibility(View.GONE);
                }
                else {
                    binding.tvRemindNameWallet.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.etMinimunMoneyWallet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                String stringEditable = editable.toString();
                if (stringEditable.length()<5){
                    binding.tvRemindWarning.setVisibility(View.VISIBLE);
                    if(stringEditable.length()!=0){
                        binding.tvRemindWarning.setText("Vui lòng nhập tối thiểu 10 000 đ");
                    }
                }
                else {
                    binding.tvRemindWarning.setVisibility(View.GONE);
                }
            }
        });

        binding.etPretensionMoneyWallet.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }
           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }
           @Override
           public void afterTextChanged(Editable editable) {
               String stringEditable = editable.toString();
               if (stringEditable.length()<6){
                   binding.tvRemindPretension.setVisibility(View.VISIBLE);
                   binding.tvRemindPretension.setText("Vui lòng nhập tối thiểu 100 000 đ");
               }
               else {
                   binding.tvRemindPretension.setVisibility(View.GONE);
               }
           }
        });

        binding.etDeadline.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()){
                    binding.tvRemindDeadline.setVisibility(View.GONE);
                }
                else {
                    binding.tvRemindDeadline.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean checkInfor(){
        boolean flag = true;
        if(binding.tvRemindNameWallet.getVisibility() == View.GONE){
            if(binding.switchWarning.isChecked()){
                if(binding.tvRemindWarning.getVisibility() != View.GONE){
                    flag = false;
                }
            }
            if(binding.switchPretension.isChecked()){
                if(binding.tvRemindPretension.getVisibility() == View.GONE && binding.tvRemindDeadline.getVisibility() == View.GONE){

                }
                else{
                    flag = false;
                }
            }
        }
        else {
            flag = false;
        }
        return flag;
    }


    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập đầy đủ và hợp lệ tất cả các thông tin!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void GetInputWallet(){
        inputWallet.setOwnerId(FirebaseAuth.getInstance().getUid());
        inputWallet.setWalletName(binding.etNameWallet.getText().toString());
        inputWallet.setSavingsDeadline(binding.etDeadline.getText().toString());
        inputWallet.setFrequency(0);

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
    }
    private void NewWallet(){
        GetInputWallet();
        try {
            String res = FireStoreService.addWallet(inputWallet);
            Toast.makeText(this, "Thêm ví tiền thành công!",Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(this, WalletDetailActivity.class);
            intent.putExtra("selectedWallet",(Serializable) inputWallet);
            startActivity(intent);
        }
        catch (Exception e){}

    }

    private void EditWallet(){
        GetInputWallet();
        try {
            String res = FireStoreService.editWallet(inputWallet);
            Toast.makeText(this, "Cập nhật ví tiền thành công!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WalletDetailActivity.class);
            intent.putExtra("selectedWallet",(Serializable) inputWallet);
            startActivity(intent);
        }
        catch (Exception e){}
    }

    private void SwitchLineWarning(){
        if (binding.switchWarning.isChecked()) {
            binding.lineWarningWallet.setVisibility(View.VISIBLE);
        }
        else {
            binding.lineWarningWallet.setVisibility(View.GONE);
        }
    }

    private void SwitchLinePretension(){
        if (binding.switchPretension.isChecked()) {
            binding.linePretensionWallet.setVisibility(View.VISIBLE);
        }
        else {
            binding.linePretensionWallet.setVisibility(View.GONE);
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

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
}
