package com.learning.personal_expense_management.model;

public class Category {
    private String categoryName;
    private String colorCode;
    private String icon;

    public Category(String categoryName, String colorCode, String icon) {
        this.categoryName = categoryName;
        this.colorCode = colorCode;
        this.icon = icon;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
