package com.lingayatpanchasanagam.sbjm.api;

import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

public interface LoginApi
{
    @POST("/post_apis/getTeamMemberDetailsByLogIn")
    void getAllLoginDetails(@Body TypedInput input, Callback<TeamMemberModule> teamMemberModuleCallback);

}
