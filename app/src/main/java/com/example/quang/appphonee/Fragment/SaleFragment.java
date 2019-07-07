package com.example.quang.appphonee.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Adapter.DanhSachSanPhamAdapter;
import com.example.quang.appphonee.Adapter.SaleAdapter;
import com.example.quang.appphonee.Adapter.SanPhamPhoBienAdapter;
import com.example.quang.appphonee.ChiTietKhuyenMai;
import com.example.quang.appphonee.DanhSachSanPhamActivity;
import com.example.quang.appphonee.MainActivity;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.ItemDiscountFragment;
import com.example.quang.appphonee.Model.LichSuMuaHang;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaleFragment extends Fragment {

    RecyclerView rvsale;
    ArrayList<ItemDiscountFragment> itemDiscountFragments;
    SaleAdapter saleAdapter;

    public SaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale,container,false);

//        recyclerView = this.getId(rvSale);

        if(CheckConnection.haveNetworkConnection(getActivity())) {
            getSanPham();
        }
        else {
            CheckConnection.ShowToast(getActivity(), getString(R.string.kiem_tra_ket_noi));
        }

        // Inflate the layout for this fragment

        rvsale = view.findViewById(R.id.rviewSale);
        itemDiscountFragments = new ArrayList<>();
        saleAdapter = new SaleAdapter(getActivity(), itemDiscountFragments);
        rvsale.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        rvsale.setAdapter(saleAdapter);

        return view;

    }

    public void loadmore(){


    }



    private void getSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLGetSanPhamKhuyenMai, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            itemDiscountFragments.add(new ItemDiscountFragment(
                                    jsonObject.getInt("id"),
                                    jsonObject.getInt("giabd"),
                                    jsonObject.getInt("giakm"),
                                    jsonObject.getString("hinhback")
                            ));
                            saleAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(getActivity(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
