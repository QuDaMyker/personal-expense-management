package com.learning.personal_expense_management.controller.account.adapter;


import static com.learning.personal_expense_management.utilities.Enum.AccountType.Cash;
import static com.learning.personal_expense_management.utilities.Enum.AccountType.Visa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.utilities.Enum;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<Account> accounts;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameAccount;
        private final ImageView imgAccount;

        public ViewHolder(View view) {
            super(view);

            nameAccount = (TextView) view.findViewById(R.id.name_account);
            imgAccount = (ImageView) view.findViewById(R.id.img_account);
        }

        public TextView getNameAccount() {
            return nameAccount;
        }

        public ImageView getImgAccount(){
            return imgAccount;
        }
    }

    public AccountAdapter(List<Account> dataSet) {
        accounts = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_account, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getNameAccount().setText(accounts.get(position).getCardName().isEmpty() ?
                accounts.get(position).getAccountType() :
                accounts.get(position).getCardName());
        switch (accounts.get(position).getAccountType()){
            case "Tiền mặt":
                viewHolder.getImgAccount().setImageResource(R.drawable.ic_money_2);
                break;
            case "Thẻ Visa":
                viewHolder.getImgAccount().setImageResource(R.drawable.visa_logo);
                break;
            case "Thẻ Mastercard":
                viewHolder.getImgAccount().setImageResource(R.drawable.mastercard_logo);
                break;
            case "Thẻ JCB":
                viewHolder.getImgAccount().setImageResource(R.drawable.jcb_logo);
                break;
            case "Ví điện tử MoMo":
                viewHolder.getImgAccount().setImageResource(R.drawable.momo_logo);
                break;
            case "Ví điện tử ShopeePay":
                viewHolder.getImgAccount().setImageResource(R.drawable.shopeepay_logo);
                break;
            case "Ví điện tử ZaloPay":
                viewHolder.getImgAccount().setImageResource(R.drawable.zalopay_logo);
                break;
            case "Ví điện tử khác":
                viewHolder.getImgAccount().setImageResource(R.drawable.ic_ewallet);
                break;
            case "Thẻ ngân hàng":
                viewHolder.getImgAccount().setImageResource(R.drawable.ic_credit_card);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
}

