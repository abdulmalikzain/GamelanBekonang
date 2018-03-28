package com.gamelanbekonang.Api;

import com.gamelanbekonang.beans.IklanList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lenovo on 27/03/2018.
 */

public interface ApiService {

    @GET("api/v1/iklan")
    Call<IklanList> getMyJSONIklan();
}
