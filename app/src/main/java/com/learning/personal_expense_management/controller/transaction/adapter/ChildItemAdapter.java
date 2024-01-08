package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.Category;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.CategoryListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.OneCategoryListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ViewHolder> {
    private List<Transaction> list;
    private ObjectListener objectListener;
    private Context context;

    public ChildItemAdapter(List<Transaction> list, ObjectListener objectListener, Context context) {
        this.list = list;
        this.objectListener = objectListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildItemAdapter.ViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildItemAdapter.ViewHolder holder, int position) {
        Transaction transaction = list.get(position);
        Log.d("infotmation transaction", transaction.toString());
        holder.setData(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTransactionBinding binding;

        public ViewHolder(@NonNull ItemTransactionBinding itemTransactionBinding) {
            super(itemTransactionBinding.getRoot());
            binding = itemTransactionBinding;
        }

        private void setData(Transaction transaction) {
            Log.d("transaction", transaction.getCategoryId().toString());

            FireStoreService.getOneCategory(transaction.getCategoryId(), new OneCategoryListener() {
                @Override
                public void getCategory(Category category) {
                    Category categoryItem = category;
                    NumberFormat formatter = new DecimalFormat("#,###");
                    binding.imgTransaction.setImageResource(categoryItem.getIcon());

                    int colorIcon =  context.getResources().getColor(categoryItem.getColorIcon());
                    binding.imgTransaction.setColorFilter(categoryItem.getColorIcon());

                    if (transaction.isFuture()) {
                        binding.rootView.setCardBackgroundColor(R.drawable.background_primary40_gradient);
                        binding.timeTransaction.setVisibility(View.GONE);
                        binding.cvFuture.setVisibility(View.VISIBLE);
                    } else {
                        binding.cvFuture.setVisibility(View.GONE);
                        binding.timeTransaction.setVisibility(View.VISIBLE);
                        binding.timeTransaction.setText(transaction.getTransactionTime() + "");
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
