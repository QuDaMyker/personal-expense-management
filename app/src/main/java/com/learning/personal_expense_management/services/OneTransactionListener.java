package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.Transaction;

public interface OneTransactionListener {
    void getTransaction(Transaction transaction);
}
