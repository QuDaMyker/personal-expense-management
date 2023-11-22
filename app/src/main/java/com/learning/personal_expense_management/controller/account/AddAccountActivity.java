package com.learning.personal_expense_management.controller.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityAddAccountBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.utilities.CustomDialog;
import com.learning.personal_expense_management.utilities.Enum;

import java.util.Calendar;


public class AddAccountActivity extends AppCompatActivity {

    private ActivityAddAccountBinding binding;
    private Account inputAccount = new Account();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.accountTypePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog cdd = new CustomDialog(AddAccountActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.setListener(new CustomDialog.MyDialogListener() {
                    @Override
                    public void userSelectedAValue(Enum.AccountType value) {
                        binding.accountTypeTv.setText(value.AccountTypeName());
                        inputAccount.setAccountType(value.AccountTypeName());

                        switch (value) {
                            case Cash:
                                binding.logo.setImageResource(R.drawable.ic_money_2);
                                setLayoutVisibility(false);
                                break;
                            case Visa:
                                binding.logo.setImageResource(R.drawable.visa_logo);
                                setLayoutVisibility(true);
                                break;
                            case Mastercard:
                                binding.logo.setImageResource(R.drawable.mastercard_logo);
                                setLayoutVisibility(true);
                                break;
                            case JCB:
                                binding.logo.setImageResource(R.drawable.jcb_logo);
                                setLayoutVisibility(true);
                                break;
                            case MoMo:
                                binding.logo.setImageResource(R.drawable.momo_logo);
                                setLayoutVisibility(false);
                                break;
                            case ShopeePay:
                                binding.logo.setImageResource(R.drawable.shopeepay_logo);
                                setLayoutVisibility(false);
                                break;
                            case ZaloPay:
                                binding.logo.setImageResource(R.drawable.zalopay_logo);
                                setLayoutVisibility(false);
                                break;
                            case EWallet:
                                binding.logo.setImageResource(R.drawable.ic_ewallet);
                                setLayoutVisibility(false);
                                break;
                            case ATM:
                                binding.logo.setImageResource(R.drawable.ic_credit_card);
                                setLayoutVisibility(true);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void userCanceled() {

                    }
                });
                cdd.show();
                cdd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cdd.getWindow().setGravity(Gravity.CENTER);
            }
        });

        binding.balanceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tvBalance.setText(editable.toString() + "₫");
                binding.tvBalanceWallet.setText(editable.toString() + "₫");
            }
        });

        binding.expiryDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Chọn ngày hết hạn")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setNegativeButtonText("Hủy")
                        .setPositiveButtonText("Chọn");

                MaterialDatePicker datePicker = builder.build();
                datePicker.show(getSupportFragmentManager(), "DatePicker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((Long) selection);
                        int selectedMonth = calendar.get(Calendar.MONTH) + 1;
                        int selectedYear = calendar.get(Calendar.YEAR);

                        String formattedDate = selectedMonth + "/" + Integer.toString(selectedYear).substring(2, 4);
                        binding.expiryDateEdt.setText(formattedDate);
                        binding.tvExpiryDate.setText(formattedDate);
                    }
                });
            }
        });

        binding.accountIdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tvAccountId.setText(editable.toString());
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void setLayoutVisibility(boolean isCard){
        if(isCard){
            binding.cardLayout.setVisibility(View.VISIBLE);
            binding.walletLayout.setVisibility(View.GONE);
            binding.cardFormSection.setVisibility(View.VISIBLE);
        }
        else{
            binding.cardLayout.setVisibility(View.GONE);
            binding.walletLayout.setVisibility(View.VISIBLE);
            binding.cardFormSection.setVisibility(View.GONE);
        }
    }
}