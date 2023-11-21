package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Category {
    private String ownerId;
    private String id;
    private String categoryName;
    private String colorCode;
    private String icon;
    public Category(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.categoryName = document.getString("categoryName");
        this.colorCode = document.getString("colorCode");
        this.icon = document.getString("icon");
    }


    @Override
    public String toString() {
        return "Category{" +
                "ownerId='" + ownerId + '\'' +
                ", id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", icon='" + icon + '\'' +
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Category(String ownerId, String id, String categoryName, String colorCode, String icon) {
        this.ownerId = ownerId;
        this.id = id;
        this.categoryName = categoryName;
        this.colorCode = colorCode;
        this.icon = icon;
    }
}
