package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class Loan {
    private String id;
    private String borrowerName;
    private int amount;
    private String note;
    private DateTime transactionDate;
    private DateTime transactionTime;
    private int repaymentPeriod;

    public Loan(String id, String borrowerName, int amount, String note, DateTime transactionDate, DateTime transactionTime, int repaymentPeriod) {
        this.id = id;
        this.borrowerName = borrowerName;
        this.amount = amount;
        this.note = note;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.repaymentPeriod = repaymentPeriod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public DateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(DateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public DateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(DateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionTime=" + transactionTime +
                ", repaymentPeriod=" + repaymentPeriod +
                '}';
    }
}
