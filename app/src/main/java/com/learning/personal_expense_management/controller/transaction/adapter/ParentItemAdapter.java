package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.databinding.ItemParentItemShowingTransactionBinding;
import com.learning.personal_expense_management.model.ParentItemTransaction;
import com.learning.personal_expense_management.model.Transaction;

import java.util.List;

public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ViewHolder> {
    private Context context;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ParentItemTransaction> list;
    private ObjectListener objectListener;

    public ParentItemAdapter(Context context, List<ParentItemTransaction> list, ObjectListener objectListener) {
        this.context = context;
        this.list = list;
        this.objectListener = objectListener;
    }

    @NonNull
    @Override
    public ParentItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ParentItemAdapter.ViewHolder(ItemParentItemShowingTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentItemAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemParentItemShowingTransactionBinding binding;

        public ViewHolder(@NonNull ItemParentItemShowingTransactionBinding itemParentItemShowingTransactionBinding) {
            super(itemParentItemShowingTransactionBinding.getRoot());
            this.binding = itemParentItemShowingTransactionBinding;
        }

        private void setData(ParentItemTransaction parentItemTransaction) {
            binding.parentItemDate.setText(parentItemTransaction.getDate());
            Log.d("date", parentItemTransaction.getDate());
            LinearLayoutManager layoutManager = new LinearLayoutManager(binding.childRecyclerview.getContext());
            layoutManager.setInitialPrefetchItemCount(parentItemTransaction.getListTransaction().size());
            ChildItemAdapter childItemAdapter = new ChildItemAdapter(parentItemTransaction.getListTransaction(), objectListener);

            binding.childRecyclerview.setLayoutManager(layoutManager);
            binding.childRecyclerview.setAdapter(childItemAdapter);
            childItemAdapter.notifyDataSetChanged();
            binding.childRecyclerview.setRecycledViewPool(viewPool);

        }
    }
}
