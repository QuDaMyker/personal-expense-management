package com.learning.personal_expense_management.controller.loan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.learning.personal_expense_management.R;
import com.learning.personal_expense_management.controller.account.AccountActivity;
import com.learning.personal_expense_management.controller.account.adapter.AccountAdapter;
import com.learning.personal_expense_management.controller.loan.adapter.LoanAdapter;
import com.learning.personal_expense_management.databinding.ActivityAddAccountBinding;
import com.learning.personal_expense_management.databinding.ActivityLoanBinding;
import com.learning.personal_expense_management.model.Account;
import com.learning.personal_expense_management.model.Loan;
import com.learning.personal_expense_management.services.AccountListener;
import com.learning.personal_expense_management.services.FireStoreService;
import com.learning.personal_expense_management.services.LoanListener;
import com.learning.personal_expense_management.utilities.custom_dialog.ChooseAccountDialog;
import com.learning.personal_expense_management.utilities.custom_dialog.UpdateLoanDialog;

import java.util.ArrayList;
import java.util.List;


public class LoanActivity extends AppCompatActivity {
    private ActivityLoanBinding binding;
    public RecyclerView recyclerView;
    public LoanAdapter loanAdapter;
    public List<Loan> loanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.loan_recycler_view);

        FireStoreService.getBorrowLoan(FirebaseAuth.getInstance().getUid(), new LoanListener() {
            @Override
            public void onLoanLoaded(List<Loan> loans) {
                loanList = loans;
                loanAdapter = new LoanAdapter(loanList);
                loanAdapter.setOnBtnClickListener(new LoanAdapter.OnBtnClickListener() {
                    @Override
                    public void onBtnClick(Loan loan) {
                        UpdateLoanDialog dialog = new UpdateLoanDialog(LoanActivity.this, loan);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                    }
                });
                loanAdapter.setOnItemClickListener(new LoanAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Loan loan) {
                        Intent intent = new Intent(LoanActivity.this, LoanDetailActivity.class);
                        intent.putExtra("selected_item", loan);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(loanAdapter);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        binding.newLoanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoanActivity.this, NewLoanActivity.class));
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.typeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == binding.lendRadioBtn.getId()){
                    FireStoreService.getLendLoan(FirebaseAuth.getInstance().getUid(), new LoanListener() {
                        @Override
                        public void onLoanLoaded(List<Loan> loans) {
                            loanList = loans;
                            loanAdapter = new LoanAdapter(loanList);
                            loanAdapter.setOnBtnClickListener(new LoanAdapter.OnBtnClickListener() {
                                @Override
                                public void onBtnClick(Loan loan) {
                                    UpdateLoanDialog dialog = new UpdateLoanDialog(LoanActivity.this, loan);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();
                                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().setGravity(Gravity.CENTER);
                                }
                            });
                            loanAdapter.setOnItemClickListener(new LoanAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Loan loan) {
                                    Intent intent = new Intent(LoanActivity.this, LoanDetailActivity.class);
                                    intent.putExtra("selected_item", loan);
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(loanAdapter);
                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    });
                }
                else {
                    FireStoreService.getBorrowLoan(FirebaseAuth.getInstance().getUid(), new LoanListener() {
                        @Override
                        public void onLoanLoaded(List<Loan> loans) {
                            loanList = loans;
                            loanAdapter = new LoanAdapter(loanList);
                            recyclerView.setAdapter(loanAdapter);
                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    });
                }
            }
        });
    }
}