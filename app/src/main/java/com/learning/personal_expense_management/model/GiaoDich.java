package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class GiaoDich {
    private String id;
    private int LoaiGiaoDich;
    private int SoTien;
    private String GhiChu;
    private DateTime NgayGiaoDich;
    private DateTime ThoiGianGiaoDich;
    private String TaiKhoanNguon;
    private String TaiKhoanDich;

    public GiaoDich(String id, int loaiGiaoDich, int soTien, String ghiChu, DateTime ngayGiaoDich, DateTime thoiGianGiaoDich, String taiKhoanNguon, String taiKhoanDich) {
        this.id = id;
        LoaiGiaoDich = loaiGiaoDich;
        SoTien = soTien;
        GhiChu = ghiChu;
        NgayGiaoDich = ngayGiaoDich;
        ThoiGianGiaoDich = thoiGianGiaoDich;
        TaiKhoanNguon = taiKhoanNguon;
        TaiKhoanDich = taiKhoanDich;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLoaiGiaoDich() {
        return LoaiGiaoDich;
    }

    public void setLoaiGiaoDich(int loaiGiaoDich) {
        LoaiGiaoDich = loaiGiaoDich;
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

    public String getTaiKhoanNguon() {
        return TaiKhoanNguon;
    }

    public void setTaiKhoanNguon(String taiKhoanNguon) {
        TaiKhoanNguon = taiKhoanNguon;
    }

    public String getTaiKhoanDich() {
        return TaiKhoanDich;
    }

    public void setTaiKhoanDich(String taiKhoanDich) {
        TaiKhoanDich = taiKhoanDich;
    }

    @Override
    public String toString() {
        return "GiaoDich{" +
                "id='" + id + '\'' +
                ", LoaiGiaoDich=" + LoaiGiaoDich +
                ", SoTien=" + SoTien +
                ", GhiChu='" + GhiChu + '\'' +
                ", NgayGiaoDich=" + NgayGiaoDich +
                ", ThoiGianGiaoDich=" + ThoiGianGiaoDich +
                ", TaiKhoanNguon='" + TaiKhoanNguon + '\'' +
                ", TaiKhoanDich='" + TaiKhoanDich + '\'' +
                '}';
    }
}
