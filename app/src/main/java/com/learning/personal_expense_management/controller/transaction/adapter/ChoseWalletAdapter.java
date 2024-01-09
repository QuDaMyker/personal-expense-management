package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.databinding.ItemWalletBinding;
import com.learning.personal_expense_management.model.Wallet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ChoseWalletAdapter extends RecyclerView.Adapter<ChoseWalletAdapter.ViewHolder> {
    private Context context;
    private List<Wallet> list;
    private ObjectListener listener;

    public ChoseWalletAdapter(Context context, List<Wallet> list, ObjectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void setList(List<Wallet> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChoseWalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoseWalletAdapter.ViewHolder(ItemWalletBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChoseWalletAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemWalletBinding binding;

        public ViewHolder(ItemWalletBinding itemWalletBinding) {
            super(itemWalletBinding.getRoot());
            this.binding = itemWalletBinding;
        }

        private void setData(Wallet wallet) {
            NumberFormat formatter = new DecimalFormat("#,###");
            binding.tvTitle.setText(wallet.getWalletName());
            binding.tvCurrentMoney.setText(String.format("%sđ", formatter.format(wallet.getCurrentMoney())));
            binding.tvMaxMoney.setText(String.format("%sđ", formatter.format(wallet.getGoalAmount())));
            binding.progressBar.setProgress(wallet.getCurrentMoney(), true);
            binding.progressBar.setMax(wallet.getGoalAmount());

            binding.getRoot().setOnClickListener(v-> {
                listener.onClick(wallet);
            });
        }
    }
}
