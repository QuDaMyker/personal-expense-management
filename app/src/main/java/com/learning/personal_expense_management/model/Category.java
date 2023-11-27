package com.learning.personal_expense_management.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Category {
    private String ownerId;
    private String id;
    private String name;
    private int backGround;
    private int icon;
    private int colorIcon;

    public Category() {
    }
    public Category(QueryDocumentSnapshot document) {
        this.ownerId = document.getString("ownerId");
        this.id = document.getString("id");
        this.name = document.getString("categoryName");
        this.backGround = document.getLong("colorCode").intValue();
        this.icon = document.getLong("icon").intValue();
    }

    public Category(String idOwner, String name, int backGround, int icon, int iconColor) {
        this.ownerId = idOwner;
        this.name = name;
        this.backGround = backGround;
        this.icon = icon;
        this.colorIcon = iconColor;
    }
    public String toString() {
        return "Category{" +
                "ownerId='" + ownerId + '\'' +
                ", id='" + id + '\'' +
                ", categoryName='" + name + '\'' +
                ", colorCode='" + backGround + '\'' +
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
}
