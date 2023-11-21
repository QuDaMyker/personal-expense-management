package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.Wallet;

import java.util.List;

public interface WalletListener {
    void onWalletsLoaded(List<Wallet> wallets);
    void onError(String errorMessage);
}
