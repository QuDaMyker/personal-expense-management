package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.List;


public class Category implements Serializable {
    private String ownerId;
    private String id;
    private String name;
    private int backGround;
    private int icon;
    private int colorIcon;

    private int isIncome;
    //0 là chi 1 là thu 2 là cả 2

    public Category() {

    }

    public Category(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.name = document.getString("categoryName");
        this.backGround = document.getLong("background").intValue();
        this.icon = document.getLong("icon").intValue();
        this.colorIcon = document.getLong("colorIcon").intValue();
        this.isIncome = document.getLong("isIncome").intValue();


    }

    public Category(String idOwner, String name, int backGround, int icon, int iconColor, int isIncome) {
        this.ownerId = idOwner;
        this.name = name;
        this.backGround = backGround;
        this.icon = icon;
        this.colorIcon = iconColor;
        this.isIncome = isIncome;
    }

    public Category(String idOwner, String id, String name, int backGround, int icon, int iconColor, int isIncome) {
        this.ownerId = idOwner;
        this.id = id;
        this.name = name;
        this.backGround = backGround;
        this.icon = icon;
        this.colorIcon = iconColor;
        this.isIncome = isIncome;
    }

    public String toString() {
        return "Category{" +
                "ownerId='" + ownerId + '\'' +
                ", id='" + id + '\'' +
                ", categoryName='" + name + '\'' +
                ", colorCodeBG='" + backGround + '\'' +
                ", icon='" + icon + '\'' +
                ", colorCodeIcon='" + colorIcon + '\'' +
                ", isIncome='" + isIncome + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackGround() {
        return backGround;
    }

    public void setBackGround(int backGround) {
        this.backGround = backGround;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColorIcon() {
        return colorIcon;
    }

    public void setColorIcon(int colorIcon) {
        this.colorIcon = colorIcon;
    }

    public int getIsIncome() {
        return this.isIncome;
    }

    ;

    public void setIncome(int income) {
        isIncome = income;
    }
}
