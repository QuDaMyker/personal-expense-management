package com.learning.personal_expense_management.controller.transaction.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
import com.learning.personal_expense_management.controller.transaction.adapter.HandleDialogNoti;
import com.learning.personal_expense_management.databinding.ActivityTransactionAddBinding;
import com.learning.personal_expense_management.databinding.ActivityTransactionFilterBinding;
import com.learning.personal_expense_management.databinding.DialogDetailTransactionBinding;
import com.learning.personal_expense_management.databinding.DialogValidationBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.FirestoreCallback;
import com.learning.personal_expense_management.services.OneAccountListener;
import com.learning.personal_expense_management.services.OneCategoryListener;
import com.learning.personal_expense_management.services.OneTransactionListener;
import com.learning.personal_expense_management.services.OneWalletListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import java.util.Objects;

public class TransactionAddActivity extends AppCompatActivity {
    private ActivityTransactionAddBinding binding;
    private ActivityResultLauncher<Intent> addWalletLaunch;
    private ActivityResultLauncher<Intent> addAccountLaunch;
    private ActivityResultLauncher<Intent> addCategoryLaunch;
    private ActivityResultLauncher<Intent> addChuyenTienTaiKhoanNguon;
    private ActivityResultLauncher<Intent> addChuyenTienTaiKhoanDich;
    private ProgressDialog progressDialog;
    private Transaction transactionEdit;
    private Boolean isEdit = false;
    private Boolean isCard = false;
    private Transaction currentTransaction = null;
    private Transaction oldTransaction = null;
    private Category currentCategory = null;
    private Account currentAccount = null;
    private Account currentAccountResource = null;
    private Account currentAccountTarget = null;
    private Wallet currentWallet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        setListeners();
    }

    private void init() {
        handleIntent();
        // invisible switch Future
        binding.clTransactionFuture.setVisibility(View.GONE);
        binding.clChuyentienTransactionFuture.setVisibility(View.GONE);

        //=====================
        progressDialog = new ProgressDialog(TransactionAddActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        NumberFormat formatter = new DecimalFormat("#,###");

//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("transactionEdit")) {
//            transactionEdit = (Transaction) intent.getSerializableExtra("transactionEdit");
//            isEdit = true;
//        }


        addWalletLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromWallet = result.getData();
                    String ownerId = intentFromWallet.getStringExtra("ownerId");
                    String id = intentFromWallet.getStringExtra("id");
                    String walletName = intentFromWallet.getStringExtra("walletName");
                    boolean lowBalanceAlert = intentFromWallet.getBooleanExtra("lowBalanceAlert", false);
                    int minimumBalance = intentFromWallet.getIntExtra("minimumBalance", 0);
                    boolean goalSavingsEnabled = intentFromWallet.getBooleanExtra("goalSavingsEnabled", false);
                    int goalAmount = intentFromWallet.getIntExtra("goalAmount", 0);
                    String savingsDeadline = intentFromWallet.getStringExtra("savingsDeadline");
                    int frequency = intentFromWallet.getIntExtra("frequency", 0);
                    int currentMoney = intentFromWallet.getIntExtra("currentMoney", 0);

                    currentWallet = new Wallet(ownerId, id, walletName, lowBalanceAlert, minimumBalance, goalSavingsEnabled, goalAmount, savingsDeadline, frequency, currentMoney);

                    binding.titleWallet.setText(currentWallet.getWalletName());
                    Toast.makeText(TransactionAddActivity.this, "", Toast.LENGTH_SHORT).show();
                    binding.subTitleWallet.setText(String.format("Số dư: %s₫", formatter.format(currentWallet.getCurrentMoney())));
                }

            }
        });

        addAccountLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromAccount = result.getData();
                    String ownerId = intentFromAccount.getStringExtra("ownerId");
                    String id = intentFromAccount.getStringExtra("id");
                    String accountType = intentFromAccount.getStringExtra("accountType");
                    String cardName = intentFromAccount.getStringExtra("cardName");
                    String cardNumber = intentFromAccount.getStringExtra("cardNumber");
                    String expirationDate = intentFromAccount.getStringExtra("expirationDate");
                    int currentBalance = intentFromAccount.getIntExtra("currentBalance", 0);
                    currentAccount = new Account(ownerId, id, accountType, cardName, cardNumber, expirationDate, currentBalance);


                    switch (currentAccount.getAccountType()) {
                        case "Tiền mặt":
                            binding.imgTaikhoan.setImageResource(R.drawable.ic_money_2);
                            isCard = false;
                            break;
                        case "Thẻ Visa":
                            binding.imgTaikhoan.setImageResource(R.drawable.visa_logo);
                            isCard = true;
                            break;
                        case "Thẻ Mastercard":
                            binding.imgTaikhoan.setImageResource(R.drawable.mastercard_logo);
                            isCard = true;
                            break;
                        case "Thẻ JCB":
                            binding.imgTaikhoan.setImageResource(R.drawable.jcb_logo);
                            isCard = true;
                            break;
                        case "Ví điện tử MoMo":
                            binding.imgTaikhoan.setImageResource(R.drawable.momo_logo);
                            isCard = false;
                            break;
                        case "Ví điện tử ShopeePay":
                            binding.imgTaikhoan.setImageResource(R.drawable.shopeepay_logo);
                            isCard = false;
                            break;
                        case "Ví điện tử ZaloPay":
                            binding.imgTaikhoan.setImageResource(R.drawable.zalopay_logo);
                            isCard = false;
                            break;
                        case "Ví điện tử khác":
                            binding.imgTaikhoan.setImageResource(R.drawable.ic_ewallet);
                            isCard = false;
                            break;
                        case "Thẻ ngân hàng":
                            binding.imgTaikhoan.setImageResource(R.drawable.ic_credit_card);
                            isCard = true;
                            break;
                        default:
                            break;
                    }

                    binding.titleTaikhoan.setText(currentAccount.getAccountType());
                    binding.subTitleTaikhoan.setText(String.format("Số dư: %s₫", formatter.format(currentAccount.getCurrentBalance())));
                }
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
                    binding.danhmuc.setCardBackgroundColor(ContextCompat.getColor(getBaseContext(), background));
                    Toast.makeText(TransactionAddActivity.this, background + "", Toast.LENGTH_SHORT).show();
                    binding.titleDanhmuc.setText(name);

//                    binding.icon.setImageResource(category.getIcon());
//                    int colorIcon = context.getResources().getColor(category.getColorIcon());
//                    binding.icon.setColorFilter(colorIcon);
                }
            }
        });
        addChuyenTienTaiKhoanNguon = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromAccount = result.getData();
                    String ownerId = intentFromAccount.getStringExtra("ownerId");
                    String id = intentFromAccount.getStringExtra("id");
                    String accountType = intentFromAccount.getStringExtra("accountType");
                    String cardName = intentFromAccount.getStringExtra("cardName");
                    String cardNumber = intentFromAccount.getStringExtra("cardNumber");
                    String expirationDate = intentFromAccount.getStringExtra("expirationDate");
                    int currentBalance = intentFromAccount.getIntExtra("currentBalance", 0);


                    if (currentAccountTarget == null || (!currentAccountTarget.getId().trim().equals(id.trim()) && currentAccountTarget != null)) {
                        currentAccountResource = new Account(ownerId, id, accountType, cardName, cardNumber, expirationDate, currentBalance);

                        switch (currentAccountResource.getAccountType()) {
                            case "Tiền mặt":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.ic_money_2);
                                isCard = false;
                                break;
                            case "Thẻ Visa":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.visa_logo);
                                isCard = true;
                                break;
                            case "Thẻ Mastercard":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.mastercard_logo);
                                isCard = true;
                                break;
                            case "Thẻ JCB":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.jcb_logo);
                                isCard = true;
                                break;
                            case "Ví điện tử MoMo":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.momo_logo);
                                isCard = false;
                                break;
                            case "Ví điện tử ShopeePay":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.shopeepay_logo);
                                isCard = false;
                                break;
                            case "Ví điện tử ZaloPay":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.zalopay_logo);
                                isCard = false;
                                break;
                            case "Ví điện tử khác":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.ic_ewallet);
                                isCard = false;
                                break;
                            case "Thẻ ngân hàng":
                                binding.imgTaikhoannguon.setImageResource(R.drawable.ic_credit_card);
                                isCard = true;
                                break;
                            default:
                                break;
                        }
                        int colorSecondary50 = ContextCompat.getColor(TransactionAddActivity.this, R.color.secondary50);
                        binding.titleTaikhoannguon.setText(currentAccountResource.getAccountType());
                        binding.titleTaikhoannguon.setTextColor(colorSecondary50);
                        binding.subTitleTaikhoannguon.setText(String.format("Số dư: %s₫", formatter.format(currentAccountResource.getCurrentBalance())));
                        binding.subTitleTaikhoannguon.setTextColor(colorSecondary50);

                    } else if (currentAccountTarget.getId().trim().equals(id.trim()) && currentAccountTarget != null) {
                        binding.titleTaikhoannguon.setText("Tên tài khoản đích");
                        binding.titleTaikhoannguon.setTextColor(Color.RED);
                        binding.subTitleTaikhoannguon.setText("Số dư tài khoản đích");
                        binding.subTitleTaikhoannguon.setTextColor(Color.RED);
                        openDialog(Gravity.CENTER, "Không được chọn trùng với tài khoản nguồn");
                    }
                }
            }
        });
        addChuyenTienTaiKhoanDich = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromAccount = result.getData();
                    String ownerId = intentFromAccount.getStringExtra("ownerId");
                    String id = intentFromAccount.getStringExtra("id");
                    String accountType = intentFromAccount.getStringExtra("accountType");
                    String cardName = intentFromAccount.getStringExtra("cardName");
                    String cardNumber = intentFromAccount.getStringExtra("cardNumber");
                    String expirationDate = intentFromAccount.getStringExtra("expirationDate");
                    int currentBalance = intentFromAccount.getIntExtra("currentBalance", 0);
                    if (currentAccountResource == null || (!currentAccountResource.getId().trim().equals(id.trim()) && currentAccountResource != null)) {
                        currentAccountTarget = new Account(ownerId, id, accountType, cardName, cardNumber, expirationDate, currentBalance);

                        switch (currentAccountTarget.getAccountType()) {
                            case "Tiền mặt":
                                binding.imgTaikhoandich.setImageResource(R.drawable.ic_money_2);
                                isCard = false;
                                break;
                            case "Thẻ Visa":
                                binding.imgTaikhoandich.setImageResource(R.drawable.visa_logo);
                                isCard = true;
                                break;
                            case "Thẻ Mastercard":
                                binding.imgTaikhoandich.setImageResource(R.drawable.mastercard_logo);
                                isCard = true;
                                break;
                            case "Thẻ JCB":
                                binding.imgTaikhoandich.setImageResource(R.drawable.jcb_logo);
                                isCard = true;
                                break;
                            case "Ví điện tử MoMo":
                                binding.imgTaikhoandich.setImageResource(R.drawable.momo_logo);
                                isCard = false;
                                break;
                            case "Ví điện tử ShopeePay":
                                binding.imgTaikhoandich.setImageResource(R.drawable.shopeepay_logo);
                                isCard = false;
                                break;
                            case "Ví điện tử ZaloPay":
                                binding.imgTaikhoandich.setImageResource(R.drawable.zalopay_logo);
                                isCard = false;
                                break;
                            case "Ví điện tử khác":
                                binding.imgTaikhoandich.setImageResource(R.drawable.ic_ewallet);
                                isCard = false;
                                break;
                            case "Thẻ ngân hàng":
                                binding.imgTaikhoandich.setImageResource(R.drawable.ic_credit_card);
                                isCard = true;
                                break;
                            default:
                                break;
                        }
                        int colorSecondary50 = ContextCompat.getColor(TransactionAddActivity.this, R.color.secondary50);
                        binding.titleTaikhoandich.setText(currentAccountTarget.getAccountType());
                        binding.titleTaikhoandich.setTextColor(colorSecondary50);
                        binding.subTitleTaikhoandich.setText(String.format("Số dư: %s₫", formatter.format(currentAccountTarget.getCurrentBalance())));
                        binding.subTitleTaikhoandich.setTextColor(colorSecondary50);
                    } else if (currentAccountResource.getId().trim().equals(id.trim()) && currentAccountResource != null) {
                        currentAccountTarget = null;
                        binding.titleTaikhoandich.setText("Tên tài khoản đích");
                        binding.titleTaikhoandich.setTextColor(Color.RED);
                        binding.subTitleTaikhoandich.setText("Số dư tài khoản đích");
                        binding.subTitleTaikhoandich.setTextColor(Color.RED);
                        openDialog(Gravity.CENTER, "Không được chọn trùng với tài khoản đích");

                    }
                }
            }
        });
        //setLayoutChip(isEdit);
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
                Log.d("switchFuture", isChecked + "");
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
                Log.d("switchChuyentienFuture", isChecked + "");

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

        binding.clChuyentienTaikhoannguon.setOnClickListener(v -> {
            Intent intentAddWallet = new Intent(TransactionAddActivity.this, TransactionAddActivity_SelectionAccount.class);
            addChuyenTienTaiKhoanNguon.launch(intentAddWallet);
        });

        binding.clChuyentienTaikhoandich.setOnClickListener(v -> {
            Intent intentAddWallet = new Intent(TransactionAddActivity.this, TransactionAddActivity_SelectionAccount.class);
            addChuyenTienTaiKhoanDich.launch(intentAddWallet);
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
            if (currentWallet == null) {
                openDialog(Gravity.CENTER, "Bạn chưa chọn ví tiền");
            } else if (currentAccount == null) {
                openDialog(Gravity.CENTER, "Bạn chưa chọn tài khoản");
            } else if (currentCategory == null) {
                openDialog(Gravity.CENTER, "Bạn chưa chọn loại danh mục");
            } else if (isNotEmptyInputWithoutDateTime()) {
                progressDialog.show();
                try {
                    if (binding.chipChi.isChecked()) {
                        if (currentWallet.getCurrentMoney() < Integer.parseInt(binding.editAmount.getText().toString().trim())) {
                            openDialog(Gravity.CENTER, "Số dư ví không đủ");
                        } else if (currentAccount.getCurrentBalance() < Integer.parseInt(binding.editAmount.getText().toString().trim())) {
                            openDialog(Gravity.CENTER, "Số dư tài khoản không đủ");
                        } else {
                            boolean isEnableAlert = currentWallet.isLowBalanceAlert();
                            boolean isAlert = (currentWallet.getCurrentMoney() - Integer.parseInt(binding.editAmount.getText().toString().trim())) < currentWallet.getMinimumBalance();
                            if (isEnableAlert) {
                                if (isAlert) {
                                    openDialogListener(Gravity.CENTER, "Sau khi thực hiện giao dịch này, số dư ví sẽ dưới mức tối thiểu", new HandleDialogNoti() {
                                        @Override
                                        public void pressOK(String message) throws ParseException {
                                            if (message.equals("close")) {
                                                handleThuChi();
                                            }
                                        }
                                    });
                                } else {
                                    handleThuChi();
                                }
                            } else {
                                handleThuChi();
                            }
                        }
                    } else if (binding.chipThu.isChecked()) {
                        handleThuChi();
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                progressDialog.dismiss();
            }
        });

        binding.btnChuyentienSubmit.setOnClickListener(v -> {

            if (currentAccountResource == null) {
                openDialog(Gravity.CENTER, "Bạn chưa chọn tài khoản nguồn");
            } else if (currentAccountTarget == null) {
                openDialog(Gravity.CENTER, "Bạn chưa chọn tài khoản đích");
            } else if (binding.chipChuyenTien.isChecked() && isNotEmptyInputWithoutDateTime()) {
                progressDialog.show();
                try {
                    if (currentAccountResource.getCurrentBalance() < Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim())) {
                        openDialog(Gravity.CENTER, "Tài khoản nguồn không đủ số dư");
                    } else {
                        handleChuyenTien();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void handleIntent() {
        NumberFormat formatter = new DecimalFormat("#,###");
        Intent intent = getIntent();
        int transactionType = intent.getIntExtra("transactionType", -1);
        if (transactionType != -1) {
            isEdit = true;
            String ownerId = intent.getStringExtra("ownerId");
            String id = intent.getStringExtra("id");

            int amount = intent.getIntExtra("amount", 0);
            String note = intent.getStringExtra("note");
            String transactionDate = intent.getStringExtra("transactionDate");
            String transactionTime = intent.getStringExtra("transactionTime");
            String sourceAccount = intent.getStringExtra("sourceAccount");
            String destinationAccount = intent.getStringExtra("destinationAccount");
            String categoryId = intent.getStringExtra("categoryId");
            Timestamp timeStamp = null;
            String month = intent.getStringExtra("month");
            String year = intent.getStringExtra("year");
            boolean isFuture = intent.getBooleanExtra("isFuture", false);

            oldTransaction = new Transaction(ownerId, id, transactionType, amount, note, transactionDate, transactionTime, sourceAccount, destinationAccount, categoryId, timeStamp, isFuture);
            if (transactionType != -1) {
                FireStoreService.getOneTransaction(id, new OneTransactionListener() {
                    @Override
                    public void getTransaction(Transaction transaction) {
                        currentTransaction = transaction;

                        if (currentTransaction.getTransactionType() != 2) {
                            binding.clRootChuyentien.setVisibility(View.GONE);
                            binding.clRootThuchi.setVisibility(View.VISIBLE);
                            if (currentTransaction.getTransactionType() == 1) {
                                binding.chipThu.setChecked(true);
                            } else {
                                binding.chipChi.setChecked(true);
                            }
                            FireStoreService.getOneWallet(currentTransaction.getSourceAccount(), new OneWalletListener() {
                                @Override
                                public void getWallet(Wallet wallet) {
                                    currentWallet = wallet;
                                    binding.titleWallet.setText(currentWallet.getWalletName());
                                    binding.subTitleWallet.setText(String.format("Số dư: %s₫", formatter.format(currentWallet.getCurrentMoney())));
                                }
                            });


                            FireStoreService.getOneAccount(currentTransaction.getDestinationAccount(), new OneAccountListener() {
                                @Override
                                public void getAccount(Account account) {
                                    currentAccount = account;
                                    switch (currentAccount.getAccountType()) {
                                        case "Tiền mặt":
                                            binding.imgTaikhoan.setImageResource(R.drawable.ic_money_2);
                                            isCard = false;
                                            break;
                                        case "Thẻ Visa":
                                            binding.imgTaikhoan.setImageResource(R.drawable.visa_logo);
                                            isCard = true;
                                            break;
                                        case "Thẻ Mastercard":
                                            binding.imgTaikhoan.setImageResource(R.drawable.mastercard_logo);
                                            isCard = true;
                                            break;
                                        case "Thẻ JCB":
                                            binding.imgTaikhoan.setImageResource(R.drawable.jcb_logo);
                                            isCard = true;
                                            break;
                                        case "Ví điện tử MoMo":
                                            binding.imgTaikhoan.setImageResource(R.drawable.momo_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử ShopeePay":
                                            binding.imgTaikhoan.setImageResource(R.drawable.shopeepay_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử ZaloPay":
                                            binding.imgTaikhoan.setImageResource(R.drawable.zalopay_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử khác":
                                            binding.imgTaikhoan.setImageResource(R.drawable.ic_ewallet);
                                            isCard = false;
                                            break;
                                        case "Thẻ ngân hàng":
                                            binding.imgTaikhoan.setImageResource(R.drawable.ic_credit_card);
                                            isCard = true;
                                            break;
                                        default:
                                            break;
                                    }

                                    binding.titleTaikhoan.setText(currentAccount.getAccountType());
                                    binding.subTitleTaikhoan.setText(String.format("Số dư: %s₫", formatter.format(currentAccount.getCurrentBalance())));
                                }
                            });

                            FireStoreService.getOneCategory(categoryId, new OneCategoryListener() {
                                @Override
                                public void getCategory(Category category) {
                                    currentCategory = category;

                                    binding.imgDanhmuc.setImageResource(currentCategory.getIcon());
                                    binding.danhmuc.setCardBackgroundColor(ContextCompat.getColor(getBaseContext(), currentCategory.getBackGround()));
                                    binding.titleDanhmuc.setText(currentCategory.getName());
                                }
                            });
                            binding.editAmount.setText(currentTransaction.getAmount() + "");
                            binding.editNote.setText(currentTransaction.getNote());

                            if (currentTransaction.isFuture()) {
                                binding.switchFuture.setChecked(true);
                            } else {
                                binding.editTransactionDay.setText(currentTransaction.getTransactionDate());
                                binding.editTransactionTime.setText(currentTransaction.getTransactionTime());
                            }
                        } else {
                            binding.chipChuyenTien.setChecked(true);
                            binding.clRootThuchi.setVisibility(View.GONE);
                            binding.clRootChuyentien.setVisibility(View.VISIBLE);
                            FireStoreService.getOneAccount(currentTransaction.getSourceAccount(), new OneAccountListener() {
                                @Override
                                public void getAccount(Account account) {
                                    currentAccountResource = account;

                                    switch (currentAccountResource.getAccountType()) {
                                        case "Tiền mặt":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.ic_money_2);
                                            isCard = false;
                                            break;
                                        case "Thẻ Visa":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.visa_logo);
                                            isCard = true;
                                            break;
                                        case "Thẻ Mastercard":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.mastercard_logo);
                                            isCard = true;
                                            break;
                                        case "Thẻ JCB":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.jcb_logo);
                                            isCard = true;
                                            break;
                                        case "Ví điện tử MoMo":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.momo_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử ShopeePay":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.shopeepay_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử ZaloPay":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.zalopay_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử khác":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.ic_ewallet);
                                            isCard = false;
                                            break;
                                        case "Thẻ ngân hàng":
                                            binding.imgTaikhoannguon.setImageResource(R.drawable.ic_credit_card);
                                            isCard = true;
                                            break;
                                        default:
                                            break;
                                    }
                                    int colorSecondary50 = ContextCompat.getColor(TransactionAddActivity.this, R.color.secondary50);
                                    binding.titleTaikhoannguon.setText(currentAccountResource.getAccountType());
                                    binding.titleTaikhoannguon.setTextColor(colorSecondary50);
                                    binding.subTitleTaikhoannguon.setText(String.format("Số dư: %s₫", formatter.format(currentAccountResource.getCurrentBalance())));
                                    binding.subTitleTaikhoannguon.setTextColor(colorSecondary50);
                                }
                            });

                            FireStoreService.getOneAccount(currentTransaction.getDestinationAccount(), new OneAccountListener() {
                                @Override
                                public void getAccount(Account account) {
                                    currentAccountTarget = account;

                                    switch (currentAccountTarget.getAccountType()) {
                                        case "Tiền mặt":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.ic_money_2);
                                            isCard = false;
                                            break;
                                        case "Thẻ Visa":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.visa_logo);
                                            isCard = true;
                                            break;
                                        case "Thẻ Mastercard":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.mastercard_logo);
                                            isCard = true;
                                            break;
                                        case "Thẻ JCB":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.jcb_logo);
                                            isCard = true;
                                            break;
                                        case "Ví điện tử MoMo":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.momo_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử ShopeePay":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.shopeepay_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử ZaloPay":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.zalopay_logo);
                                            isCard = false;
                                            break;
                                        case "Ví điện tử khác":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.ic_ewallet);
                                            isCard = false;
                                            break;
                                        case "Thẻ ngân hàng":
                                            binding.imgTaikhoandich.setImageResource(R.drawable.ic_credit_card);
                                            isCard = true;
                                            break;
                                        default:
                                            break;
                                    }
                                    int colorSecondary50 = ContextCompat.getColor(TransactionAddActivity.this, R.color.secondary50);
                                    binding.titleTaikhoandich.setText(currentAccountTarget.getAccountType());
                                    binding.titleTaikhoandich.setTextColor(colorSecondary50);
                                    binding.subTitleTaikhoandich.setText(String.format("Số dư: %s₫", formatter.format(currentAccountTarget.getCurrentBalance())));
                                    binding.subTitleTaikhoandich.setTextColor(colorSecondary50);
                                }
                            });

                            binding.editChuyentienAmount.setText(currentTransaction.getAmount() + "");
                            binding.editChuyentienNote.setText(currentTransaction.getNote());
                            binding.editChuyentienTransactionDay.setText(currentTransaction.getTransactionDate());
                            binding.editChuyentienTransactionTime.setText(currentTransaction.getTransactionTime());

                            if (currentTransaction.isFuture()) {
                                binding.switchChuyentienFuture.setChecked(true);
                            } else {
                                binding.editChuyentienTransactionDay.setText(currentTransaction.getTransactionDate());
                                binding.editChuyentienTransactionTime.setText(currentTransaction.getTransactionTime());
                            }
                        }
                    }
                });
            }
        } else {
            binding.chipThu.setChecked(true);
            binding.clRootThuchi.setVisibility(View.VISIBLE);
            binding.clRootChuyentien.setVisibility(View.GONE);
        }

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

    private void handleThuChi() throws ParseException {
        Transaction newTransaction = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy HH:mm", Locale.getDefault());
        if (!binding.switchFuture.isChecked() && isNotEmptyDateTime()) {
            String dateStr = binding.editTransactionDay.getText().toString().trim();
            String timeStr = binding.editTransactionTime.getText().toString().trim();


            Date date = dateFormat.parse(dateStr + " " + timeStr);

            if (date != null) {
                Timestamp timestamp = new Timestamp(date);

                newTransaction = new Transaction(
                        FirebaseAuth.getInstance().getUid(),
                        oldTransaction != null ? oldTransaction.getId() : "idLater",
                        binding.chipThu.isChecked() ? 0 : 1,
                        Integer.parseInt(binding.editAmount.getText().toString().trim()),
                        binding.editNote.getText().toString().trim(),
                        binding.editTransactionDay.getText().toString().trim(),
                        binding.editTransactionTime.getText().toString().trim(),
                        currentWallet.getId(),
                        currentAccount.getId(),
                        currentCategory.getId(),
                        new com.google.firebase.Timestamp(date),
                        false
                );
            }
        } else {
            // isFuture == true
            Date date = dateFormat.parse(getCurrentDate() + " " + getCurrentTime());
            newTransaction = new Transaction(
                    FirebaseAuth.getInstance().getUid(),
                    oldTransaction != null ? oldTransaction.getId() : "idLater",
                    binding.chipThu.isChecked() ? 0 : 1,
                    Integer.parseInt(binding.editAmount.getText().toString().trim()),
                    binding.editNote.getText().toString().trim(),
                    getCurrentDate(),
                    getCurrentTime(),
                    currentWallet.getId(),
                    currentAccount.getId(),
                    currentCategory.getId(),
                    new com.google.firebase.Timestamp(date),
                    true
            );
        }

        if (isEdit) {
            if(binding.chipThu.isChecked()) {
                currentAccount.setCurrentBalance(currentAccount.getCurrentBalance() + oldTransaction.getAmount() + Integer.parseInt(binding.editAmount.getText().toString().trim()));
            }else if(binding.chipChi.isChecked()){
                currentAccount.setCurrentBalance(currentAccount.getCurrentBalance() + oldTransaction.getAmount() - Integer.parseInt(binding.editAmount.getText().toString().trim()));
            }
            FireStoreService.editAccount(currentAccount, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {

                }
            });

            if(binding.chipThu.isChecked()) {
                currentWallet.setCurrentMoney(currentWallet.getCurrentMoney() + oldTransaction.getAmount() + Integer.parseInt(binding.editAmount.getText().toString().trim()));

            }else if(binding.chipChi.isChecked()) {
                currentWallet.setCurrentMoney(currentWallet.getCurrentMoney() + oldTransaction.getAmount() - Integer.parseInt(binding.editAmount.getText().toString().trim()));
            }

            FireStoreService.editWallet(currentWallet);

            if (newTransaction != null) {
                FireStoreService.updateTransaction(newTransaction, new FirestoreCallback() {
                    @Override
                    public void onCallback(String result) {
                        if (result.equals("success")) {
                            Log.e("isEdit - transaction - thu chi", "thanh cong");
                            finish();
                        }
                    }
                });
            }
        } else {

            if(binding.chipThu.isChecked()) {
                currentAccount.setCurrentBalance(currentAccount.getCurrentBalance() + Integer.parseInt(binding.editAmount.getText().toString().trim()));
            }else if(binding.chipChi.isChecked()) {
                currentAccount.setCurrentBalance(currentAccount.getCurrentBalance() - Integer.parseInt(binding.editAmount.getText().toString().trim()));
            }
            FireStoreService.editAccount(currentAccount, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {

                }
            });
            if(binding.chipThu.isChecked()) {
                currentWallet.setCurrentMoney(currentWallet.getCurrentMoney() + Integer.parseInt(binding.editAmount.getText().toString().trim()));

            }else if(binding.chipChi.isChecked()) {
                currentWallet.setCurrentMoney(currentWallet.getCurrentMoney() - Integer.parseInt(binding.editAmount.getText().toString().trim()));

            }

            FireStoreService.editWallet(currentWallet);

            if (newTransaction != null) {
                FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                    @Override
                    public void onCallback(String result) {
                        Toast.makeText(TransactionAddActivity.this, "result" + result, Toast.LENGTH_SHORT).show();
                        if (!result.equals("error")) {
                            finish();
                        }
                    }
                });
            }
        }

    }

    private void handleChuyenTien() throws ParseException {
        Transaction newTransaction = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        if (!binding.switchChuyentienFuture.isChecked() && isNotEmptyDateTime()) {
            String dateStr = binding.editChuyentienTransactionDay.getText().toString().trim();
            String timeStr = binding.editChuyentienTransactionTime.getText().toString().trim();

            Date date = dateFormat.parse(dateStr + " " + timeStr);
            if (date != null) {
                Timestamp timestamp = new Timestamp(date);
                newTransaction = new Transaction(
                        FirebaseAuth.getInstance().getUid(),
                        oldTransaction != null ? oldTransaction.getId() : "idLater",
                        2,
                        Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()),
                        binding.editChuyentienNote.getText().toString().trim(),
                        dateStr,
                        timeStr,
                        currentAccountResource.getId(),
                        currentAccountTarget.getId(),
                        "currentCategory.getId()",
                        new com.google.firebase.Timestamp(date),
                        false
                );
            }
        } else {
            Date date = dateFormat.parse(getCurrentDate() + " " + getCurrentTime());
            Timestamp timestamp = new Timestamp(date);
            newTransaction = new Transaction(
                    FirebaseAuth.getInstance().getUid(),
                    oldTransaction != null ? oldTransaction.getId() : "idLater",
                    2,
                    Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()),
                    binding.editChuyentienNote.getText().toString().trim(),
                    getCurrentDate(),
                    getCurrentTime(),
                    currentAccountResource.getId(),
                    currentAccountTarget.getId(),
                    "currentCategory.getId()",
                    new com.google.firebase.Timestamp(date),
                    true
            );

        }

        if (isEdit) {
            currentAccountResource.setCurrentBalance(currentAccountResource.getCurrentBalance() + oldTransaction.getAmount() - Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()));
            FireStoreService.editAccount(currentAccountResource, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {
                    if (!result.equals("error")) {
                        Log.e("editAccount - rs", "currentAccountResource: " + currentAccountResource.getCurrentBalance() + "");
                    } else {
                        Log.e("editAccount - rs", "currentAccountResource: Error");
                    }
                }
            });

            currentAccountTarget.setCurrentBalance(currentAccountTarget.getCurrentBalance() - oldTransaction.getAmount() + Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()));
            FireStoreService.editAccount(currentAccountTarget, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {
                    if (!result.equals("error")) {
                        Log.e("editAccount - rs", "currentAccountTarget: " + currentAccountTarget.getCurrentBalance() + "");
                    } else {
                        Log.e("editAccount - rs", "currentAccountTarget: Error");
                    }
                }
            });
            FireStoreService.updateTransaction(newTransaction, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {
                    if (result.equals("success")) {
                        finish();
                    }
                }
            });
        } else {
            currentAccountResource.setCurrentBalance(currentAccountResource.getCurrentBalance() - Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()));
            FireStoreService.editAccount(currentAccountResource, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {
                    if (result.equals("success")) {
                        Log.e("editAccount - rs", "currentAccountResource: " + currentAccountResource.getCurrentBalance() + "");
                    } else {
                        Log.e("editAccount - rs", "currentAccountResource: Error");
                    }
                }
            });

            currentAccountTarget.setCurrentBalance(currentAccountTarget.getCurrentBalance() + Integer.parseInt(binding.editChuyentienAmount.getText().toString().trim()));
            FireStoreService.editAccount(currentAccountTarget, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {
                    if (result.equals("success")) {
                        Log.e("editAccount - rs", "currentAccountTarget: " + currentAccountTarget.getCurrentBalance() + "");
                    } else {
                        Log.e("editAccount - rs", "currentAccountTarget: Error");
                    }
                }
            });
            FireStoreService.addTransaction(newTransaction, new FirestoreCallback() {
                @Override
                public void onCallback(String result) {
                    if (!result.equals("error")) {
                        finish();
                    }
                }
            });
        }
    }

    private void openDatePickerDialog(String typeChip) {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker().setTitleText("Chọn ngày thông báo").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).setNegativeButtonText("Hủy").setPositiveButtonText("Chọn");

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

    private boolean isNotEmptyInputWithoutDateTime() {
        if (binding.chipChuyenTien.isChecked()) {
            if (binding.editChuyentienAmount.getText().toString().isEmpty()) {
                binding.edtChuyentienAmount.setError("Vui lòng điền số tiền");
                return false;
            } else if (binding.editChuyentienNote.getText().toString().isEmpty()) {
                binding.edtChuyentienNote.setError("Vui lòng điền ghi chú");
                return false;
            }
        } else {
            if (binding.editAmount.getText().toString().isEmpty()) {
                binding.edtAmount.setError("Vui lòng điền số tiền");
                return false;
            } else if (binding.editNote.getText().toString().isEmpty()) {
                binding.edtNote.setError("Vui lòng điền ghi chú");
                return false;
            }
        }
        return true;
    }

    private boolean isNotEmptyDateTime() {
        if (binding.chipChuyenTien.isChecked()) {
            if (binding.editChuyentienTransactionDay.getText().toString().isEmpty()) {
                binding.edtChuyentienTransactionDay.setError("Vui lòng chọn ngày giao dịch");
                return false;
            } else if (binding.editChuyentienTransactionTime.getText().toString().isEmpty()) {
                binding.edtChuyentienTransactionTime.setError("Vui lòng chọn thời gian giao dịch");
                return false;
            }
        } else {
            if (binding.editTransactionDay.getText().toString().isEmpty()) {
                binding.edtTransactionDay.setError("Vui lòng chọn ngày giao dịch");
                return false;
            } else if (binding.editTransactionTime.getText().toString().isEmpty()) {
                binding.edtTransactionTime.setError("Vui lòng chọn thời gian giao dịch");
                return false;
            }
        }

        return true;
    }

    private void openDialog(int gravity, String message) {
        final Dialog dialog = new Dialog(TransactionAddActivity.this);
        DialogValidationBinding dialogVal;
        dialogVal = DialogValidationBinding.inflate(getLayoutInflater());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogVal.getRoot());


        dialogVal.tvMessage.setText(message);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialogVal.btnOk.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }

    private void openDialogListener(int gravity, String message, HandleDialogNoti handleDialogNoti) {
        final Dialog dialog = new Dialog(TransactionAddActivity.this);
        DialogValidationBinding dialogVal;
        dialogVal = DialogValidationBinding.inflate(getLayoutInflater());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogVal.getRoot());


        dialogVal.tvMessage.setText(message);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialogVal.btnOk.setOnClickListener(v -> {
            try {
                handleDialogNoti.pressOK("close");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });


        dialog.show();
    }

    @NonNull
    public static String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = new Date(System.currentTimeMillis());
        return timeFormat.format(currentTime);
    }

    @NonNull
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
        Date currentDate = new Date(System.currentTimeMillis());
        return dateFormat.format(currentDate);
    }

    private boolean checkAvailableResourceMoney(int resourceMoney, int payMoney) {
        return resourceMoney >= payMoney;
    }
}