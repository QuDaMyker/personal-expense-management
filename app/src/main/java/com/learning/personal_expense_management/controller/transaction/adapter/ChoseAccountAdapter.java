package com.learning.personal_expense_management.controller.transaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ItemAccountBinding;
import com.learning.personal_expense_management.model.Account;

import java.util.List;

public class ChoseAccountAdapter extends RecyclerView.Adapter<ChoseAccountAdapter.ViewHolder> {
    private Context context;
    private List<Account> list;
    private ObjectListener listener;

    public ChoseAccountAdapter(Context context, List<Account> list, ObjectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void setList(List<Account> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChoseAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoseAccountAdapter.ViewHolder(ItemAccountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChoseAccountAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAccountBinding binding;

        public ViewHolder(ItemAccountBinding itemAccountBinding) {
            super(itemAccountBinding.getRoot());
            this.binding = itemAccountBinding;
        }

        private void setData(Account account) {
            binding.nameAccount.setText(account.getCardName());
            switch (account.getAccountType()) {
                case "Tiền mặt":
                    binding.imgAccount.setImageResource(R.drawable.ic_money_2);
                    break;
                case "Thẻ Visa":
                    binding.imgAccount.setImageResource(R.drawable.visa_logo);
                    break;
                case "Thẻ Mastercard":
                    binding.imgAccount.setImageResource(R.drawable.mastercard_logo);
                    break;
                case "Thẻ JCB":
                    binding.imgAccount.setImageResource(R.drawable.jcb_logo);
                    break;
                case "Ví điện tử MoMo":
                    binding.imgAccount.setImageResource(R.drawable.momo_logo);
                    break;
                case "Ví điện tử ShopeePay":
                    binding.imgAccount.setImageResource(R.drawable.shopeepay_logo);
                    break;
                case "Ví điện tử ZaloPay":
                    binding.imgAccount.setImageResource(R.drawable.zalopay_logo);
                    break;
                case "Ví điện tử khác":
                    binding.imgAccount.setImageResource(R.drawable.ic_ewallet);
                    break;
                case "Thẻ ngân hàng":
                    binding.imgAccount.setImageResource(R.drawable.ic_credit_card);
                    break;
                default:
                    break;
            }

            binding.getRoot().setOnClickListener(v -> {
                listener.onClick(account);
            });
        }

    }
}
