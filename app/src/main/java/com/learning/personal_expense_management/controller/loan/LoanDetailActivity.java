package com.learning.personal_expense_management.controller.loan;

import static com.learning.personal_expense_management.controller.loan.adapter.LoanAdapter.roundDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.databinding.ActivityLoanDetailBinding;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.utilities.Utils;

import java.util.HashMap;
import java.util.Map;

public class LoanDetailActivity extends AppCompatActivity {

    private ActivityLoanDetailBinding binding;
    private Loan loan;
    Map<Integer, String> periodMap = new HashMap<>();

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
        binding.paidTv.setText("1000000₫");
        binding.amountTotalTv.setText("/" + loan.getAmount() + "₫");
        double percent = 100000*100.0/loan.getAmount();
        binding.percentTv.setText(roundDouble(percent) + "%");

        binding.borrowerNameTv.setText(loan.getBorrowerName());
        binding.typeTv.setText(loan.isLend() ? "Cho vay" : "Vay");
        binding.dateTv.setText(Utils.convertTimestampToDateString(Long.parseLong(loan.getTimestamp())));
        binding.timeTv.setText(Utils.convertTimestampToTimeString(Long.parseLong(loan.getTimestamp())));
        binding.deadlineTv.setText(loan.getDeadline());

        binding.interestRateTv.setText(loan.getInterestRate() + "%");
        binding.interestRateTypeTv.setText(loan.isInterestRateType() ? "Theo dư nợ gốc" : "Theo dư nợ giảm dần");
        binding.periodTv.setText(periodMap.get(loan.getRepaymentPeriod()));
    }
}