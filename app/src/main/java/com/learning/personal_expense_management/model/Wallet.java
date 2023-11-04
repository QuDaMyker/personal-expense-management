package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class Wallet {
    private String walletName;
    private boolean lowBalanceAlert;
    private int minimumBalance;
    private boolean goalSavingsEnabled;
    private int goalAmount;
    private DateTime savingsDeadline;
    private int frequency;

    public Wallet(String walletName, boolean lowBalanceAlert, int minimumBalance, boolean goalSavingsEnabled, int goalAmount, DateTime savingsDeadline, int frequency) {
        this.walletName = walletName;
        this.lowBalanceAlert = lowBalanceAlert;
        this.minimumBalance = minimumBalance;
        this.goalSavingsEnabled = goalSavingsEnabled;
        this.goalAmount = goalAmount;
        this.savingsDeadline = savingsDeadline;
        this.frequency = frequency;
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

    public DateTime getSavingsDeadline() {
        return savingsDeadline;
    }

    public void setSavingsDeadline(DateTime savingsDeadline) {
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
                "walletName='" + walletName + '\'' +
                ", lowBalanceAlert=" + lowBalanceAlert +
                ", minimumBalance=" + minimumBalance +
                ", goalSavingsEnabled=" + goalSavingsEnabled +
                ", goalAmount=" + goalAmount +
                ", savingsDeadline=" + savingsDeadline +
                ", frequency=" + frequency +
                '}';
    }
}
