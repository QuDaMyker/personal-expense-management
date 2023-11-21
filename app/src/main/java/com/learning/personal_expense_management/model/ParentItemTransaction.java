package com.learning.personal_expense_management.model;

import java.util.List;

public class ParentItemTransaction {
    private String date;
    private List<Transaction> listTransaction;

    public ParentItemTransaction(String date, List<Transaction> listTransaction) {
        this.date = date;
        this.listTransaction = listTransaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Transaction> getListTransaction() {
        return listTransaction;
    }

    public void setListTransaction(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    @Override
    public String toString() {
        return "ParentItemTransaction{" +
                "date='" + date + '\'' +
                ", listTransaction=" + listTransaction +
                '}';
    }
}
