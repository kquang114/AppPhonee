package com.example.quang.appphonee.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by quang on 5/18/19.
 */

public class sanphamtimkiem implements Serializable {


    @SerializedName("id") private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhanhSP() {
        return hinhanhSP;
    }

    public void setHinhanhSP(String hinhanhSP) {
        this.hinhanhSP = hinhanhSP;
    }

    public String getMotaSP() {
        return motaSP;
    }

    public void setMotaSP(String motaSP) {
        this.motaSP = motaSP;
    }

    public int getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(int idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }

    @SerializedName("tensanpham") private String tenSP;
    @SerializedName("giasanpham") private int giaSP;
    @SerializedName("hinhanhsanpham") private String hinhanhSP;
    @SerializedName("motasanpham") private String motaSP;
    @SerializedName("idLoaiSanPham") private int idLoaiSP;
}
