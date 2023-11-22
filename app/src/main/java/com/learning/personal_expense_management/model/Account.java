package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

public class Account {
    private String ownerId;
    private String id;
    private String accountType;
    private String cardName;
    private String cardNumber;
    private String expirationDate;
    private int currentBalance;

    public Account(){

    }
    public Account(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.accountType = document.getString("accountType");
        this.cardName = document.getString("cardName");
        this.cardNumber = document.getString("cardNumber");
        this.expirationDate = document.getString("expirationDate");
        this.currentBalance = Integer.parseInt(String.valueOf(document.getLong("currentBalance")));
    }

    @Override
    public String toString() {
        return "Account{" +
                "ownerId='" + ownerId + '\'' +
                ", id='" + id + '\'' +
                ", accountType='" + accountType + '\'' +
                ", cardName='" + cardName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", currentBalance=" + currentBalance +
                '}';
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Account(String ownerId, String id, String accountType, String cardName, String cardNumber, String expirationDate, int currentBalance) {
        this.ownerId = ownerId;
        this.id = id;
        this.accountType = accountType;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.currentBalance = currentBalance;
    }
}
