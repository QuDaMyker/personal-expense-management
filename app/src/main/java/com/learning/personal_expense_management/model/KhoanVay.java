package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class KhoanVay {
    private String id;
    private String TenNguoiVay;
    private int SoTien;
    private String GhiChu;
    private DateTime NgayGiaoDich;
    private DateTime ThoiGianGiaoDich;
    private int ThoiGianThanhToan;

    public KhoanVay(String id, String tenNguoiVay, int soTien, String ghiChu, DateTime ngayGiaoDich, DateTime thoiGianGiaoDich, int thoiGianThanhToan) {
        this.id = id;
        TenNguoiVay = tenNguoiVay;
        SoTien = soTien;
        GhiChu = ghiChu;
        NgayGiaoDich = ngayGiaoDich;
        ThoiGianGiaoDich = thoiGianGiaoDich;
        ThoiGianThanhToan = thoiGianThanhToan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenNguoiVay() {
        return TenNguoiVay;
    }

    public void setTenNguoiVay(String tenNguoiVay) {
        TenNguoiVay = tenNguoiVay;
    }

    public int getSoTien() {
        return SoTien;
    }

    public void setSoTien(int soTien) {
        SoTien = soTien;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public DateTime getNgayGiaoDich() {
        return NgayGiaoDich;
    }

    public void setNgayGiaoDich(DateTime ngayGiaoDich) {
        NgayGiaoDich = ngayGiaoDich;
    }

    public DateTime getThoiGianGiaoDich() {
        return ThoiGianGiaoDich;
    }

    public void setThoiGianGiaoDich(DateTime thoiGianGiaoDich) {
        ThoiGianGiaoDich = thoiGianGiaoDich;
    }

    public int getThoiGianThanhToan() {
        return ThoiGianThanhToan;
    }

    public void setThoiGianThanhToan(int thoiGianThanhToan) {
        ThoiGianThanhToan = thoiGianThanhToan;
    }

    @Override
    public String toString() {
        return "KhoanVay{" +
                "id='" + id + '\'' +
                ", TenNguoiVay='" + TenNguoiVay + '\'' +
                ", SoTien=" + SoTien +
                ", GhiChu='" + GhiChu + '\'' +
                ", NgayGiaoDich=" + NgayGiaoDich +
                ", ThoiGianGiaoDich=" + ThoiGianGiaoDich +
                ", ThoiGianThanhToan=" + ThoiGianThanhToan +
                '}';
    }
}
