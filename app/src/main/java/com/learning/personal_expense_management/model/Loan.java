package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

public class Loan {
    private String id;
    private String ownerId;
    private String borrowerName;
    private int amount;
    private String note;
    private String transactionDate;
    private String transactionTime;
    private int repaymentPeriod;

    public Loan(QueryDocumentSnapshot document) {
        this.id = document.getString("id");
        this.ownerId = document.getString("ownerId");
        this.borrowerName = document.getString("borrowerName");
        this.amount = Integer.parseInt(String.valueOf(document.getLong("amount")));
        this.note = document.getString("note");
        this.transactionDate = document.getString("transactionDate");
        this.transactionTime = document.getString("transactionTime");
        this.repaymentPeriod = Integer.parseInt(String.valueOf(document.getString("repaymentPeriod")));
    }

    @Override
    public String toString() {
        return "Loan{" + "id='" + id + '\'' + ", ownerId='" + ownerId + '\'' + ", borrowerName='" + borrowerName + '\'' + ", amount=" + amount + ", note='" + note + '\'' + ", transactionDate='" + transactionDate + '\'' + ", transactionTime='" + transactionTime + '\'' + ", repaymentPeriod=" + repaymentPeriod + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public Loan(String id, String ownerId, String borrowerName, int amount, String note, String transactionDate, String transactionTime, int repaymentPeriod) {
        this.id = id;
        this.ownerId = ownerId;
        this.borrowerName = borrowerName;
        this.amount = amount;
        this.note = note;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.repaymentPeriod = repaymentPeriod;
    }
}
