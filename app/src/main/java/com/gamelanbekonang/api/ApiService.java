package com.gamelanbekonang.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 27/03/2018.
 */

public interface ApiService {

    @GET("iklan")
    Call<ResponseBody> getData();

    @GET("category/{categoryId}")
    Call <ResponseBody> getCategory(@Path("categoryId") String categoryId);

}
