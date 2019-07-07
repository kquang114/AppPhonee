package com.example.quang.appphonee.Fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.appphonee.Adapter.DanhSachSanPhamAdapter;
import com.example.quang.appphonee.Adapter.FindAdpater;
import com.example.quang.appphonee.ChiTietSanPhamActivity;
import com.example.quang.appphonee.DanhSachSanPhamActivity;
import com.example.quang.appphonee.GioHangActivity;
import com.example.quang.appphonee.Model.ApiInterface;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {

    ListView listViewDanhSachSP;
    ArrayList<Sanpham> arrayListDanhSachSP;
    DanhSachSanPhamAdapter danhSachSanPhamAdapter;
    int idDanhSachSP = 0;
    String title = "";
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean emptyData = false;
    DanhSachSanPhamActivity.mHandler mHandler;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sanpham> sanphamList;
    private FindAdpater findAdpater;
    private ApiInterface apiInterface;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_find, container, false);

//
//        listViewDanhSachSP = v.findViewById(R.id.lvFind);
//        footerView = inflater.inflate(R.layout.progressbar, null);
//
//
//        arrayListDanhSachSP = new ArrayList<>();
//        danhSachSanPhamAdapter = new DanhSachSanPhamAdapter(getActivity(), arrayListDanhSachSP);
//        listViewDanhSachSP.setAdapter(danhSachSanPhamAdapter);
//
//        Log.i("giatriloaisanpham", idDanhSachSP + "");
//
//        if (CheckConnection.haveNetworkConnection(getActivity())) {
//            getData(page);
//            LoadMoreData();
//        } else {
//            CheckConnection.ShowToast(getActivity(), getString(R.string.kiem_tra_ket_noi));
//
//        }
////
////        recyclerView = v.findViewById(R.id.rvFind);
////        layoutManager = new LinearLayoutManager(getActivity());
////        recyclerView.setLayoutManager(layoutManager);
////        recyclerView.setHasFixedSize(true);
////
////        find( "");
//
        Intent intent = new Intent(getContext(),FindFragmentActivity.class);
        startActivity(intent);
        return v;
    }
//
////    public void find(String key){
////        apiInterface = Server.getApi().create(ApiInterface.class);
////        Call<ArrayList<Sanpham>> call = apiInterface.getSanpham(key);
////
////        call.enqueue(new Callback<ArrayList<Sanpham>>() {
////            @Override
////            public void onResponse(Call<ArrayList<Sanpham>> call, Response<ArrayList<Sanpham>> response) {
////                sanphamList = response.body();
////                findAdpater= new FindAdpater( sanphamList,getActivity());
////                recyclerView.setAdapter(findAdpater);
////                findAdpater.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onFailure(Call<ArrayList<Sanpham>> call, Throwable t) {
////                Toast.makeText(getActivity(),"error on :" + t.toString(),Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
//
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.menu, menu);
////        return super.onCreateOptionsMenu(menu);
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.search:
//                Intent intent = new Intent(getContext(), GioHangActivity.class);
//                startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void LoadMoreData() {
//        listViewDanhSachSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
//                intent.putExtra("ChiTietSanPham", arrayListDanhSachSP.get(position));
//                startActivity(intent);
//            }
//        });
//        listViewDanhSachSP.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && emptyData == false) {
//                    isLoading = true;
//                    FindFragment.ThreadData threadData = new ThreadData();
//                    threadData.start();
//                }
//            }
//        });
//    }
//
//    private void getData(int trang) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDanhSachSanPham + String.valueOf(trang), new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response != null && response.length() != 2) {
//                    listViewDanhSachSP.removeFooterView(footerView);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            arrayListDanhSachSP.add(new Sanpham(
//                                    jsonObject.getInt("id"),
//                                    jsonObject.getString("tensp"),
//                                    jsonObject.getInt("giasp"),
//                                    jsonObject.getString("hinhanhsp"),
//                                    jsonObject.getString("motasp"),
//                                    jsonObject.getInt("idloaisp")
//                            ));
//                            danhSachSanPhamAdapter.notifyDataSetChanged();
//                            Log.i("cc", jsonArray.toString());
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    emptyData = true;
//                    listViewDanhSachSP.removeFooterView(footerView);
//                    CheckConnection.ShowToast(getActivity(), getString(R.string.het_du_lieu_hien_thi));
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast(getActivity(), error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<String, String>();
//                param.put("idloaisanpham", String.valueOf(idDanhSachSP));
//                return param;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
//
//    public class mHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    listViewDanhSachSP.addFooterView(footerView);
//                    break;
//                case 1:
//                    getData(++page);
//                    isLoading = false;
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    }
//
//    public class ThreadData extends Thread {
//        @Override
//        public void run() {
//            mHandler.sendEmptyMessage(0);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Message message = mHandler.obtainMessage(1);
//            mHandler.sendMessage(message);
//            super.run();
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.search, menu);
//
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getActivity().getComponentName())
//        );
//
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                getData(page);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                getData(page);
//                return false;
//            }
//        });
//
//    }


}
