package com.learning.personal_expense_management.model;

public class HoSoNguoiDung {
    private String id;
    private String Ten;
    private String Gmail;
    private String DonViTienMacDinh;
    private String NgonNgu;
    private String PhuongThucBaoMat;
    private boolean MeoTieuVat;
    private boolean CanhBaoCanViTien;
    private boolean NhacNhoHangNgay;

    public HoSoNguoiDung(String id, String ten, String gmail, String donViTienMacDinh, String ngonNgu, String phuongThucBaoMat, boolean meoTieuVat, boolean canhBaoCanViTien, boolean nhacNhoHangNgay) {
        this.id = id;
        Ten = ten;
        Gmail = gmail;
        DonViTienMacDinh = donViTienMacDinh;
        NgonNgu = ngonNgu;
        PhuongThucBaoMat = phuongThucBaoMat;
        MeoTieuVat = meoTieuVat;
        CanhBaoCanViTien = canhBaoCanViTien;
        NhacNhoHangNgay = nhacNhoHangNgay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getDonViTienMacDinh() {
        return DonViTienMacDinh;
    }

    public void setDonViTienMacDinh(String donViTienMacDinh) {
        DonViTienMacDinh = donViTienMacDinh;
    }

    public String getNgonNgu() {
        return NgonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        NgonNgu = ngonNgu;
    }

    public String getPhuongThucBaoMat() {
        return PhuongThucBaoMat;
    }

    public void setPhuongThucBaoMat(String phuongThucBaoMat) {
        PhuongThucBaoMat = phuongThucBaoMat;
    }

    public boolean isMeoTieuVat() {
        return MeoTieuVat;
    }

    public void setMeoTieuVat(boolean meoTieuVat) {
        MeoTieuVat = meoTieuVat;
    }

    public boolean isCanhBaoCanViTien() {
        return CanhBaoCanViTien;
    }

    public void setCanhBaoCanViTien(boolean canhBaoCanViTien) {
        CanhBaoCanViTien = canhBaoCanViTien;
    }

    public boolean isNhacNhoHangNgay() {
        return NhacNhoHangNgay;
    }

    public void setNhacNhoHangNgay(boolean nhacNhoHangNgay) {
        NhacNhoHangNgay = nhacNhoHangNgay;
    }

    @Override
    public String toString() {
        return "HoSoNguoiDung{" +
                "id='" + id + '\'' +
                ", Ten='" + Ten + '\'' +
                ", Gmail='" + Gmail + '\'' +
                ", DonViTienMacDinh='" + DonViTienMacDinh + '\'' +
                ", NgonNgu='" + NgonNgu + '\'' +
                ", PhuongThucBaoMat='" + PhuongThucBaoMat + '\'' +
                ", MeoTieuVat=" + MeoTieuVat +
                ", CanhBaoCanViTien=" + CanhBaoCanViTien +
                ", NhacNhoHangNgay=" + NhacNhoHangNgay +
                '}';
    }
}
