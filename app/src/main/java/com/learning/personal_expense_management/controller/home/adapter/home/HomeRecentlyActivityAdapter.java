package com.learning.personal_expense_management.controller.home.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.OneCategoryListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
            NumberFormat formatter = new DecimalFormat("#,###");
            if (transaction.getCategoryId().contains("getId")) {
                binding.imgTransaction.setImageResource(R.drawable.ic_cash);
                binding.timeTransaction.setVisibility(View.VISIBLE);
                binding.timeTransaction.setText(transaction.getTransactionTime() + " " + transaction.getTransactionDate());
                binding.categoryTransaction.setText("Chuyển khoản");
                binding.subTitleTransaction.setText(transaction.getNote());
                binding.priceTransaction.setText(String.format("%sđ", formatter.format(transaction.getAmount())));

                binding.getRoot().setOnClickListener(v -> {
                    objectListener.onClick(transaction);
                });
            } else {
                FireStoreService.getOneCategory(transaction.getCategoryId(), new OneCategoryListener() {
                    @Override
                    public void getCategory(Category category) {
                        Category categoryItem = category;

                        binding.imgTransaction.setImageResource(categoryItem.getIcon());

                        int colorIcon = context.getResources().getColor(categoryItem.getColorIcon());
                        binding.imgTransaction.setColorFilter(categoryItem.getColorIcon());

                        if (transaction.isFuture()) {
                            binding.clLayout.setBackgroundResource(R.drawable.background_primary40_gradient);
                            binding.timeTransaction.setVisibility(View.GONE);
                            binding.cvFuture.setVisibility(View.VISIBLE);

                        } else {
                            binding.cvFuture.setVisibility(View.GONE);
                            binding.timeTransaction.setVisibility(View.VISIBLE);
                            binding.timeTransaction.setText(transaction.getTransactionTime() + " " + transaction.getTransactionDate());
                        }
                        int backgroundColor = context.getResources().getColor(category.getBackGround());
                        binding.backGround.setCardBackgroundColor(backgroundColor);
                        binding.categoryTransaction.setText(category.getName());
                        binding.subTitleTransaction.setText(transaction.getNote());
                        binding.priceTransaction.setText(String.format("%sđ", formatter.format(transaction.getAmount())));

                        binding.getRoot().setOnClickListener(v -> {
                            objectListener.onClick(transaction);
                        });
                    }
                });
            }


        }
    }
}
