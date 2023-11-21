package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.Transaction;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private Context context;
    private List<Transaction> list;
    private ObjectListener objectListener;

    public TransactionAdapter(Context context, List<Transaction> list, ObjectListener objectListener) {
        this.context = context;
        this.list = list;
        this.objectListener = objectListener;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionAdapter.ViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTransactionBinding binding;

        public ViewHolder(ItemTransactionBinding itemTransactionBinding) {
            super(itemTransactionBinding.getRoot());
            this.binding = itemTransactionBinding;
        }

        private void setData(Transaction transaction) {
            if (transaction.getTransactionType() == 1) {
                Picasso.get().load(R.drawable.ic_money).into(binding.imgTransaction);
            }
            Picasso.get().load(R.drawable.ic_money).into(binding.imgTransaction);
            binding.titleTransaction.setText(transaction.getId());
            binding.subTitleTransaction.setText(transaction.getNote());
            binding.priceTransaction.setText(transaction.getAmount() + "");
            binding.timeTransaction.setText(transaction.getTransactionTime() + "");
            binding.getRoot().setOnClickListener(v -> {
                objectListener.onClick(transaction);
            });

        }
    }
}
