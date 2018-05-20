package com.gamelanbekonang.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 4/5/2018.
 */

public interface BaseApiService {

    String BASE_API_URL = "http://bekonang-store.000webhostapp.com";
    String BASE_URL_IMAGE = "http://bekonang-store.000webhostapp.com/images/";
    String BASE_URL_IKLAN = "https://bekonang-store.000webhostapp.com/api/v1/";

    @FormUrlEncoded
    @POST("signin")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("notelp") String notelp,
                                       @Field("address") String address,
                                       @Field("password") String password,
                                       @Field("password_confirmation") String password_confirmation);

    @FormUrlEncoded
    @POST("signin")
    Call<ResponseBody> loginRequest();
}
