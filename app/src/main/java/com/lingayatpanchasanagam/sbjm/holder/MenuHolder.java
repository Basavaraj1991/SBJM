package com.lingayatpanchasanagam.sbjm.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lingayatpanchasanagam.sbjm.R;

/**
 * Created by Basavaraj Navi on 08/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public class MenuHolder extends RecyclerView.ViewHolder {

    public TextView menuItemTv;

    public MenuHolder(View itemView) {
        super(itemView);
        menuItemTv = (TextView) itemView.findViewById(R.id.menuItem);
    }
}
