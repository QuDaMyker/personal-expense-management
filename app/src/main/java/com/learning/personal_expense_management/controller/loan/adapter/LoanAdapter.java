package com.learning.personal_expense_management.controller.loan.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Loan;

import java.text.DecimalFormat;
import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {
    private List<Loan> loans;

    public interface OnBtnClickListener {
        void onBtnClick(Loan loan);
    }
    private LoanAdapter.OnBtnClickListener onBtnClickListener;

    public void setOnBtnClickListener(LoanAdapter.OnBtnClickListener listener) {
        this.onBtnClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Loan loan);
    }
    private LoanAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(LoanAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getBorrowerNameTv().setText(loans.get(position).getBorrowerName());
        holder.getPaidTv().setText(loans.get(position).getPaid() + "₫");
        int totalAmount = (int) (loans.get(position).getAmount() + loans.get(position).getInterest());
        holder.getAmountTotalTv().setText("/" + totalAmount + "₫");

        double percent = loans.get(position).getPaid()*100.0/totalAmount;
        holder.getPercentTv().setText(roundDouble(percent) + "%");
        float factor = Resources.getSystem().getDisplayMetrics().density;
        holder.getPaidLine().setLayoutParams(new FrameLayout.LayoutParams((int) (230*factor*percent/100), (int)(12*factor)));

        holder.getDeadlineTv().setText(loans.get(position).getDeadline());

        holder.getUpdateBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClickListener.onBtnClick(loans.get(position));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(loans.get(position));
            }
        });

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
        private final MaterialButton updateBtn;
        private final View paidLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            borrowerNameTv = (TextView) itemView.findViewById(R.id.borrowerNameTv);
            paidTv = (TextView) itemView.findViewById(R.id.paidTv);
            amountTotalTv = (TextView) itemView.findViewById(R.id.amountTotalTv);
            percentTv = (TextView) itemView.findViewById(R.id.percentTv);
            deadlineTv = (TextView) itemView.findViewById(R.id.deadlineTv);
            updateBtn = (MaterialButton) itemView.findViewById(R.id.updateBtn);
            paidLine = (View) itemView.findViewById(R.id.paidLine);
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

        public MaterialButton getUpdateBtn() {
            return updateBtn;
        }

        public View getPaidLine() {
            return paidLine;
        }
    }

    public static String roundDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }
}
