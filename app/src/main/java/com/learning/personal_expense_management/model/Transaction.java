package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class Transaction {
    private String ownerId;
    private String id;
    private int transactionType;
    private int amount;
    private String note;
    private String transactionDate;
    private String transactionTime;
    private String sourceAccount;
    private String destinationAccount;

    public Transaction(String ownerId, String id, int transactionType, int amount, String note, String transactionDate, String transactionTime, String sourceAccount, String destinationAccount) {
        this.ownerId = ownerId;
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.note = note;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
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

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "ownerId='" + ownerId + '\'' +
                ", id='" + id + '\'' +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", destinationAccount='" + destinationAccount + '\'' +
                '}';
    }
}

