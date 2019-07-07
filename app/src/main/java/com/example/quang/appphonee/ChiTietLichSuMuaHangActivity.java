package com.example.quang.appphonee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Adapter.ChiTietLichSuAdapter;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.GioHang;
import com.example.quang.appphonee.Model.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietLichSuMuaHangActivity extends AppCompatActivity {

    TextView txtMaDH, txtNgayMuaHang, txtTinhTrang, txtTongTien;
    Button btnHuyDonHang;
    Toolbar toolbar;
    ListView listView;
    ChiTietLichSuAdapter chiTietLSMHAdapter;
    ArrayList<GioHang> arrayList;
    Integer maDH = 0;
    String ngayMuaHang = "";
    String tinhTrang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_su_mua_hang);

        txtMaDH = findViewById(R.id.tvMaDonHang);
        txtNgayMuaHang = findViewById(R.id.tvNgayMuaHang);
        txtTinhTrang = findViewById(R.id.tvTinhTrang);
        txtTongTien = findViewById(R.id.tvTongTien);
        btnHuyDonHang = findViewById(R.id.btnHuyDonHang);
        toolbar = findViewById(R.id.toolbarCTLSMH);
        listView = findViewById(R.id.listviewChiTietLichSuMuaHang);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LichSuMuaHangActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arrayList = new ArrayList<>();
        chiTietLSMHAdapter = new ChiTietLichSuAdapter(arrayList, getApplicationContext());
        listView.setAdapter(chiTietLSMHAdapter);

        LoadData();
        HuyDonHang();
    }

    private void HuyDonHang() {
        if(tinhTrang.equals("Chưa Giao")) {
            btnHuyDonHang.setVisibility(View.VISIBLE);
        }
        else {
            btnHuyDonHang.setVisibility(View.INVISIBLE);
        }
        btnHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietLichSuMuaHangActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn HỦY đơn hàng #" + maDH + " này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLHuyDonHangGanNhat, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("1")) {
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), LichSuMuaHangActivity.class);
                                        startActivity(intent);
                                        CheckConnection.ShowToast(getApplicationContext(), "Đơn hàng đã được HỦY");
                                    }
                                    else {
                                        CheckConnection.ShowToast(getApplicationContext(), "Quá trình xử lý bị lỗi");
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    CheckConnection.ShowToast(getApplicationContext(), error.toString());
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("madonhang", String.valueOf(maDH));
                                    return hashMap;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                        else {
                            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void LoadData() {
        maDH = getIntent().getIntExtra("madonhang", 0);
        ngayMuaHang = getIntent().getStringExtra("ngaymuahang");
        tinhTrang = getIntent().getStringExtra("tinhtrang");
        txtMaDH.setText("Mã đơn hàng: #" + maDH);
        txtNgayMuaHang.setText("Ngày mua hàng: " + ngayMuaHang);
        txtTinhTrang.setText("Tình trạng đơn hàng: " + tinhTrang);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLGetChiTietLichSuMuaHang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.length() > 2) {
                        try {
                            long tongtien = 0;
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                arrayList.add(new GioHang(
                                        jsonObject.getString("tensanpham"),
                                        jsonObject.getString("hinhanhsp"),
                                        jsonObject.getInt("giasp"),
                                        jsonObject.getInt("soluong"),
                                        jsonObject.getInt("tonggiasp")
                                ));
                                chiTietLSMHAdapter.notifyDataSetChanged();
                                tongtien += jsonObject.getInt("tonggiasp");
                            }
                            DecimalFormat df = new DecimalFormat("###,###,###");
                            txtTongTien.setText("Tổng tiền: " + df.format(tongtien) + " VNĐ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        CheckConnection.ShowToast(getApplicationContext(), "Quá trình xử lý bị lỗi");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("madonhang", String.valueOf(maDH));
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }
        else {
            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
        }
    }
}
