package com.example.quang.appphonee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Adapter.LichSuMuaHangAdapter;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.LichSuMuaHang;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LichSuMuaHangActivity extends AppCompatActivity {

    TextView txtTaiKhoan;
    ListView listView;
    Toolbar toolbar;
    ArrayList<LichSuMuaHang> arrayList;
    LichSuMuaHangAdapter lichSuMuaHangAdapter;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_hang);

        txtTaiKhoan = findViewById(R.id.tvTaiKhoanMuaHang);
        listView = findViewById(R.id.listviewLichSuMuaHang);
        toolbar = findViewById(R.id.toolbarLichSuMuaHang);
        sessionManager = new SessionManager(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtTaiKhoan.setText("Tài Khoản mua hàng: " + sessionManager.getUserName());
        Log.i("cc", sessionManager.getIDAccount());

        arrayList = new ArrayList<>();
        lichSuMuaHangAdapter = new LichSuMuaHangAdapter(arrayList, getApplicationContext());
        listView.setAdapter(lichSuMuaHangAdapter);


        LoadData();
        XemChiTiet();
        Log.i("cc", arrayList.toString());

    }

    private void XemChiTiet() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LichSuMuaHangActivity.this, ChiTietLichSuMuaHangActivity.class);
                intent.putExtra("madonhang", arrayList.get(position).getMaDonHang());
                intent.putExtra("ngaymuahang", arrayList.get(position).getNgayMuaHang());
                intent.putExtra("tinhtrang", arrayList.get(position).getTinhTrang());
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoadData() {
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLGetLichSuMuaHang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("chuoi", response);
                    Log.i("dodai", String.valueOf(response.length()));

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.i("dodaiarray", String.valueOf(jsonArray.length()));
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            Log.i("cc", jsonObject.getString("tenkhachhang"));
                            arrayList.add(new LichSuMuaHang(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tenkhachhang"),
                                    jsonObject.getString("diachi"),
                                    jsonObject.getString("sdt"),
                                    jsonObject.getString("ngaymuahang"),
                                    jsonObject.getString("tinhtrang")
                            ));
                            lichSuMuaHangAdapter.notifyDataSetChanged();
                        }
                        Log.i("cc", arrayList.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    if(response.length() > 2) {
//                        try {
//
//                            }
//                            Log.i("cc", arrayList.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else {
//                        CheckConnection.ShowToast(getApplicationContext(), "Quá trình xử lý bị lỗi");
//                    }
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
