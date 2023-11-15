package com.learning.personal_expense_management.controller.adapter;

import com.learning.personal_expense_management.model.Wallet;

public interface ObjectListener<T> {
    void onClick(T t);
}
