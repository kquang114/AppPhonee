package com.example.quang.appphonee.Model;

/**
 * Created by quang on 5/6/19.
 */

public class HopThu {

    String moTa;
    String hinhAnh;
    String ngayGio;

    public HopThu(String moTa, String hinhAnh, String ngayGio) {
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.ngayGio = ngayGio;
    }
    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }





}
