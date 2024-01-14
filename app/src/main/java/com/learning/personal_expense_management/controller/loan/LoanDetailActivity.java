package com.learning.personal_expense_management.controller.loan;

import static com.learning.personal_expense_management.controller.loan.adapter.LoanAdapter.roundDouble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.transaction.adapter.ChildItemAdapter;
import com.learning.personal_expense_management.controller.transaction.adapter.ObjectListener;
import com.learning.personal_expense_management.controller.transaction.adapter.ParentItemAdapter;
import com.learning.personal_expense_management.controller.transaction.adapter.TransactionAdapter;
import com.learning.personal_expense_management.databinding.ActivityLoanDetailBinding;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.model.Transaction;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.TransactionListener;
import com.learning.personal_expense_management.utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanDetailActivity extends AppCompatActivity {

    private ActivityLoanDetailBinding binding;
    private Loan loan;
    Map<Integer, String> periodMap = new HashMap<>();

    ChildItemAdapter childItemAdapter;
    List<Transaction> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        periodMap.put(1, "1 tháng");
        periodMap.put(2, "2 tháng");
        periodMap.put(3, "3 tháng");
        periodMap.put(4, "4 tháng");
        periodMap.put(5, "5 tháng");
        periodMap.put(6, "6 tháng");
        periodMap.put(7, "7 tháng");
        periodMap.put(8, "8 tháng");
        periodMap.put(9, "9 tháng");
        periodMap.put(10, "10 tháng");
        periodMap.put(11, "11 tháng");
        periodMap.put(12, "1 năm");
        periodMap.put(24, "2 năm");

        loan = (Loan) getIntent().getSerializableExtra("selected_item");

        childItemAdapter = new ChildItemAdapter(transactionList, new ObjectListener() {
            @Override
            public void onClick(Object o) {

            }
        }, getBaseContext());

        binding.rvTransaction.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.rvTransaction.setAdapter(childItemAdapter);

        getTransactions();

        initData();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void initData(){
        binding.noteTv.setText(loan.getNote());
        binding.paidTv.setText(loan.getPaid() + "₫");

        int totalAmount = (int) (loan.getAmount() + loan.getInterest());

        binding.amountTotalTv.setText("/" + totalAmount + "₫");
        if(loan.isHasInterestRate()){
            binding.principalTv.setText(loan.getAmount() + "₫");
            binding.principalLayout.setVisibility(View.VISIBLE);
            binding.interestLayout.setVisibility(View.VISIBLE);
        }
        else{
            binding.principalLayout.setVisibility(View.GONE);
            binding.interestLayout.setVisibility(View.GONE);
        }
        double percent = loan.getPaid()*100.0/totalAmount;
        binding.percentTv.setText(roundDouble(percent) + "%");
        float factor = this.getBaseContext().getResources().getDisplayMetrics().density;
        binding.paidLine.setLayoutParams(new FrameLayout.LayoutParams((int) (270*factor*percent/100), (int)(12*factor)));
        binding.borrowerNameTv.setText(loan.getBorrowerName());
        binding.typeTv.setText(loan.isLend() ? "Cho vay" : "Vay");
        binding.dateTv.setText(Utils.convertTimestampToDateString(Long.parseLong(loan.getTimestamp())));
        binding.timeTv.setText(Utils.convertTimestampToTimeString(Long.parseLong(loan.getTimestamp())));
        binding.deadlineTv.setText(loan.getDeadline());

        binding.interestRateTv.setText(loan.getInterestRate() + "%/năm");
        binding.interestRateTypeTv.setText(loan.isInterestRateType() ? "Theo dư nợ gốc" : "Theo dư nợ giảm dần");
        binding.periodTv.setText(periodMap.get(loan.getRepaymentPeriod()));
    }

    void getTransactions(){
        List<String> ids = new ArrayList<>();
        ids.addAll(loan.getPredictTransactions());
        ids.add(loan.getInitialTransaction());
        ids.addAll(loan.getReturnTransactions());

        FireStoreService.getTransactionsById(ids, new TransactionListener() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                childItemAdapter = new ChildItemAdapter(transactions, new ObjectListener() {
                    @Override
                    public void onClick(Object o) {

                    }
                }, getBaseContext());

                binding.rvTransaction.setAdapter(childItemAdapter);
                childItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(LoanDetailActivity.this, "ERROR: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
}