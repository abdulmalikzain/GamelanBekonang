package com.gamelanbekonang.beans;

import com.google.gson.annotations.SerializedName;

public class ResponseApiModel {


    @SerializedName("response")
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
