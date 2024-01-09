package com.learning.personal_expense_management.controller.account;

import static com.learning.personal_expense_management.utilities.Enum.AccountType.ATM;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.Cash;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.EWallet;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.JCB;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.Mastercard;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.MoMo;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.ShopeePay;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.Visa;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.ZaloPay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityAccountBinding;
import com.learning.personal_expense_management.databinding.ActivityLoginBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.AccountListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.TransactionListener;
import com.learning.personal_expense_management.utilities.Enum;
import com.learning.personal_expense_management.utilities.custom_dialog.ChooseAccountDialog;
import com.learning.personal_expense_management.utilities.custom_dialog.ChooseAccountTypeDialog;

import java.util.List;


public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private Account selectedAccount;
    private List<Account> accountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FireStoreService.getAccount(FirebaseAuth.getInstance().getUid(), new AccountListener() {
            @Override
            public void onAccountsLoaded(List<Account> accounts) {
                accountList = accounts;
                if(accounts.size() > 0){
                    selectAccount(accounts.get(0));
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        binding.addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, AddAccountActivity.class));
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.accountPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseAccountDialog cdd = new ChooseAccountDialog(AccountActivity.this, accountList);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.setListener(new ChooseAccountDialog.MyDialogListener() {
                    @Override
                    public void userSelectedAValue(Account value) {
                        selectAccount(value);
                    }
                });
                cdd.show();
                cdd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cdd.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }

    void selectAccount(Account account){
        boolean isCard = false;
        selectedAccount = account;
        binding.accountName.setText(account.getCardName().isEmpty() ? account.getAccountType() : account.getCardName());

        switch (account.getAccountType()) {
            case "Tiền mặt":
                binding.logo.setImageResource(R.drawable.ic_money_2);
                isCard = false;
                break;
            case "Thẻ Visa":
                binding.logo.setImageResource(R.drawable.visa_logo);
                binding.cardLogo.setImageResource(R.drawable.visa_logo);
                isCard = true;
                break;
            case "Thẻ Mastercard":
                binding.logo.setImageResource(R.drawable.mastercard_logo);
                binding.cardLogo.setImageResource(R.drawable.mastercard_logo);
                isCard = true;
                break;
            case "Thẻ JCB":
                binding.logo.setImageResource(R.drawable.jcb_logo);
                binding.cardLogo.setImageResource(R.drawable.jcb_logo);
                isCard = true;
                break;
            case "Ví điện tử MoMo":
                binding.logo.setImageResource(R.drawable.momo_logo);
                isCard = false;
                break;
            case "Ví điện tử ShopeePay":
                binding.logo.setImageResource(R.drawable.shopeepay_logo);
                isCard = false;
                break;
            case "Ví điện tử ZaloPay":
                binding.logo.setImageResource(R.drawable.zalopay_logo);
                isCard = false;
                break;
            case "Ví điện tử khác":
                binding.logo.setImageResource(R.drawable.ic_ewallet);
                isCard = false;
                break;
            case "Thẻ ngân hàng":
                binding.logo.setImageResource(R.drawable.ic_credit_card);
                binding.cardLogo.setImageResource(R.drawable.ic_credit_card);
                isCard = true;
                break;
            default:
                break;
        }

        binding.tvBalance.setText(selectedAccount.getCurrentBalance() + "₫");
        binding.tvBalanceWallet.setText(selectedAccount.getCurrentBalance() + "₫");
        binding.tvCardNumber.setText(selectedAccount.getCardNumber());
        binding.tvExpiryDate.setText(selectedAccount.getExpirationDate());
        setLayoutVisibility(isCard);

//        FireStoreService.getAllTransaction(FirebaseAuth.getInstance().getUid(), new TransactionListener() {
//            @Override
//            public void onTransactionsLoaded(List<Transaction> transactions) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
    }
    void setLayoutVisibility(boolean isCard){
        if(isCard){
            binding.cardLayout.setVisibility(View.VISIBLE);
            binding.walletLayout.setVisibility(View.GONE);
        }
        else{
            binding.cardLayout.setVisibility(View.GONE);
            binding.walletLayout.setVisibility(View.VISIBLE);
        }
    }
}