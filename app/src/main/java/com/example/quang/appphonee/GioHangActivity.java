package com.example.quang.appphonee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quang.appphonee.Adapter.GioHangAdapter;
import com.example.quang.appphonee.Model.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ListView listViewGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbar   ;
    GioHangAdapter gioHangAdapter;
    public static long tongtien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        toolbar = findViewById(R.id.toolbarGioHang);
        listViewGioHang = findViewById(R.id.listviewGioHang);
        txtThongBao = findViewById(R.id.tvGioHangTrong);
        txtTongTien = findViewById(R.id.tvTongTienGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMuaHang);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, MainActivity.gioHangArrayList);
        listViewGioHang.setAdapter(gioHangAdapter);

        if(MainActivity.gioHangArrayList.size() > 0) {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
        else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        }
        GetTongTien();
        XoaSanPhamKhoiGioHang();

        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangArrayList.size() > 0) {
                    Intent intent = new Intent(GioHangActivity.this, ThongTinKhachHangActivity.class);
                    startActivity(intent);
                }
                else {
                    CheckConnection.ShowToast(GioHangActivity.this, "Giỏ hàng chưa có sản phẩm để thanh toán !!!");
                }
            }
        });
    }

    private void loaddata(){
    }
    private void XoaSanPhamKhoiGioHang() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ra khỏi giỏ hàng ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.gioHangArrayList.remove(position);
                        gioHangAdapter.notifyDataSetChanged();
                        GetTongTien();
                        if(MainActivity.gioHangArrayList.size() <= 0) {
                            txtThongBao.setVisibility(View.VISIBLE);
                            GetTongTien();
                        }
                        else {
                            txtThongBao.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void GetTongTien() {
        DecimalFormat df = new DecimalFormat("###,###,###");

                for (int i = 0; i < MainActivity.gioHangArrayList.size(); i++) {
                    if (MainActivity.gioHangArrayList.size() == 1) {
                        tongtien = MainActivity.gioHangArrayList.get(i).getTongGiaSP();
                        txtTongTien.setText(df.format(tongtien) + "VNĐ");
                    }
                    else if (MainActivity.gioHangArrayList.size() > 1){
                        tongtien += MainActivity.gioHangArrayList.get(i).getTongGiaSP()   ;
                        txtTongTien.setText(df.format(tongtien) + "VNĐ");
                        //MainActivity.gioHangArrayList.get(i).setTongGiaSP(arrayList.get(position).getTongGiaSP() + arrayList.get(position).getGiaSP());
                    }
                    else {
                        txtTongTien.setText(df.format(tongtien) + "VNĐ");
                    }
                }
            Log.i("cc", String.valueOf(MainActivity.gioHangArrayList.size()));

    }
}
