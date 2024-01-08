package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

import java.io.Serializable;

public class Wallet implements Serializable {
    private String ownerId;
    private String id;
    private String walletName;
    private boolean lowBalanceAlert;
    private int minimumBalance;
    private boolean goalSavingsEnabled;
    private int goalAmount;
    private String savingsDeadline;
    private int frequency;
    private int currentMoney;

    public Wallet(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.walletName = document.getString("walletName");
        this.lowBalanceAlert = document.getBoolean("lowBalanceAlert");
        this.minimumBalance = Integer.parseInt(String.valueOf(document.getLong("minimumBalance")));
        this.goalSavingsEnabled = document.getBoolean("goalSavingsEnabled");
        this.goalAmount = Integer.parseInt(String.valueOf(document.getLong("goalAmount")));
        this.savingsDeadline = document.getString("savingsDeadline");
        this.frequency = Integer.parseInt(String.valueOf(document.getLong("frequency")));
        this.currentMoney = Integer.parseInt(String.valueOf(document.getLong("currentMoney")));
    }

    public Wallet() {
        this.ownerId = "";
        this.id = "";
        this.walletName = "";
        this.lowBalanceAlert = false;
        this.minimumBalance = 0;
        this.goalSavingsEnabled = false;
        this.goalAmount = 0;
        this.savingsDeadline = "";
        this.frequency = 0;
        this.currentMoney = 0;
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
        this.currentMoney = 0;
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

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(int currentMoney) { this.currentMoney = currentMoney;    }

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
                ", currentMoney=" + currentMoney +
                "}";
    }
}
