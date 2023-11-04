package com.learning.personal_expense_management.model;

public class DanhMuc {
    private String TenDanhMuc;
    private String MaMau;
    private String BieuTuong;

    public DanhMuc(String tenDanhMuc, String maMau, String bieuTuong) {
        TenDanhMuc = tenDanhMuc;
        MaMau = maMau;
        BieuTuong = bieuTuong;
    }

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        TenDanhMuc = tenDanhMuc;
    }

    public String getMaMau() {
        return MaMau;
    }

    public void setMaMau(String maMau) {
        MaMau = maMau;
    }

    public String getBieuTuong() {
        return BieuTuong;
    }

    public void setBieuTuong(String bieuTuong) {
        BieuTuong = bieuTuong;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "TenDanhMuc='" + TenDanhMuc + '\'' +
                ", MaMau='" + MaMau + '\'' +
                ", BieuTuong='" + BieuTuong + '\'' +
                '}';
    }
}
