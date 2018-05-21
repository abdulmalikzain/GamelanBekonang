package com.gamelanbekonang.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lenovo on 27/03/2018.
 */

public interface ApiService {

    @GET("api/v1/iklan")
    Call<RetroClient> getJSON();

    //kenong
    @GET("api/v1/category/1")
    Call<RetroClient> getCategoryKenong();

    //kenong
    @GET("api/v1/category/2")
    Call<RetroClient> getCategoryDemung();

    //bonang
    @GET("api/v1/category/3")
    Call<RetroClient> getCategoryBonang();

    //gambang
    @GET("api/v1/category/4")
    Call<RetroClient> getCategoryGambang();

    //kendang
    @GET("api/v1/category/5")
    Call<RetroClient> getCategoryKendang();

    //peking
    @GET("api/v1/category/6")
    Call<RetroClient> getCategoryPeking();

    //rebab
    @GET("api/v1/category/7")
    Call<RetroClient> getCategoryRebab();

    //saron
    @GET("api/v1/category/8")
    Call<RetroClient> getCategorySaron();

    //slenthem
    @GET("api/v1/category/9")
    Call<RetroClient> getCategorySlenthem();

    //gong
    @GET("api/v1/category/10")
    Call<RetroClient> getCategoryGong();

    //kethukkempyang
    @GET("api/v1/category/11")
    Call<RetroClient> getCategoryKethuk();

    @GET("iklan")
    Call<ResponseBody> getData();

    @GET("api/v1/iklan")
    Call<ResponseBody> getDataCount();
}
