package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

import java.io.Serializable;
import java.util.List;

public class Loan implements Serializable {
    private String id;
    private String ownerId;
    private String borrowerName;
    private int amount;
    private String note;
    private String timestamp;
    private int repaymentPeriod;
    private boolean hasInterestRate;
    private double interestRate;
    private boolean interestRateType; //true = based on pricipal debt
    private boolean isLend;
    private String deadline;
    private double interest;
    private int paid;
    private List<String> predictTransactions;
    private List<String> returnTransactions;
    private String initialTransaction;

    public Loan() {
    }

    public Loan(String id, String ownerId, String borrowerName, int amount, String note, String timestamp, int repaymentPeriod, boolean hasInterestRate, double interestRate, boolean interestRateType, boolean isLend, String deadline, double interest, int paid, List<String> predictTransactions, List<String> returnTransactions, String initialTransaction) {
        this.id = id;
        this.ownerId = ownerId;
        this.borrowerName = borrowerName;
        this.amount = amount;
        this.note = note;
        this.timestamp = timestamp;
        this.repaymentPeriod = repaymentPeriod;
        this.hasInterestRate = hasInterestRate;
        this.interestRate = interestRate;
        this.interestRateType = interestRateType;
        this.isLend = isLend;
        this.deadline = deadline;
        this.interest = interest;
        this.paid = paid;
        this.predictTransactions = predictTransactions;
        this.returnTransactions = returnTransactions;
        this.initialTransaction = initialTransaction;
    }

    public Loan(QueryDocumentSnapshot document) {
        this.id = document.getString("id");
        this.ownerId = document.getString("ownerId");
        this.borrowerName = document.getString("borrowerName");
        this.amount = Integer.parseInt(String.valueOf(document.getLong("amount")));
        this.note = document.getString("note");
        this.timestamp = document.getString("timestamp");
        this.repaymentPeriod = Integer.valueOf(Math.toIntExact(document.getLong("repaymentPeriod")));
        this.hasInterestRate = Boolean.TRUE.equals(document.getBoolean("hasInterestRate"));
        this.interestRate = document.getDouble("interestRate");
        this.interestRateType = Boolean.TRUE.equals(document.getBoolean("interestRateType"));
        this.isLend = Boolean.TRUE.equals(document.getBoolean("isLend"));
        this.deadline = document.getString("deadline");
        this.interest = document.getDouble("interest");
        this.paid = Integer.valueOf(Math.toIntExact(document.getLong("paid")));
        this.predictTransactions = (List<String>) document.get("predictTransactions");
        this.returnTransactions = (List<String>) document.get("returnTransactions");
        this.initialTransaction = document.getString("initialTransaction");
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", repaymentPeriod=" + repaymentPeriod +
                ", hasInterestRate=" + hasInterestRate +
                ", interestRate=" + interestRate +
                ", interestRateType=" + interestRateType +
                ", isLend=" + isLend +
                ", deadline='" + deadline + '\'' +
                ", interest=" + interest +
                ", paid=" + paid +
                ", predictTransactions=" + predictTransactions +
                ", returnTransactions=" + returnTransactions +
                ", initialTransaction='" + initialTransaction + '\'' +
                '}';
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public boolean isHasInterestRate() {
        return hasInterestRate;
    }

    public void setHasInterestRate(boolean hasInterestRate) {
        this.hasInterestRate = hasInterestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public boolean isInterestRateType() {
        return interestRateType;
    }

    public void setInterestRateType(boolean interestRateType) {
        this.interestRateType = interestRateType;
    }

    public boolean isLend() {
        return isLend;
    }

    public void setLend(boolean lend) {
        isLend = lend;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public List<String> getPredictTransactions() {
        return predictTransactions;
    }

    public void setPredictTransactions(List<String> predictTransactions) {
        this.predictTransactions = predictTransactions;
    }

    public List<String> getReturnTransactions() {
        return returnTransactions;
    }

    public void setReturnTransactions(List<String> returnTransactions) {
        this.returnTransactions = returnTransactions;
    }

    public String getInitialTransaction() {
        return initialTransaction;
    }

    public void setInitialTransaction(String initialTransaction) {
        this.initialTransaction = initialTransaction;
    }
}
