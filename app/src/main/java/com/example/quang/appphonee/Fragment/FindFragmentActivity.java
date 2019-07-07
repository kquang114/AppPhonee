package com.example.quang.appphonee.Fragment;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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
import com.example.quang.appphonee.MainActivity;
import com.example.quang.appphonee.Model.ApiInterface;
import com.example.quang.appphonee.Model.CheckConnection;
import com.example.quang.appphonee.Model.Sanpham;
import com.example.quang.appphonee.Model.Server;
import com.example.quang.appphonee.Model.sanphamtimkiem;
import com.example.quang.appphonee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindFragmentActivity extends AppCompatActivity {

   RecyclerView recyclerView;
   ArrayList<sanphamtimkiem> sanphams;
   FindAdpater findAdpater;
   ApiInterface apiInterface;
   RecyclerView.LayoutManager layoutManager;

    Toolbar timkiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_fragment);

        recyclerView = findViewById(R.id.rviewFind);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        find("");

        timkiem = findViewById(R.id.toolbarTimKiemSP);
        setSupportActionBar(timkiem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timkiem.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FindFragmentActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void find(String key){
        apiInterface = Server.getApi().create(ApiInterface.class);
        Call<ArrayList<sanphamtimkiem>> call = apiInterface.getSanpham(key);

        call.enqueue(new Callback<ArrayList<sanphamtimkiem>>() {
            @Override
            public void onResponse(Call<ArrayList<sanphamtimkiem>> call, Response<ArrayList<sanphamtimkiem>> response) {

                sanphams = response.body();
                findAdpater = new FindAdpater(sanphams,FindFragmentActivity.this);
                recyclerView.setAdapter(findAdpater);
                findAdpater.notifyDataSetChanged();

                Log.i("cc", String.valueOf(findAdpater));
            }

            @Override
            public void onFailure(Call<ArrayList<sanphamtimkiem>> call, Throwable t) {
                Toast.makeText(FindFragmentActivity.this,"error on :" + t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);

        super.onCreateOptionsMenu(menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                find(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                find(newText);
                return false;
            }
        });
        return true;

    }
}