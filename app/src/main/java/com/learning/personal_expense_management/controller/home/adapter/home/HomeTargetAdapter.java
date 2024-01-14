package com.learning.personal_expense_management.controller.home.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.databinding.ItemTargetBinding;
import com.learning.personal_expense_management.model.Wallet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            NumberFormat formatCurrency = new DecimalFormat("#,###");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
            Date deadlineDate = null;
            try {
                deadlineDate = formatter.parse(wallet.getSavingsDeadline());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentDate = new Date();
            long date = (deadlineDate.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000);
            binding.titleMuctieu.setText(wallet.getWalletName());
            binding.priceMucTieu.setText(String.format("%sđ", formatCurrency.format(wallet.getGoalAmount())));


            binding.noteMucTieuPriceConLai.setText(String.format("%sđ", formatCurrency.format(wallet.getGoalAmount() - wallet.getCurrentMoney())));
            binding.noteMucTieuTimeConlai.setText(date + " ngày");
            binding.percentTv.setText((int) (wallet.getCurrentMoney()*100/wallet.getGoalAmount()) + "%");

            // them value cho progressbar
            binding.getRoot().setOnClickListener(v -> {
                objectListener.onClick(wallet);
            });
        }
    }
}
