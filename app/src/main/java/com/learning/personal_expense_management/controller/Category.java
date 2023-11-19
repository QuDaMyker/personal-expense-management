package com.learning.personal_expense_management.controller;

public class Category {
    String name;
    int backGround;
    int icon;
    int colorIcon;
    public Category() {
    }

    public Category(String name, int backGround, int icon, int iconColor) {
        this.name = name;
        this.backGround = backGround;
        this.icon = icon;
        this.colorIcon = iconColor;
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
