package com.example.quang.appphonee.Model;

import java.io.Serializable;

/**
 * Created by quang on 4/12/19.
 */

public class Sanpham implements Serializable {

    private int id;
    private String tenSP;
    private int giaSP;
    private String hinhanhSP;
    private String motaSP;
    private int idLoaiSP;

    public Sanpham(int id, String tenSP, int giaSP, String hinhanhSP, String motaSP, int idLoaiSP) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhanhSP = hinhanhSP;
        this.motaSP = motaSP;
        this.idLoaiSP = idLoaiSP;
    }

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
}

