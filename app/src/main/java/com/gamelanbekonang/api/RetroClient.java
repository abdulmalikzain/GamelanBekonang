package com.gamelanbekonang.api;

import com.gamelanbekonang.beans.Iklan;
import com.gamelanbekonang.beans.Kategori;

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

    private Kategori[] Category;

    public Kategori[] getCategory() {
        return Category;
    }
}
