package com.lingayatpanchasanagam.sbjm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vaibhav on 10-01-2018.
 */

public class DistrictsModel
{
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("districtDetails")
    @Expose
    private List<Districts> districts;

    public Boolean getSuccess() {
        return success;
    }

    public List<Districts> getDistricts() {
        return districts;
    }
}
