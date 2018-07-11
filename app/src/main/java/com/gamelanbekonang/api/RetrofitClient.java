package com.gamelanbekonang.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 4/5/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }





//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseApiService.BASE_URL_IKLAN)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//        return retrofit;
//    }
//
//    public static ApiService getInstanceRetrofit(){
//        return getClient1().create(ApiService.class);
//    }






    //////////////////////////////////////////////////
    private static Retrofit getClient2(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseApiService.BASE_URL_UPDATE_PROFIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static BaseApiService getUpdateProfilRetrofit(){
        return getClient2().create(BaseApiService.class);
    }

    /////////////////////////////////////

    private static Retrofit getPassword(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseApiService.BASE_URL_CHANGE_PASS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
        public static BaseApiService getChangePassword(){
        return getPassword().create(BaseApiService.class);
        }


    static final String BASE_URL_MY_IKLAN = "https://bekonangstore.herokuapp.com/api/v1/user/";
    ////////////////////////////////////////////
    public static Retrofit getMyClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_MY_IKLAN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static BaseApiService getDataMyIklan(){
        return getMyClient().create(BaseApiService.class);
    }

}

