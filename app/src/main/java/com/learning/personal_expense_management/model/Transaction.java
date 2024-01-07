package com.learning.personal_expense_management.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String ownerId;
    private String id;
    private int transactionType;
    private int amount;
    private String note;
    private String transactionDate;
    private String transactionTime;
    private String sourceAccount;
    private String destinationAccount;
    private String categoryId;
    private Timestamp timeStamp;
    private String month;
    private String year;
    private boolean isFuture;


    public Transaction(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.transactionType = Integer.parseInt(String.valueOf(document.getLong("transactionType")));
        this.amount = Integer.parseInt(String.valueOf(document.getLong("amount")));
        this.note = document.getString("note");
        this.transactionDate = document.getString("transactionDate");
        this.transactionTime = document.getString("transactionTime");
        this.sourceAccount = document.getString("sourceAccount");
        this.destinationAccount = document.getString("destinationAccount");
        this.categoryId = document.getString("categoryId");
        this.timeStamp = document.getTimestamp("timeStamp");
        this.month = document.getString("month");
        this.year = document.getString("year");
        this.isFuture = document.getBoolean("isFuture");
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
                ", categoryId='" + categoryId + '\'' +
                ", timeStamp=" + timeStamp +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", isFuture='" + isFuture + '\'' +
                '}';
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isFuture() {
        return isFuture;
    }

    public void setFuture(boolean future) {
        isFuture = future;
    }

    public Transaction(String ownerId, String id, int transactionType, int amount, String note, String transactionDate, String transactionTime, String sourceAccount, String destinationAccount, String categoryId, Timestamp timeStamp, boolean isFuture) {
        this.ownerId = ownerId;
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.note = note;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.categoryId = categoryId;
        this.timeStamp = timeStamp;
        this.month = this.transactionDate.split("/")[1];
        this.year = this.transactionDate.split("/")[2];
        this.isFuture = isFuture;
    }


}

