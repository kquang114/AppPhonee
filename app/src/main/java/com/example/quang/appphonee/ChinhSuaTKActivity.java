package com.example.quang.appphonee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.quang.appphonee.Model.InfoAccount;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChinhSuaTKActivity extends AppCompatActivity {

    EditText editHoTen, editDiaChi, editSDT;
    Button btnXacNhan;
    Toolbar toolbar;
    ArrayList<InfoAccount> arrayList;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_tk);

        editHoTen = findViewById(R.id.editHoTen);
        editDiaChi = findViewById(R.id.editDiaChi);
        editSDT = findViewById(R.id.editSDT);
        toolbar = findViewById(R.id.toolbarChinhSuaTTTK);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        arrayList = new ArrayList<>();
        sessionManager = new SessionManager(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arrayList = (ArrayList<InfoAccount>) getIntent().getSerializableExtra("tttk");
        if(!arrayList.get(0).getHoTen().equals("Trống")) {
            editHoTen.setText(arrayList.get(0).getHoTen());
            editDiaChi.setText(arrayList.get(0).getDiaChi());
            editSDT.setText(arrayList.get(0).getSDT());
        }

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = editHoTen.getText().toString();
                String diaChi = editDiaChi.getText().toString();
                String sdt = editSDT.getText().toString();
                if(hoTen.length() > 0 && diaChi.length() > 0 && sdt.length() > 0) {
                    if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLUpdateTTTK, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("update thanh cong")) {
                                    Intent intent = new Intent(ChinhSuaTKActivity.this, ThongTinTaiKhoanActivity.class);
                                    startActivity(intent);
                                    finish();
                                    CheckConnection.ShowToast(getApplicationContext(), "Cập nhật Thông tin hoàn tất");
                                }
                                else {
                                    CheckConnection.ShowToast(getApplicationContext(), "Quá trình cập nhật Thông tin bị lỗi");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("idAccount", sessionManager.getIDAccount());
                                hashMap.put("hoten", editHoTen.getText().toString());
                                hashMap.put("diachi", editDiaChi.getText().toString());
                                hashMap.put("sdt", editSDT.getText().toString());
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    else {
                        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
                    }
                }
                else {
                    CheckConnection.ShowToast(getApplicationContext(), "Thông tin không phép được để Trống");
                }

            }
        });
    }
}
