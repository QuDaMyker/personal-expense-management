package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.OneCategoryListener;
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
            FireStoreService.getOneCategory(transaction.getCategoryId(), new OneCategoryListener() {
                @Override
                public void getCategory(Category category) {
                    Category categoryItem = category;
                    if(categoryItem != null) {
                        Toast.makeText(context, "khc ", Toast.LENGTH_SHORT).show();
                    }

                    binding.imgTransaction.setImageResource(categoryItem.getIcon());

                    int colorIcon =  context.getResources().getColor(categoryItem.getColorIcon());
                    binding.imgTransaction.setColorFilter(categoryItem.getColorIcon());
//                    if (transaction.getTransactionType() == 1) {
//                        //Picasso.get().load(R.drawable.ic_money).into(binding.imgTransaction);
//                        //Picasso.get().load("https://images.unsplash.com/photo-1682685797088-283404e24b9d?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D").into(binding.imgTransaction);
//                        binding.imgTransaction.setImageResource(R.drawable.ic_money);
//                    } else {
//                        //Picasso.get().load(R.drawable.ic_money).into(binding.imgTransaction);
//                        //Picasso.get().load("https://images.unsplash.com/photo-1682685797088-283404e24b9d?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D").into(binding.imgTransaction);
//                        binding.imgTransaction.setImageResource(R.drawable.ic_cash);
//                    }

                    if (transaction.isFuture()) {
                        binding.timeTransaction.setVisibility(View.GONE);
                        binding.cvFuture.setVisibility(View.VISIBLE);

                    } else {
                        binding.cvFuture.setVisibility(View.GONE);
                        binding.timeTransaction.setVisibility(View.VISIBLE);
                        binding.timeTransaction.setText(transaction.getTransactionTime() + "");
                    }
                    binding.categoryTransaction.setText(category.getName());
                    binding.subTitleTransaction.setText(transaction.getNote());
                    binding.priceTransaction.setText(transaction.getAmount() + "");

                    binding.getRoot().setOnClickListener(v -> {
                        objectListener.onClick(transaction);
                    });
                }
            });
        }
    }
}
