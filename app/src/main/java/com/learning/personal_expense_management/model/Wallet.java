package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

public class Wallet {
    private String ownerId;
    private String id;
    private String walletName;
    private boolean lowBalanceAlert;
    private int minimumBalance;
    private boolean goalSavingsEnabled;
    private int goalAmount;
    private String savingsDeadline;
    private int frequency;

    public Wallet(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.walletName = document.getString("walletName");
        this.lowBalanceAlert = Boolean.parseBoolean(document.getString("lowBalanceAlert"));
        this.minimumBalance = Integer.parseInt(document.getString("minimumBalance"));
        this.goalSavingsEnabled = Boolean.parseBoolean(document.getString("goalSavingsEnabled"));
        this.goalAmount = Integer.parseInt(document.getString("goalAmount"));
        this.savingsDeadline = document.getString("savingsDeadline");
        this.frequency = Integer.parseInt(document.getString("frequency"));
    }

    public Wallet(String ownerId, String id, String walletName, boolean lowBalanceAlert, int minimumBalance, boolean goalSavingsEnabled, int goalAmount, String savingsDeadline, int frequency) {
        this.ownerId = ownerId;
        this.id = id;
        this.walletName = walletName;
        this.lowBalanceAlert = lowBalanceAlert;
        this.minimumBalance = minimumBalance;
        this.goalSavingsEnabled = goalSavingsEnabled;
        this.goalAmount = goalAmount;
        this.savingsDeadline = savingsDeadline;
        this.frequency = frequency;
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

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public boolean isLowBalanceAlert() {
        return lowBalanceAlert;
    }

    public void setLowBalanceAlert(boolean lowBalanceAlert) {
        this.lowBalanceAlert = lowBalanceAlert;
    }

    public int getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(int minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public boolean isGoalSavingsEnabled() {
        return goalSavingsEnabled;
    }

    public void setGoalSavingsEnabled(boolean goalSavingsEnabled) {
        this.goalSavingsEnabled = goalSavingsEnabled;
    }

    public int getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(int goalAmount) {
        this.goalAmount = goalAmount;
    }

    public String getSavingsDeadline() {
        return savingsDeadline;
    }

    public void setSavingsDeadline(String savingsDeadline) {
        this.savingsDeadline = savingsDeadline;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "ownerId='" + ownerId + '\'' +
                ", id='" + id + '\'' +
                ", walletName='" + walletName + '\'' +
                ", lowBalanceAlert=" + lowBalanceAlert +
                ", minimumBalance=" + minimumBalance +
                ", goalSavingsEnabled=" + goalSavingsEnabled +
                ", goalAmount=" + goalAmount +
                ", savingsDeadline='" + savingsDeadline + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
