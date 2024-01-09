package com.learning.personal_expense_management.controller.wallet.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.controller.wallet.WalletDetailActivity;
import com.learning.personal_expense_management.databinding.ActivityWalletDetailBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Wallet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private static List<Wallet> wallets;

    private OnItemClickListener itemClickListener;
    public WalletAdapter(List<Wallet> dataSet, OnItemClickListener itemClickListener) {
        wallets = dataSet;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallet, parent, false);
        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Wallet wallet = wallets.get(position);
        holder.getTv_title().setText(wallet.getWalletName());
        holder.getTv_current_money().setText(convertCurrency(wallet.getCurrentMoney()));
        holder.getCheckBox().setVisibility(View.GONE);

        int max_money = wallet.getGoalAmount();
        int current_money = wallet.getCurrentMoney();
        if (max_money>0) {
            holder.getTv_date_wallet().setText(dateFormat(wallet.getSavingsDeadline()));
            holder.getTv_max_money().setText(convertCurrency(max_money));
            holder.getTv_saving_per_month().setText(monthlyAmount(wallet.getSavingsDeadline(), max_money) + "đ mỗi tháng");
            if(current_money<max_money) {
                holder.progressBar.setProgress((int) ((float) current_money / max_money * 100));
            }
            else {
                holder.progressBar.setProgress(100);
            }
        }
        else {
            holder.getTv_date_wallet().setText("");
            holder.getTv_max_money().setText("");
            if(wallet.isLowBalanceAlert()){
                holder.getTv_saving_per_month().setText("Không dưới " + wallet.getMinimumBalance() + "đ");
            }
            else{
                holder.getTv_saving_per_month().setText("");
            }
            if (current_money==0){
                holder.progressBar.setProgress(0);
            }
            else {
                holder.progressBar.setProgress(100);
            }
        }
    }

    @Override
    public int getItemCount() {
        return wallets == null ? 0 : wallets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // private final ImageView imageView;
        private final TextView tv_title;
        private final TextView tv_saving_per_month;
        private final TextView tv_current_money;
        private final TextView tv_max_money;
        private final TextView tv_date_wallet;
        private OnItemClickListener itemClickListener;
        private final CheckBox checkBox;

        private final ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            //imageView = (ImageView) itemView.findViewById(R.id.imageWallet);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_saving_per_month = (TextView) itemView.findViewById(R.id.tv_sub_title);
            tv_current_money = (TextView) itemView.findViewById(R.id.tv_current_money);
            tv_max_money = (TextView) itemView.findViewById(R.id.tv_max_money);
            tv_date_wallet = (TextView) itemView.findViewById(R.id.tv_date_wallet);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            itemView.setOnClickListener(this);
        }


        public TextView getTv_title() {
            return tv_title;
        }

        public TextView getTv_saving_per_month() {
            return tv_saving_per_month;
        }

        public TextView getTv_current_money() {
            return tv_current_money;
        }

        public TextView getTv_max_money() {
            return tv_max_money;
        }

        public TextView getTv_date_wallet() {
            return tv_date_wallet;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }


        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    private static String dateFormat(String input) {
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-M-d");
            Date date = sdfInput.parse(input);

            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");
            return sdfOutput.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public int monthlyAmount(String deadline, int totalAmount) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        Date deadlineDate;
        try {
            deadlineDate = formatter.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Date currentDate = new Date();
        long date = (deadlineDate.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000);
        double monthlyAmount = totalAmount / (date / 30.0);
        return (int) monthlyAmount;
    }

    public String convertCurrency(int amount) {
        if (amount < 1000000) {
            return String.valueOf(amount) + "đ";
        } else {
            double convertedAmount = amount / 1000000.0;
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##", symbols);
            return decimalFormat.format(convertedAmount) + " triệu";
        }
    }
}
