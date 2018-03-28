package com.gamelanbekonang.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lenovo on 28/03/2018.
 */

public class IklanList {
    @SerializedName("iklans")
    @Expose
    private List<Iklan> iklans = null;

    public List<Iklan> getIklans() {
        return iklans;
    }

    public void setIklans(List<Iklan> iklans) {
        this.iklans = iklans;
    }
}
