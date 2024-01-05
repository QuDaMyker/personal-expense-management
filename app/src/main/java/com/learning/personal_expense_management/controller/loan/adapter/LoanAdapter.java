package com.learning.personal_expense_management.controller.loan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Loan;

import java.text.DecimalFormat;
import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {
    private List<Loan> loans;

    public LoanAdapter(List<Loan> loans){
        this.loans = loans;
    }

    @NonNull
    @Override
    public LoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loan, parent, false);

        return new LoanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanAdapter.ViewHolder holder, int position) {
        holder.getBorrowerNameTv().setText(loans.get(position).getBorrowerName());
        holder.getPaidTv().setText("100000₫");
        holder.getAmountTotalTv().setText("/" + loans.get(position).getAmount() + "₫");
        double percent = 100000*100.0/loans.get(position).getAmount();
        holder.getPercentTv().setText(roundDouble(percent) + "%");
        holder.getDeadlineTv().setText(loans.get(position).getDeadline());
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView borrowerNameTv;
        private final TextView paidTv;
        private final TextView amountTotalTv;
        private final TextView percentTv;
        private final TextView deadlineTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            borrowerNameTv = (TextView) itemView.findViewById(R.id.borrowerNameTv);
            paidTv = (TextView) itemView.findViewById(R.id.paidTv);
            amountTotalTv = (TextView) itemView.findViewById(R.id.amountTotalTv);
            percentTv = (TextView) itemView.findViewById(R.id.percentTv);
            deadlineTv = (TextView) itemView.findViewById(R.id.deadlineTv);
        }

        public TextView getBorrowerNameTv() {
            return borrowerNameTv;
        }

        public TextView getPaidTv() {
            return paidTv;
        }

        public TextView getAmountTotalTv() {
            return amountTotalTv;
        }

        public TextView getPercentTv() {
            return percentTv;
        }

        public TextView getDeadlineTv() {
            return deadlineTv;
        }
    }

    public static String roundDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }
}
