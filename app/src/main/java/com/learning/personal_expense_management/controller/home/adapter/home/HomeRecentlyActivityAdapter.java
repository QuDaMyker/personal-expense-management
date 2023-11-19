package com.learning.personal_expense_management.controller.home.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.Transaction;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecentlyActivityAdapter extends RecyclerView.Adapter<HomeRecentlyActivityAdapter.ViewHolder> {
    private Context context;
    private List<Transaction> list;
    private ObjectListener objectListener;

    public HomeRecentlyActivityAdapter(Context context, List<Transaction> list, ObjectListener objectListener) {
        this.context = context;
        this.list = list;
        this.objectListener = objectListener;
    }

    @NonNull
    @Override
    public HomeRecentlyActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecentlyActivityAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTransactionBinding binding;

        public ViewHolder(ItemTransactionBinding itemTransactionBinding) {
            super(itemTransactionBinding.getRoot());
            binding = itemTransactionBinding;
        }

        private void setData(Transaction transaction) {
            if (transaction.getTransactionType() == 1) {
                //Picasso.get().load(R.drawable.ic_money).into(binding.imgTransaction);
                //Picasso.get().load("https://images.unsplash.com/photo-1682685797088-283404e24b9d?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D").into(binding.imgTransaction);
                binding.imgTransaction.setImageResource(R.drawable.ic_money);
            } else {
                //Picasso.get().load(R.drawable.ic_money).into(binding.imgTransaction);
                //Picasso.get().load("https://images.unsplash.com/photo-1682685797088-283404e24b9d?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D").into(binding.imgTransaction);
                binding.imgTransaction.setImageResource(R.drawable.ic_cash);
            }
            //binding.rootView.setCardBackgroundColor(Color.parseColor("white"));
            binding.titleTransaction.setText(transaction.getId());
            binding.subTitleTransaction.setText(transaction.getNote());
            binding.priceTransaction.setText(transaction.getAmount() + "");
            binding.timeTransaction.setText(transaction.getTransactionTime() + " " + transaction.getTransactionDate());
            binding.getRoot().setOnClickListener(v -> {
                objectListener.onClick(transaction);
            });

        }
    }
}
