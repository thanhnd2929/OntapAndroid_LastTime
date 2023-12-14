package com.example.ontapandroid_lasttime.DTO;

public class DongVatDTO {

    private int ma;
    private String ten;
    private String loai;
    private String gioiTinh;
    private int soLuong;

    public DongVatDTO(int ma, String ten, String loai, String gioiTinh, int soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.loai = loai;
        this.gioiTinh = gioiTinh;
        this.soLuong = soLuong;
    }

    public DongVatDTO() {
    }

    public DongVatDTO(String ten, String loai, String gioiTinh, int soLuong) {
        this.ten = ten;
        this.loai = loai;
        this.gioiTinh = gioiTinh;
        this.soLuong = soLuong;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
