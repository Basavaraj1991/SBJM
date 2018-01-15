package com.lingayatpanchasanagam.sbjm.api;

import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Basavaraj Navi on 14/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public interface SMSApi {
    @GET("/{api_key}/SMS/{phone_number}/{otp}")
    void sendSms(@Path("api_key") String  apikey, @Path("phone_number") String phoneNumber, @Path("otp") String otp, Callback<Response> responseCallback);
}
