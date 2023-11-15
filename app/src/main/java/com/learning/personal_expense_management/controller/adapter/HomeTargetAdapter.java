package com.learning.personal_expense_management.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.databinding.ItemTargetBinding;
import com.learning.personal_expense_management.model.Wallet;

import java.util.List;

public class HomeTargetAdapter extends RecyclerView.Adapter<HomeTargetAdapter.ViewHolder> {
    private Context context;
    private List<Wallet> listTarget;
    private final ObjectListener objectListener;

    public HomeTargetAdapter(Context context, List<Wallet> listTarget, ObjectListener objectListener) {
        this.context = context;
        this.listTarget = listTarget;
        this.objectListener = objectListener;
    }

    @NonNull
    @Override
    public HomeTargetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTargetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTargetAdapter.ViewHolder holder, int position) {
        holder.setData(listTarget.get(position));
    }

    @Override
    public int getItemCount() {
        return listTarget == null ? 0 : listTarget.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTargetBinding binding;

        public ViewHolder(ItemTargetBinding itemTargetBinding) {
            super(itemTargetBinding.getRoot());
            binding = itemTargetBinding;
        }

        private void setData(Wallet wallet) {
            binding.titleMuctieu.setText(wallet.getWalletName());
            binding.priceMucTieu.setText(wallet.getMinimumBalance());
            binding.noteMucTieuPriceConLai.setText(wallet.getGoalAmount());
            binding.noteMucTieuTimeConlai.setText(wallet.getSavingsDeadline().toString());

            // them value cho progressbar
            binding.getRoot().setOnClickListener(v -> {
                objectListener.onClick(wallet);
            });
        }
    }
}
