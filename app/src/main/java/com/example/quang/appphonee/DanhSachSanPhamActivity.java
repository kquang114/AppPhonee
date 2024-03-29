package com.example.quang.appphonee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Adapter.DanhSachSanPhamAdapter;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarDanhSachSP;
    ListView listViewDanhSachSP;
    ArrayList<Sanpham> arrayListDanhSachSP;
    DanhSachSanPhamAdapter danhSachSanPhamAdapter;
    int idDanhSachSP;
    String title = "";
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean emptyData = false;
    mHandler mHandler;
    ShimmerFrameLayout shimmerViewContainer;
    String finalSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);

        toolbarDanhSachSP = findViewById(R.id.toolbarDanhSachSP);
        listViewDanhSachSP = findViewById(R.id.listviewDanhSachSP);
        shimmerViewContainer = findViewById(R.id.shimmer_view_container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();

        setSupportActionBar(toolbarDanhSachSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDanhSachSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrayListDanhSachSP = new ArrayList<>();
        danhSachSanPhamAdapter = new DanhSachSanPhamAdapter(DanhSachSanPhamActivity.this, arrayListDanhSachSP);
        listViewDanhSachSP.setAdapter(danhSachSanPhamAdapter);

        idDanhSachSP = getIntent().getIntExtra("idloaisp", 0);
        title = getIntent().getStringExtra("title_activity");
        toolbarDanhSachSP.setTitle(title);
        Log.d("giatriloaisanpham", idDanhSachSP + "");

        if(CheckConnection.haveNetworkConnection(DanhSachSanPhamActivity.this)) {
            getData(page);
            LoadMoreData();
        }
        else {
            CheckConnection.ShowToast(this, getString(R.string.kiem_tra_ket_noi));
            finish();
        }

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem item = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                String[] search = s.split(" ");
//
//                for(int i = 0; i < search.length; i++) {
//                    finalSearch += "%" + search[i] + "%";
//                }
//                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
//                intent.putExtra("searchString", finalSearch);
//                startActivity(intent);
//                finalSearch = "";
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void LoadMoreData() {
        listViewDanhSachSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachSanPhamActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("ChiTietSanPham", arrayListDanhSachSP.get(position));
                startActivity(intent);
            }
        });
        listViewDanhSachSP.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && emptyData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int trang) {
        RequestQueue requestQueue = Volley.newRequestQueue(DanhSachSanPhamActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDanhSachSanPham + String.valueOf(trang), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null && response.length() != 2) {
                    listViewDanhSachSP.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            arrayListDanhSachSP.add(new Sanpham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idloaisp")
                            ));
                            danhSachSanPhamAdapter.notifyDataSetChanged();
                        }
                        shimmerViewContainer.stopShimmerAnimation();
                        shimmerViewContainer.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    emptyData = true;
                    listViewDanhSachSP.removeFooterView(footerView);
                    CheckConnection.ShowToast(DanhSachSanPhamActivity.this, getString(R.string.het_du_lieu_hien_thi));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(DanhSachSanPhamActivity.this, error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idloaisanpham", String.valueOf(idDanhSachSP));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewDanhSachSP.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerViewContainer.stopShimmerAnimation();
    }
}
