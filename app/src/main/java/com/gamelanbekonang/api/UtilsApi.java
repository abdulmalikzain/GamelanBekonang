package com.gamelanbekonang.api;

/**
 * Created by user on 4/5/2018.
 */

public class UtilsApi {
    // ini adalah localhost.
    public static final String BASE_URL_API = "http://bekonangstore.herokuapp.com/api/v1/user/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
