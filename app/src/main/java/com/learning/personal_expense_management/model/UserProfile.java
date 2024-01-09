package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String id;
    private String name;
    private String email;
    private String defaultCurrency;
    private String language;
    private String securityMethod;
    private boolean tip;
    private boolean lowBalanceAlert;
    private boolean dailyReminders;

    public UserProfile(QueryDocumentSnapshot document) {
        this.id = document.getString("id");
        this.name = document.getString("name");
        this.email = document.getString("email");
        this.defaultCurrency = document.getString("defaultCurrency");
        this.language = document.getString("language");
        this.securityMethod = document.getString("securityMethod");
        this.tip = document.getBoolean("tip");
        this.lowBalanceAlert = document.getBoolean("lowBalanceAlert");
        this.dailyReminders = document.getBoolean("dailyReminders");
    }
    public UserProfile() {
        this.id = "";
        this.name = "";
        this.email = "";
        this.defaultCurrency = "";
        this.language = "";
        this.securityMethod = "";
        this.tip = false;
        this.lowBalanceAlert = false;
        this.dailyReminders = false;
    }
    public UserProfile(String id, String name, String email, String defaultCurrency, String language, String securityMethod, boolean tip, boolean lowBalanceAlert, boolean dailyReminders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.defaultCurrency = defaultCurrency;
        this.language = language;
        this.securityMethod = securityMethod;
        this.tip = tip;
        this.lowBalanceAlert = lowBalanceAlert;
        this.dailyReminders = dailyReminders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSecurityMethod() {
        return securityMethod;
    }

    public void setSecurityMethod(String securityMethod) {
        this.securityMethod = securityMethod;
    }

    public boolean isTip() {
        return tip;
    }

    public void setTip(boolean tip) {
        this.tip = tip;
    }

    public boolean isLowBalanceAlert() {
        return lowBalanceAlert;
    }

    public void setLowBalanceAlert(boolean lowBalanceAlert) {
        this.lowBalanceAlert = lowBalanceAlert;
    }

    public boolean isDailyReminders() {
        return dailyReminders;
    }

    public void setDailyReminders(boolean dailyReminders) {
        this.dailyReminders = dailyReminders;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", defaultCurrency='" + defaultCurrency + '\'' +
                ", language='" + language + '\'' +
                ", securityMethod='" + securityMethod + '\'' +
                ", tip=" + tip +
                ", lowBalanceAlert=" + lowBalanceAlert +
                ", dailyReminders=" + dailyReminders +
                '}';
    }
}

