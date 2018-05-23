package com.gamelanbekonang.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 27/03/2018.
 */

public interface ApiService {


    String BASE_URL_IMAGEIKLAN = "http://bekonang-store.000webhostapp.com/images/iklans/";
    String BASE_URL_IMAGEUSER = "http://bekonang-store.000webhostapp.com/images/users/";

    @GET("iklan")
    Call<ResponseBody> getData();

    @GET("iklan/{iklanId}")
    Call<ResponseBody> getDataId(@Path("iklanId") String iklanId);

    @GET("category/{categoryId}")
    Call <ResponseBody> getCategory(@Path("categoryId") String categoryId);


    @FormUrlEncoded
    @POST("wishlist")
    Call <ResponseBody> postFavorite(@Query("token") String token,
                                     @Field("user_id") String userId,
                                     @Field("iklan_id") String iklanId);

    @GET("wishlist")
    Call <ResponseBody> getFavorite(@Query("token") String token,
                                     @Field("user_id") String userId,
                                     @Field("iklan_id") String iklanId);

    @FormUrlEncoded
    @POST("iklan/{iklanId}/countview")
    Call <ResponseBody> viewCount(@Path("iklanId") String iklanId,
                                  @Field("_method") String Key);


}
