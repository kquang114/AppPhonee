package com.example.quang.appphonee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by quang on 5/15/19.
 */

public class ThongTinKhachHangKhacActivity extends AppCompatActivity {
    EditText editHoTen, editDiaChi, editSDT;
    Button btnXacNhan;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang_khac);

        editHoTen = findViewById(R.id.editHoTen);
        editDiaChi = findViewById(R.id.editDiaChi);
        editSDT = findViewById(R.id.editSDT);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        sessionManager = new SessionManager(getApplicationContext());

        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String hoTen = editHoTen.getText().toString();
                    final String diaChi = editDiaChi.getText().toString();
                    final String SDT = editSDT.getText().toString();
                    if(hoTen.length() > 0 && diaChi.length() > 0 && editSDT.length() > 0) {
                        RequestQueue requestQueue = Volley.newRequestQueue(ThongTinKhachHangKhacActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDonHang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String madonhang) {
                                if(Integer.parseInt(madonhang) > 0) {
                                    sessionManager.setMaDonHang(madonhang);
                                    RequestQueue queue = Volley.newRequestQueue(ThongTinKhachHangKhacActivity.this);
                                    StringRequest request = new StringRequest(Request.Method.POST, Server.URLChiTietDonHang, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("1")) {
                                                MainActivity.gioHangArrayList.clear();
                                                CheckConnection.ShowToast(ThongTinKhachHangKhacActivity.this, "Thông tin và giỏ hàng của bạn đã được chúng tôi ghi nhận");
                                                Intent intent = new Intent(ThongTinKhachHangKhacActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                CheckConnection.ShowToast(ThongTinKhachHangKhacActivity.this, "Quá trình ghi nhận thông tin bị lỗi!");
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
                                            if(sessionManager.getLoginState()) {
                                                hashMap.put("idAccount", sessionManager.getIDAccount());
                                            }
                                            else {
                                                hashMap.put("idAccount", "null");
                                            }
                                            return hashMap;
                                        }
                                    };
                                    queue.add(request);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CheckConnection.ShowToast(ThongTinKhachHangKhacActivity.this, error.toString());
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("tenkhachhang", hoTen);
                                hashMap.put("diachi", diaChi);
                                hashMap.put("sodienthoai", SDT);
                                if(sessionManager.getLoginState()) {
                                    hashMap.put("idAccount", sessionManager.getIDAccount());
                                }
                                else {
                                    hashMap.put("idAccount", "null");
                                }
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    else {
                        CheckConnection.ShowToast(ThongTinKhachHangKhacActivity.this, "Bạn hãy kiểm tra lại các thông tin !!!");
                    }
                }
            });
        }
        else {
            CheckConnection.ShowToast(ThongTinKhachHangKhacActivity.this, getString(R.string.kiem_tra_ket_noi));
        }
    }
}
