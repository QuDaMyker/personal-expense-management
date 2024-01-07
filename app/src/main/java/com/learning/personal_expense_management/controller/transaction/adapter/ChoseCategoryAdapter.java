package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.controller.CategoryAdapter;
import com.learning.personal_expense_management.databinding.ItemCategoryBinding;
import com.learning.personal_expense_management.databinding.ItemTransactionBinding;
import com.learning.personal_expense_management.model.Category;

import java.util.List;

public class ChoseCategoryAdapter extends RecyclerView.Adapter<ChoseCategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private ObjectListener listener;

    public ChoseCategoryAdapter(Context context, List<Category> categoryList, ObjectListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    public void setList(List<Category> list) {
        this.categoryList = list;
    }

    @NonNull
    @Override
    public ChoseCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoseCategoryAdapter.ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChoseCategoryAdapter.ViewHolder holder, int position) {
        holder.setData(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            this.binding = itemCategoryBinding;
        }

        private void setData(Category category) {
            int backgroundColor = context.getResources().getColor(category.getBackGround());
            binding.backGround.setCardBackgroundColor(backgroundColor);
            binding.icon.setImageResource(category.getIcon());
            int colorIcon = context.getResources().getColor(category.getColorIcon());
            binding.icon.setColorFilter(colorIcon);
            binding.title.setText(category.getName());
            binding.getRoot().setOnClickListener(v -> {
                listener.onClick(category);
            });
        }
    }
}
