package com.learning.personal_expense_management.model;

import com.google.type.DateTime;

public class ViTien {
    private String TenVi;
    private boolean Canhbao;
    private int SoTienToiThieu;
    private boolean DatKiVongTietKiem;
    private int SoTienKiVong;
    private DateTime HanTietKiem;
    private int ChuKy;

    public ViTien(String tenVi, boolean canhbao, int soTienToiThieu, boolean datKiVongTietKiem, int soTienKiVong, DateTime hanTietKiem, int chuKy) {
        TenVi = tenVi;
        Canhbao = canhbao;
        SoTienToiThieu = soTienToiThieu;
        DatKiVongTietKiem = datKiVongTietKiem;
        SoTienKiVong = soTienKiVong;
        HanTietKiem = hanTietKiem;
        ChuKy = chuKy;
    }

    public String getTenVi() {
        return TenVi;
    }

    public void setTenVi(String tenVi) {
        TenVi = tenVi;
    }

    public boolean isCanhbao() {
        return Canhbao;
    }

    public void setCanhbao(boolean canhbao) {
        Canhbao = canhbao;
    }

    public int getSoTienToiThieu() {
        return SoTienToiThieu;
    }

    public void setSoTienToiThieu(int soTienToiThieu) {
        SoTienToiThieu = soTienToiThieu;
    }

    public boolean isDatKiVongTietKiem() {
        return DatKiVongTietKiem;
    }

    public void setDatKiVongTietKiem(boolean datKiVongTietKiem) {
        DatKiVongTietKiem = datKiVongTietKiem;
    }

    public int getSoTienKiVong() {
        return SoTienKiVong;
    }

    public void setSoTienKiVong(int soTienKiVong) {
        SoTienKiVong = soTienKiVong;
    }

    public DateTime getHanTietKiem() {
        return HanTietKiem;
    }

    public void setHanTietKiem(DateTime hanTietKiem) {
        HanTietKiem = hanTietKiem;
    }

    public int getChuKy() {
        return ChuKy;
    }

    public void setChuKy(int chuKy) {
        ChuKy = chuKy;
    }

    @Override
    public String toString() {
        return "ViTien{" +
                "TenVi='" + TenVi + '\'' +
                ", Canhbao=" + Canhbao +
                ", SoTienToiThieu=" + SoTienToiThieu +
                ", DatKiVongTietKiem=" + DatKiVongTietKiem +
                ", SoTienKiVong=" + SoTienKiVong +
                ", HanTietKiem=" + HanTietKiem +
                ", ChuKy=" + ChuKy +
                '}';
    }
}
