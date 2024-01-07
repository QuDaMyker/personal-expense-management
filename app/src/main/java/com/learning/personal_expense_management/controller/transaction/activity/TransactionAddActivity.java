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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.RootActivity;
import com.learning.personal_expense_management.controller.category.CategoriesActivity;
import com.learning.personal_expense_management.controller.transaction.adapter.ChoseCategoryAdapter;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddBinding;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.FirestoreCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TransactionAddActivity extends AppCompatActivity {
    private ActivityTransactionAddBinding binding;
    private ActivityResultLauncher<Intent> addWalletLaunch;
    private ActivityResultLauncher<Intent> addAccountLaunch;
    private ActivityResultLauncher<Intent> addCategoryLaunch;
    private ProgressDialog progressDialog;
    private Transaction transactionEdit;
    private Boolean isEdit = false;
    private Category currentCategory;

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


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("transactionEdit")) {
            transactionEdit = (Transaction) intent.getSerializableExtra("transactionEdit");
            isEdit = true;
        }
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

        addCategoryLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromChoseCategory = result.getData();
                    String ownerId = intentFromChoseCategory.getStringExtra("ownerIdCategory");
                    String id = intentFromChoseCategory.getStringExtra("idCategory");
                    String name = intentFromChoseCategory.getStringExtra("nameCategory");
                    int background = intentFromChoseCategory.getIntExtra("backgroundCategory", 2131034178);
                    int icon = intentFromChoseCategory.getIntExtra("iconCategory", 2131165746);
                    int colorIcon = intentFromChoseCategory.getIntExtra("colorIconCategory", 2131034237);
                    int isInCome = intentFromChoseCategory.getIntExtra("isInComeCategory", 0);


                    currentCategory = new Category(ownerId, id, name, background, icon, colorIcon, isInCome);
                    binding.imgDanhmuc.setImageResource(icon);
                    binding.titleDanhmuc.setText(name);
                    binding.subTitleDanhmuc.setVisibility(View.INVISIBLE);

//                    binding.icon.setImageResource(category.getIcon());
//                    int colorIcon = context.getResources().getColor(category.getColorIcon());
//                    binding.icon.setColorFilter(colorIcon);
                }
            }
        });

        setLayoutChip(isEdit);
    }

    private void setLayoutChip(Boolean isEdit) {
        if (isEdit) {
            switch (transactionEdit.getTransactionType()) {
                case 0: {
                    binding.chipThu.setChecked(true);
                    setLayoutCLNOTChuyenTien(transactionEdit);
                    break;
                }
                case 1: {
                    binding.chipChi.setChecked(true);
                    setLayoutCLNOTChuyenTien(transactionEdit);
                    break;
                }
                case 2: {
                    binding.chipChuyenTien.setChecked(true);
                    setLayoutCLChuyenTien(transactionEdit);
                    break;
                }
                default: {
                    binding.chipThu.setChecked(true);
                    setLayoutCLNOTChuyenTien(transactionEdit);
                }
            }
        } else {
            binding.chipThu.setChecked(true);
            binding.clRootThuchi.setVisibility(View.VISIBLE);
            binding.clRootChuyentien.setVisibility(View.GONE);
        }
    }

    private void setLayoutCLNOTChuyenTien(Transaction transaction) {
        binding.editAmount.setText(transaction.getAmount());
        binding.editNote.setText(transaction.getNote());
        binding.editChuyentienTransactionDay.setText(transaction.getTransactionDate());
        binding.editChuyentienTransactionTime.setText(transaction.getTransactionTime());
        binding.btnSubmit.setText("Lưu");
    }

    private void setLayoutCLChuyenTien(Transaction transaction) {
        binding.editChuyentienAmount.setText(transaction.getAmount());
        binding.editChuyentienNote.setText(transaction.getNote());
        binding.editChuyentienTransactionDay.setText(transaction.getTransactionDate());
        binding.editChuyentienTransactionTime.setText(transaction.getTransactionTime());
        binding.btnChuyentienSubmit.setText("Lưu");
    }

    private void setListeners() {
        binding.switchFuture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                binding.editTransactionDay.setEnabled(!isChecked);
//                binding.editTransactionTime.setEnabled(!isChecked);
                if (isChecked) {
                    binding.clTransactionDay.setVisibility(View.GONE);
                    binding.clTransactionTime.setVisibility(View.GONE);
                } else {
                    binding.clTransactionDay.setVisibility(View.VISIBLE);
                    binding.clTransactionTime.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.switchChuyentienFuture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.clChuyentienTransactionDay.setVisibility(View.GONE);
                    binding.clChuyentienTransactionTime.setVisibility(View.GONE);
                } else {
                    binding.clChuyentienTransactionDay.setVisibility(View.VISIBLE);
                    binding.clChuyentienTransactionTime.setVisibility(View.VISIBLE);
                }
            }
        });
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
            Intent intentAddCategory = new Intent(TransactionAddActivity.this, ChoseCategoryActivity.class);
            addCategoryLaunch.launch(intentAddCategory);
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
            if (isNotEmptyInput()) {
                progressDialog.show();
                try {
                    String dateStr = binding.editTransactionDay.getText().toString().trim();
                    String timeStr = binding.editTransactionTime.getText().toString().trim();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

                    Date date = dateFormat.parse(dateStr + " " + timeStr);
                    if (date != null) {
                        Timestamp timestamp = new Timestamp(date);
                        Transaction newTransaction = new Transaction(
                                FirebaseAuth.getInstance().getUid(),
                                "idLater",
                                binding.chipThu.isChecked() ? 0 : 1,
                                Integer.parseInt(binding.editAmount.getText().toString().trim()),
                                binding.editNote.getText().toString().trim(),
                                dateStr,
                                timeStr,
                                "Vi tien",
                                "tien mat",
                                currentCategory.getId(),
                                new com.google.firebase.Timestamp(date),
                                false
                        );
                        // String res = FireStoreService.addTransaction(newTransaction);
                        FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                            @Override
                            public void onCallback(String result) {
                                if (result.equals("success")) {
                                    finish();
                                }
                            }
                        });
                    } else {

                    }
                } catch (ParseException | NumberFormatException e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();

                finish();
            }

            Map<String, List<Transaction>> list = new HashMap<>();

        });

        binding.btnChuyentienSubmit.setOnClickListener(v -> {
            Log.d("chuyen tien checked", binding.chipChuyenTien.isChecked() + "" + isNotEmptyInput() + "");
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            if (binding.chipChuyenTien.isChecked() && isNotEmptyInput()) {
                progressDialog.show();


                try {
                    String dateStr = binding.editChuyentienTransactionDay.getText().toString().trim();
                    String timeStr = binding.editChuyentienTransactionTime.getText().toString().trim();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

                    Date date = dateFormat.parse(dateStr + " " + timeStr);
                    if (date != null) {
                        Timestamp timestamp = new Timestamp(date);
                        Transaction newTransaction = new Transaction(
                                FirebaseAuth.getInstance().getUid(),
                                "idLater",
                                2,
                                Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()),
                                binding.editChuyentienNote.getText().toString().trim(),
                                dateStr,
                                timeStr,
                                "Vi tien",
                                "tien mat",
                                currentCategory.getId(),
                                new com.google.firebase.Timestamp(date),
                                false
                        );
                        //String res = FireStoreService.addTransaction(newTransaction);
                        FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                            @Override
                            public void onCallback(String result) {
                                if (result.equals("success")) {
                                    finish();
                                }
                            }
                        });
                    } else {
                        // Handle parsing error or null date
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle parsing exception
                }

                progressDialog.dismiss();
                finish();
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

    private boolean isNotEmptyInput() {
        if (binding.chipChuyenTien.isChecked()) {
            if (binding.editChuyentienAmount.getText().toString().isEmpty()) {
                binding.edtChuyentienAmount.setError("Vui lòng điền số tiền");
                return false;
            } else if (binding.editChuyentienNote.getText().toString().isEmpty()) {
                binding.edtChuyentienNote.setError("Vui lòng điền ghi chú");
                return false;
            } else if (binding.editChuyentienTransactionDay.getText().toString().isEmpty()) {
                binding.edtChuyentienTransactionDay.setError("Vui lòng chọn ngày giao dịch");
                return false;
            } else if (binding.editChuyentienTransactionTime.getText().toString().isEmpty()) {
                binding.edtChuyentienTransactionTime.setError("Vui lòng chọn thời gian giao dịch");
                return false;
            }
        } else {
            if (binding.editAmount.getText().toString().isEmpty()) {
                binding.edtAmount.setError("Vui lòng điền số tiền");
                return false;
            } else if (binding.editNote.getText().toString().isEmpty()) {
                binding.edtNote.setError("Vui lòng điền ghi chú");
                return false;
            } else if (binding.editTransactionDay.getText().toString().isEmpty()) {
                binding.edtTransactionDay.setError("Vui lòng chọn ngày giao dịch");
                return false;
            } else if (binding.editTransactionTime.getText().toString().isEmpty()) {
                binding.edtTransactionTime.setError("Vui lòng chọn thời gian giao dịch");
                return false;
            }
        }
        return true;
    }
}