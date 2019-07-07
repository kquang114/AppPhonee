package com.example.quang.appphonee.Model;

/**
 * Created by quang on 5/7/19.
 */

public class ItemDiscountFragment {

    int idKM;
    int giaKM;
    int giaBD;
    String imgBack;

    public int getIdKM() {
        return idKM;
    }

    public void setIdKM(int idKM) {
        this.idKM = idKM;
    }

    public int getGiaKM() {
        return giaKM;
    }

    public void setGiaKM(int giaKM) {
        this.giaKM = giaKM;
    }

    public int getGiaBD() {
        return giaBD;
    }

    public void setGiaBD(int giaBD) {
        this.giaBD = giaBD;
    }

    public String getImgBack() {
        return imgBack;
    }

    public void setImgBack(String imgBack) {
        this.imgBack = imgBack;
    }


    public ItemDiscountFragment(int idKM, int giaKM, int giaBD, String imgBack) {
        this.idKM = idKM;
        this.giaKM = giaKM;
        this.giaBD = giaBD;
        this.imgBack = imgBack;
    }
}
