package com.lingayatpanchasanagam.sbjm.api;

import com.lingayatpanchasanagam.sbjm.model.ForgotPasswordOtpModule;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;
import com.lingayatpanchasanagam.sbjm.model.UpdatePasswordModule;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

public interface LoginApi
{
    @POST("/post_apis/getTeamMemberDetailsByLogIn")
    void getAllLoginDetails(@Body TypedInput input, Callback<TeamMemberModule> teamMemberModuleCallback);

    @POST("/post_apis/checkTeamMemberPhoneExistOrNot")
    void checkPhoneExistOrNot(@Body TypedInput input, Callback<ForgotPasswordOtpModule> forgotPasswordOtpModuleCallback);

    @POST("/post_apis/updateTeamMemberPassword")
    void updatePassword(@Body TypedInput input, Callback<UpdatePasswordModule> updatePasswordModuleCallback);


}
