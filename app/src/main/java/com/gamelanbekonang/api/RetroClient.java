package com.gamelanbekonang.api;

import com.gamelanbekonang.beans.Iklan;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 27/03/2018.
 */

public class RetroClient {

    private Iklan[] iklan;

    public Iklan[] getIklan() {
        return iklan;
    }


    static final String BASE_URL_API = "http://gamelanwirun.com/api/v1/";
    ////////////////////////////////////////////
    public static Retrofit getClient1(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static ApiService getInstanceRetrofit(){
        return getClient1().create(ApiService.class);
    }

}
