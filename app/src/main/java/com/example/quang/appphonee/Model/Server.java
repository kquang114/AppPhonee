package com.example.quang.appphonee.Model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by quang on 4/12/19.
 */

public class Server {
    public static final String localhost = "http://192.168.124.111:80/server/";
    public static String localhostimage = localhost + "image/";
    public static String URLSPMoiNhat = localhost + "getsanphammoinhat.php";
    public static String URLIphone = localhost + "getiphone.php";
    public static String URLOppo = localhost + "getoppo.php";
    public static String URLSamSung = localhost + "getsamsung.php";
    public static String URLXiaomi = localhost + "getxiaomi.php";
    public static String URLLoaiSP = localhost + "getloaisanpham.php";
    public static String URLSanPham = localhost + "getsanpham.php";
    public static String URLDonHang = localhost + "donhang.php";
    public static String URLChiTietDonHang = localhost + "chitietdonhang.php";
    public static String URLDanhSachSanPham = localhost + "getdanhsachsanpham.php?page=";
    public static String URLDangNhap = localhost + "dangnhap.php";
    public static String URLDangKi = localhost + "dangki.php";
    public static String URLInsertTTTK = localhost + "insertthongtinaccount.php";
    public static String URLUpdateTTTK = localhost + "updatethongtinaccount.php";
    public static String URLGetTTTK = localhost + "getthongtinaccount.php";
    public static String URLGetChiTietLichSuMuaHang = localhost + "getchitietlichsumuahang.php";
    public static String URLGetLichSuMuaHang = localhost + "getlichsumuahang.php";
    public static String URLGetSanPhamKhuyenMai = localhost + "getsanphamkhuyenmai.php";

    public static String URLDanhGiaSanPham = localhost + "danhgiasanpham.php";
    public static String URLListDanhGiaSanPham = localhost + "getdanhgiasanpham.php?page=";
    public static String URLGuiCauHoi = localhost + "guicauhoi.php";
    public static String URLGetHopThu = localhost + "gethopthu.php";
    public static String URLGetCauHoiThuongGap = localhost + "getcauhoithuonggap.php";
    public static String URLTimKiemTheoTen = localhost + "timkiemtheoten.php?page=";
    public static String URLGetGioHang = localhost + "getgiohang.php";

    public static String URLHuyDonHangGanNhat = localhost + "huydonhanggannhat.php";
    public static Retrofit retrofit;
    public static Retrofit getApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(localhost)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }

        return retrofit;
    }
}
