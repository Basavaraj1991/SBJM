package com.lingayatpanchasanagam.sbjm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vaibhav on 11-01-2018.
 */

public class TeamMembers
{
    @SerializedName("memberId")
    @Expose
    private String memberId;

    @SerializedName("registeredOn")
    @Expose
    private String registeredOn;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("talukId")
    @Expose
    private String talukId;

    @SerializedName("talukName")
    @Expose
    private String talukName;

    @SerializedName("districtId")
    @Expose
    private String districtId;

    @SerializedName("districtName")
    @Expose
    private String districtName;

    public String getMemberId() {
        return memberId;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getTalukId() {
        return talukId;
    }

    public String getTalukName() {
        return talukName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getDistrictName() {
        return districtName;
    }
}
