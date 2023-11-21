package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.Category;

import java.util.List;

public interface CategoryListener {
    void onCategoryLoaded(List<Category> categories);
    void onError(String errorMessage);
}
