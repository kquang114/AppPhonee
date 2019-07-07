package com.example.quang.appphonee.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Adapter.NoticeAdapter;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.HopThu;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.SessionManager;
import com.example.quang.appphonee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoticeFragment extends Fragment {

    TextView txtTaiKhoan, txtThongBao;
    Toolbar toolbar;
    SessionManager sessionManager;
    ListView listView;
    ArrayList<HopThu> arrayList;
    NoticeAdapter noticeAdapter;


    public NoticeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);


       // toolbar = view.findViewById(R.id.toolbarHopThuKH);
        txtTaiKhoan = view.findViewById(R.id.tvTaiKhoan);
        txtThongBao = view.findViewById(R.id.tvThongBao);
        listView = view.findViewById(R.id.listviewHopThu);
        sessionManager = new SessionManager(getContext());

        arrayList = new ArrayList<>();
        noticeAdapter = new NoticeAdapter(arrayList, getContext());
        listView.setAdapter(noticeAdapter);

        LoadData();

        return view;

    }

    private void LoadData() {
        if (CheckConnection.haveNetworkConnection(getContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLGetHopThu, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.length() > 2) {
                        txtThongBao.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                arrayList.add(new HopThu(
                                        jsonObject.getString("motakm"),
                                        jsonObject.getString("hinhanh"),
                                        jsonObject.getString("ngaygio")
                                ));
                                noticeAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        txtThongBao.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CheckConnection.ShowToast(getContext(), error.toString());
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
            CheckConnection.ShowToast(getContext(), getString(R.string.kiem_tra_ket_noi));
        }
    }
}
