package com.learning.personal_expense_management.controller.wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.Timestamp;
import com.learning.personal_expense_management.R;

import com.learning.personal_expense_management.controller.transaction.activity.TransactionAddActivity;
import com.learning.personal_expense_management.controller.transaction.adapter.ObjectListener;
import com.learning.personal_expense_management.controller.transaction.adapter.TransactionAdapter;
import com.learning.personal_expense_management.databinding.ActivityWalletDetailBinding;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.model.Wallet;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.TransactionListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletDetailActivity extends AppCompatActivity implements ObjectListener{
    private ActivityWalletDetailBinding binding;
    private Wallet selectedWallet;
    private List<Transaction> currentTransactions;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        selectedWallet = (Wallet) intent.getSerializableExtra("selectedWallet");

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvNameWallet.setText(selectedWallet.getWalletName());
        binding.tvMoneyWallet.setText(String.valueOf(selectedWallet.getCurrentMoney())+" đ");


        int goalAmount = selectedWallet.getGoalAmount();
        if(goalAmount>0){
            String[] deadlineSaving = selectedWallet.getSavingsDeadline().split("-");
            binding.monthYearDeadline.setText(deadlineSaving[1] +" - " + deadlineSaving[0]);
            binding.dateDeadline.setText(deadlineSaving[2]);
            int currentMoney = selectedWallet.getCurrentMoney();
            int valueProgress;
            if(currentMoney<goalAmount) {
                valueProgress = (int) ((float) currentMoney / goalAmount * 100);
            }
            else {
                valueProgress = 100;
            }
            int months = Integer.valueOf(deadlineSaving[1]) - LocalDate.now().getMonthValue();

            binding.tvPretensionWallet.setText(String.valueOf(goalAmount)+" đ");
            if(months == 0){
                months = 1;
            }
            binding.tvSavingPerMonth.setText(String.valueOf(goalAmount-currentMoney/months) + " đ");
            binding.progressBar.setProgress(valueProgress);
        }
        else{
            binding.tvPretensionWallet.setVisibility(View.GONE);
            binding.tvSavingPerMonth.setVisibility(View.GONE);
            binding.lineMonthSavingMoney.setVisibility(View.GONE);
                    
            if (selectedWallet.getCurrentMoney()==0){
                binding.progressBar.setProgress(0);
            }
            else {
                binding.progressBar.setProgress(100);
            }
        }

        binding.btnEditWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletDetailActivity.this, NewWalletActivity.class);
                intent.putExtra("isEdit",true);
                intent.putExtra("selectedWallet",(Serializable) selectedWallet);
                startActivity(intent);
            }
        });

        binding.btnTransactionWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletDetailActivity.this, TransactionAddActivity.class);
                startActivity(intent);
            }
        });

        binding.btnDeleteWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedWallet.getCurrentMoney()==0) {
                    showYesNoDialog();
                }
                else{
                    showDialog();
                }
            }
        });

        FireStoreService.getTransactionWallet(selectedWallet.getOwnerId(), selectedWallet.getWalletName(), new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
//                currentTransactions = new ArrayList<>();
//                currentTransactions.add(temp);
                binding.rvRecentActivity.setLayoutManager(new LinearLayoutManager(binding.rvRecentActivity.getContext()));
                TransactionAdapter transactionAdapter = new TransactionAdapter(binding.rvRecentActivity.getContext(), transactions,(ObjectListener) binding.rvRecentActivity.getContext());
                transactionAdapter.notifyDataSetChanged();
                binding.rvRecentActivity.setAdapter(transactionAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("VIEWHOLDER", "JOIN");
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ví tiền không thể xóa khi vẫn còn tiền!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showYesNoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa ví tiền?");

        // Nút Yes
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FireStoreService.deleteWallet(selectedWallet.getOwnerId(),selectedWallet.getId());
                dialog.dismiss();
                Intent intent = new Intent(WalletDetailActivity.this,WalletActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(Object o) {

    }
}
