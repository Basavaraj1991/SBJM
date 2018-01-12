package com.lingayatpanchasanagam.sbjm.api;

import com.lingayatpanchasanagam.sbjm.model.DistrictsModel;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
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

    @POST("/post_apis/addUserDetails")
    void addUser(@Body TypedInput input, Callback<Response> responseCallback);
}
