package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.Loan;

import java.util.List;

public interface LoanListener {
    void onLoanLoaded(List<Loan> loans);
    void onError(String errorMessage);
}
