package com.gamelanbekonang.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
<<<<<<< Updated upstream
import retrofit2.http.Header;
import retrofit2.http.PATCH;
=======
import retrofit2.http.Multipart;
>>>>>>> Stashed changes
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by user on 4/5/2018.
 */

public interface BaseApiService {

//    String BASE_API_URL = "http://bekonang-store.000webhostapp.com";
    String BASE_URL_IMAGE = "http://bekonang-store.000webhostapp.com/images/iklans";


    @FormUrlEncoded
    @POST("signin")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("notelp") String notelp,
//                                       @Field("address") String address,
                                       @Field("password") String password);
//                                       @Field("password_confirmation") String password_confirmation);


<<<<<<< Updated upstream
=======


    @FormUrlEncoded
    @POST("iklan/{iklanId}/countview")
    Call <ResponseBody> viewCount(@Path("iklanId") String iklanId,
                                  @Field("Key") String Key,
                                  @Field("Value") String Value);
>>>>>>> Stashed changes

    @Multipart
    @POST("myprofile")
    Call<Result> postImage(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("myprofile")
    Call<ResponseBody> updateProfile(
            @Field("name") String name,
            @Field("email") String email,
            @Field("address") String address,
            @Field("notelp") String notelp);
//                                     @Field("image") String image);
    String BASE_URL_UPDATE_PROFIL ="https://bekonang-store.000webhostapp.com/api/user/myprofile/";
    String BASE_URL_IMAGE_USER = "http://bekonang-store.000webhostapp.com/images/users/";
}
