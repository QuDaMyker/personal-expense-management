package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.Transaction;

import java.util.List;

public interface TransactionListener {
    void onTransactionsLoaded(List<Transaction> transactions);

    void onError(String errorMessage);

}
