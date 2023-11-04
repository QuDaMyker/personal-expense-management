package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class TaiKhoan {
    private String LoaiTaiKhoan;
    private String TenThe;
    private String SoThe;
    private DateTime NgayHetHan;
    private int SoTienHienCo;

    public TaiKhoan(String loaiTaiKhoan, String tenThe, String soThe, DateTime ngayHetHan, int soTienHienCo) {
        LoaiTaiKhoan = loaiTaiKhoan;
        TenThe = tenThe;
        SoThe = soThe;
        NgayHetHan = ngayHetHan;
        SoTienHienCo = soTienHienCo;
    }

    public String getLoaiTaiKhoan() {
        return LoaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        LoaiTaiKhoan = loaiTaiKhoan;
    }

    public String getTenThe() {
        return TenThe;
    }

    public void setTenThe(String tenThe) {
        TenThe = tenThe;
    }

    public String getSoThe() {
        return SoThe;
    }

    public void setSoThe(String soThe) {
        SoThe = soThe;
    }

    public DateTime getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(DateTime ngayHetHan) {
        NgayHetHan = ngayHetHan;
    }

    public int getSoTienHienCo() {
        return SoTienHienCo;
    }

    public void setSoTienHienCo(int soTienHienCo) {
        SoTienHienCo = soTienHienCo;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "LoaiTaiKhoan='" + LoaiTaiKhoan + '\'' +
                ", TenThe='" + TenThe + '\'' +
                ", SoThe='" + SoThe + '\'' +
                ", NgayHetHan=" + NgayHetHan +
                ", SoTienHienCo=" + SoTienHienCo +
                '}';
    }
}
