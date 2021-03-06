package com.gamelanbekonang.api;

import com.gamelanbekonang.beans.ResponseApiModel;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 4/5/2018.
 */

public interface BaseApiService {

    String BASE_URL_IMAGE = "http://gamelanwirun.com/images/iklans";
    String BASE_URL_UPDATE_PROFIL ="http://gamelanwirun.com/api/v1/user/myprofile/";
    String BASE_URL_IMAGE_USER = "http://gamelanwirun.com/images/users/";
    String BASE_URL_CHANGE_PASS = "http://gamelanwirun.com/api/v1/user/changePassword/";
    String BASE_URL_LUPA_PASS = "http://gamelanwirun.com/api/v1/user/recover";


    @FormUrlEncoded
    @POST("signin")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("notelp") String notelp,
                                       @Field("password") String password);



    @FormUrlEncoded
    @POST("iklan/{iklanId}/countview")
    Call <ResponseBody> viewCount(@Path("iklanId") String iklanId,
                                  @Field("Key") String Key,
                                  @Field("Value") String Value);

    @Multipart
    @POST("myprofile")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("myprofile")
    Call<ResponseBody> updateProfile(
            @Query("token") String token,
            @Field("id")  String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("address") String address,
            @Field("notelp") String notelp,
            @Field("image") String image);

    @FormUrlEncoded
    @POST("changePassword")
    Call<ResponseBody> ubahPassword(@Query("token") String token,
                                      @Field("current_password") String current_password,
                                      @Field("new_password") String new_password,
                                      @Field("new_password_confirmation") String new_password_confirmation);

    @GET("myiklan")
    Call<ResponseBody> MyData(@Query("token") String token);

    @GET("profile/{id}")
    Call <ResponseBody> getInfo(@Path("id") String id);

    @FormUrlEncoded
    @POST("recover")
    Call<ResponseBody> lupapass(@Field("email") String email);

    @GET("logout")
    Call<ResponseBody> LogOut(@Query("token")String token);

    @Multipart
    @POST("changepic")
    Call<ResponseApiModel> gantiFoto(@Part MultipartBody.Part image,
                                     @Query("token") String token);
}
