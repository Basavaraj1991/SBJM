package com.lingayatpanchasanagam.sbjm.util;

import android.content.Context;
import android.content.Intent;

import com.lingayatpanchasanagam.sbjm.LingayatPanchasangamActivity;
import com.lingayatpanchasanagam.sbjm.ListOfMembersActivity;

/**
 * Created by Basavaraj Navi on 09/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public class Navigator {
    public static void navigateToLingayatPanchasangam(Context context){
        Intent intent = new Intent(context, LingayatPanchasangamActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToMembersActivity(Context context){
        Intent intent = new Intent(context, ListOfMembersActivity.class);
        context.startActivity(intent);
    }
}
