package com.example.quang.appphonee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.InfoAccount;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ThongTinKhachHangActivity extends AppCompatActivity {

    TextView txtHoTen, txtDiaChi, txtSDT;
    Button btnSuDungTTnay, btnSuDungTTkhac,btnthanhtoantainha;
    ArrayList<InfoAccount> arrayList;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        btnthanhtoantainha = findViewById(R.id.btnthanhtoantainha);
        txtHoTen = findViewById(R.id.tvHoTen);
        txtDiaChi = findViewById(R.id.tvDiaChi);
        txtSDT = findViewById(R.id.tvSDT);
        btnSuDungTTnay = findViewById(R.id.btnSuDungTTnay);
        btnSuDungTTkhac = findViewById(R.id.btnSuDungTTkhac);
        arrayList = new ArrayList<>();
        sessionManager = new SessionManager(getApplicationContext());
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        LoadThongTin();
        XuLyNutSuDungThanhpaypal();
        XuLyNutSuDungTainha();
        XuLyNutSuDungTTKhac();

    }

    private void XuLyNutSuDungTTKhac() {
        btnSuDungTTkhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinKhachHangActivity.this, ThongTinKhachHangKhacActivity.class);
                startActivity(intent);
            }
        });
    }

    private void XuLyNutSuDungThanhpaypal() {
        btnSuDungTTnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    final String hoTen = arrayList.get(0).getHoTen();
                    final String diaChi = arrayList.get(0).getDiaChi();
                    final String SDT = arrayList.get(0).getSDT();

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            if(Integer.parseInt(madonhang) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(ThongTinKhachHangActivity.this);
                                StringRequest request = new StringRequest(Request.Method.POST, Server.URLChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")) {
                                            MainActivity.gioHangArrayList.clear();
                                            CheckConnection.ShowToast(ThongTinKhachHangActivity.this, "Thông tin và giỏ hàng của bạn đã được chúng tôi ghi nhận");
                                            Intent intent = new Intent(ThongTinKhachHangActivity.this, PaypalActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            CheckConnection.ShowToast(ThongTinKhachHangActivity.this, "Quá trình ghi nhận thông tin bị lỗi!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0; i < MainActivity.gioHangArrayList.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.gioHangArrayList.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.gioHangArrayList.get(i).getTenSP());
                                                jsonObject.put("soluong", MainActivity.gioHangArrayList.get(i).getSoLuong());
                                                jsonObject.put("giasp", MainActivity.gioHangArrayList.get(i).getGiaSP());
                                                jsonObject.put("tonggiasp", MainActivity.gioHangArrayList.get(i).getTongGiaSP());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        hashMap.put("idAccount", sessionManager.getIDAccount());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            CheckConnection.ShowToast(ThongTinKhachHangActivity.this, error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang", hoTen);
                            hashMap.put("diachi", diaChi);
                            hashMap.put("sodienthoai", SDT);
                            hashMap.put("idAccount", sessionManager.getIDAccount());
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
    }


    private void XuLyNutSuDungTainha() {
        btnthanhtoantainha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    final String hoTen = arrayList.get(0).getHoTen();
                    final String diaChi = arrayList.get(0).getDiaChi();
                    final String SDT = arrayList.get(0).getSDT();

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            if(Integer.parseInt(madonhang) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(ThongTinKhachHangActivity.this);
                                StringRequest request = new StringRequest(Request.Method.POST, Server.URLChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")) {
                                            MainActivity.gioHangArrayList.clear();
                                            CheckConnection.ShowToast(ThongTinKhachHangActivity.this, "Thông tin và giỏ hàng của bạn đã được chúng tôi ghi nhận");
                                            Intent intent = new Intent(ThongTinKhachHangActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            CheckConnection.ShowToast(ThongTinKhachHangActivity.this, "Quá trình ghi nhận thông tin bị lỗi!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0; i < MainActivity.gioHangArrayList.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.gioHangArrayList.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.gioHangArrayList.get(i).getTenSP());
                                                jsonObject.put("soluong", MainActivity.gioHangArrayList.get(i).getSoLuong());
                                                jsonObject.put("giasp", MainActivity.gioHangArrayList.get(i).getGiaSP());
                                                jsonObject.put("tonggiasp", MainActivity.gioHangArrayList.get(i).getTongGiaSP());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        hashMap.put("idAccount", sessionManager.getIDAccount());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            CheckConnection.ShowToast(ThongTinKhachHangActivity.this, error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang", hoTen);
                            hashMap.put("diachi", diaChi);
                            hashMap.put("sodienthoai", SDT);
                            hashMap.put("idAccount", sessionManager.getIDAccount());
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
    }

    private void LoadThongTin() {
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLGetTTTK, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.length() > 2) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            arrayList.add(new InfoAccount(
                                    jsonObject.getString("hoten"),
                                    jsonObject.getString("diachi"),
                                    jsonObject.getString("sdt")
                            ));
                            txtHoTen.setText(getString(R.string.ho_ten) + "  " + jsonObject.getString("hoten"));
                            txtDiaChi.setText(getString(R.string.dia_chi) + "  " + jsonObject.getString("diachi"));
                            txtSDT.setText(getString(R.string.sdt) + "  " + jsonObject.getString("sdt"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                    hashMap.put("idAccount", sessionManager.getIDAccount());
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
