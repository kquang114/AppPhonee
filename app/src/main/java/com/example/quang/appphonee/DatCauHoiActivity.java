package com.example.quang.appphonee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class DatCauHoiActivity extends AppCompatActivity {

    EditText editCauHoi;
    Button btnGui;
    Toolbar toolbar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_cau_hoi);

        editCauHoi = findViewById(R.id.editCauHoi);
        btnGui = findViewById(R.id.btnGui);
        toolbar = findViewById(R.id.toolbarDatCauHoi);
        sessionManager = new SessionManager(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GuiCauHoi();
    }

    private void GuiCauHoi() {
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cauhoi = editCauHoi.getText().toString();
                if(cauhoi.length() > 0) {
                    if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLGuiCauHoi, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")) {
                                    finish();
                                    CheckConnection.ShowToast(getApplicationContext(), "Câu hỏi của bạn đã được chúng tôi ghi nhận và sẽ trả lời cho bạn sớm nhất có thể");
                                }
                                else {
                                    CheckConnection.ShowToast(getApplicationContext(), "Qúa trình bị lỗi");
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
                                hashMap.put("cauhoi", cauhoi);
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
                else {
                    CheckConnection.ShowToast(getApplicationContext(), "Bạn phải đặt câu hỏi, không được để trống");
                }
            }
        });
    }
}
