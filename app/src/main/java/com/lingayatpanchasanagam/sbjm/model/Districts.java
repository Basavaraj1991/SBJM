package com.lingayatpanchasanagam.sbjm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vaibhav on 10-01-2018.
 */

public class Districts
{
    @SerializedName("districtId")
    @Expose
    private String districtId;

    @SerializedName("districtName")
    @Expose
    private String districtName;

    public String getDistrictId() {
        return districtId;
    }

    public String getDistrictName() {
        return districtName;
    }
}
