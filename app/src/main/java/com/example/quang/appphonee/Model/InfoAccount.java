package com.example.quang.appphonee.Model;

import java.io.Serializable;

/**
 * Created by quang on 4/20/19.
 */

public class InfoAccount  implements Serializable {
    private String hoTen;
    private String diaChi;
    private String SDT;

    public InfoAccount(String hoTen, String diaChi, String SDT) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.SDT = SDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
