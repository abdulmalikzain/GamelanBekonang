package com.gamelanbekonang.api;

import com.gamelanbekonang.beans.Iklan;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 27/03/2018.
 */

public class RetroClient {
    /********
     * URLS
     *******/
//    private static final String ROOT_URL = "http://bekonang-store.000webhostapp.com/";

    /**
     * Get Retrofit Instance
     */
//    private static Retrofit getRetrofitInstance() {
//        return new Retrofit.Builder()
//                .baseUrl(ROOT_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

    /**
     * Get API Service
     *
     * @return API Service
     */
//    public static ApiService getApiService() {
//        return getRetrofitInstance().create(ApiService.class);
//    }

    private Iklan[] iklans;

    public Iklan[] getAndroid() {
        return iklans;
    }

}
