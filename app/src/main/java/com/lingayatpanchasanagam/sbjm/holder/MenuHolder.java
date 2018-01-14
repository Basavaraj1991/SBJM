package com.lingayatpanchasanagam.sbjm.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lingayatpanchasanagam.sbjm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Basavaraj Navi on 08/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public class MenuHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.menuItem)
    public TextView menuItemTv;

    public MenuHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
