package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.Account;

import java.util.List;

public interface AccountListener {
    void onAccountsLoaded(List<Account> accounts);
    void onError(String errorMessage);
}
