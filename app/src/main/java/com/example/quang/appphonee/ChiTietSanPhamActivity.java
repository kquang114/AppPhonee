package com.example.quang.appphonee;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.GioHang;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.sanphamtimkiem;
import com.squareup.picasso.Picasso;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarCTSP;
    ImageView imgCTSP;
    TextView txtTenSP, txtGiaSP, txtMoTaSP;
    Button btnThemVaoGioHang, btnMuaNgay;
    int idCTSP = 0;
    String tenCTSP = "";
    int giaCTSP = 0;
    String hinhAnhCTSP = "";
    String motaCTSP = "";
    int soLuong = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        toolbarCTSP = findViewById(R.id.toolbarChiTietSP);
        imgCTSP = findViewById(R.id.imgChiTietSP);
        txtTenSP = findViewById(R.id.tvTenCTSP);
        txtGiaSP = findViewById(R.id.tvGiaCTSP);
        txtMoTaSP = findViewById(R.id.tvMoTaCTSP);
        btnThemVaoGioHang = findViewById(R.id.btnThemVaoGioHang);
        btnMuaNgay = findViewById(R.id.btnMuaNgay);

        setSupportActionBar(toolbarCTSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCTSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      try{
          Sanpham sanPham = (Sanpham) getIntent().getSerializableExtra("ChiTietSanPham");
          idCTSP = sanPham.getId();
          tenCTSP = sanPham.getTenSP();
          giaCTSP = sanPham.getGiaSP();
          hinhAnhCTSP = sanPham.getHinhanhSP();
          motaCTSP = sanPham.getMotaSP();

      }
      catch(Exception e){

          sanphamtimkiem sanphamtimkiem = (sanphamtimkiem) getIntent().getSerializableExtra("ChiTiet");
          idCTSP = sanphamtimkiem.getId();
          tenCTSP = sanphamtimkiem.getTenSP();
          giaCTSP = sanphamtimkiem.getGiaSP();
          hinhAnhCTSP = sanphamtimkiem.getHinhanhSP();
          motaCTSP = sanphamtimkiem.getMotaSP();
      }

        Picasso.with(ChiTietSanPhamActivity.this).load(Server.localhostimage + hinhAnhCTSP).into(imgCTSP);
        txtTenSP.setText(tenCTSP);
        DecimalFormat df = new DecimalFormat("###,###,###");
        txtGiaSP.setText(getString(R.string.giasp, df.format(giaCTSP)));
        txtMoTaSP.setText(motaCTSP);

        btnMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangArrayList.size() > 0) {
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.gioHangArrayList.size(); i++) {
                        if(MainActivity.gioHangArrayList.get(i).getIdSP() == idCTSP) {
                            MainActivity.gioHangArrayList.get(i).setSoLuong(MainActivity.gioHangArrayList.get(i).getSoLuong() + 1);
                            if(MainActivity.gioHangArrayList.get(i).getSoLuong() >= 10){
                                MainActivity.gioHangArrayList.get(i).setSoLuong(10);
                            }
                            MainActivity.gioHangArrayList.get(i).setTongGiaSP(MainActivity.gioHangArrayList.get(i).getSoLuong() * giaCTSP);
                            exists = true;
                        }
                    }
                    if(exists == false) {
                        long tongGiaSP = soLuong * giaCTSP;
                        MainActivity.gioHangArrayList.add(new GioHang(idCTSP, tenCTSP, hinhAnhCTSP, giaCTSP, soLuong, tongGiaSP));
                    }
                }
                else {
                    long tongGiaSP = soLuong * giaCTSP;
                    MainActivity.gioHangArrayList.add(new GioHang(idCTSP, tenCTSP, hinhAnhCTSP, giaCTSP, soLuong, tongGiaSP));
                }
                Intent intent = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });

        btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangArrayList.size() > 0) {
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.gioHangArrayList.size(); i++) {
                        if(MainActivity.gioHangArrayList.get(i).getIdSP() == idCTSP) {
                            MainActivity.gioHangArrayList.get(i).setSoLuong(MainActivity.gioHangArrayList.get(i).getSoLuong() + 1);
                            if(MainActivity.gioHangArrayList.get(i).getSoLuong() >= 10){
                                MainActivity.gioHangArrayList.get(i).setSoLuong(10);
                            }
                            MainActivity.gioHangArrayList.get(i).setTongGiaSP(MainActivity.gioHangArrayList.get(i).getSoLuong() * giaCTSP);
                            exists = true;
                        }
                    }
                    if(exists == false) {
                        long tongGiaSP = soLuong * giaCTSP;
                        MainActivity.gioHangArrayList.add(new GioHang(idCTSP, tenCTSP, hinhAnhCTSP, giaCTSP, soLuong, tongGiaSP));
                    }
                    finish();
                }
                else {
                    long tongGiaSP = soLuong * giaCTSP;
                    MainActivity.gioHangArrayList.add(new GioHang(idCTSP, tenCTSP, hinhAnhCTSP, giaCTSP, soLuong, tongGiaSP));
                    finish();
                }
                CheckConnection.ShowToast(ChiTietSanPhamActivity.this, getString(R.string.da_them_vao_gio_hang));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

