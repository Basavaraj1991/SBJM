package com.lingayatpanchasanagam.sbjm.api;

import android.support.v7.util.SortedList;

import com.lingayatpanchasanagam.sbjm.model.DistrictsModel;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;
import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by Vaibhav on 10-01-2018.
 */

public interface AllScreenAPIs
{
    @POST("/get_apis/getAllDistricts")
    void getAllDistricts(@Body TypedInput input, Callback<DistrictsModel> districtsModelCallback);

    @POST("/post_apis/getTeamMemberDetailsByDistrictId")
    void getTeamMembersByDistrict(@Body TypedInput input, Callback<TeamMemberModule> teamMemberModuleCallback);
}
