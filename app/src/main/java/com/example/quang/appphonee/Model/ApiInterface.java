package com.example.quang.appphonee.Model;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by quang on 5/13/19.
 */

public interface ApiInterface {

    @GET("timkiem.php")
    Call <ArrayList<sanphamtimkiem>> getSanpham(@Query("key") String keyword) ;
}
