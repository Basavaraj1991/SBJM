package com.lingayatpanchasanagam.sbjm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.callback.MenuClickCallback;
import com.lingayatpanchasanagam.sbjm.holder.MenuHolder;


/**
 * Created by Basavaraj Navi on 08/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public class DashboardMenuAdapter extends RecyclerView.Adapter<MenuHolder> {
    Context context;
    String menuItems[];
    MenuClickCallback menuClickCallback;

    public DashboardMenuAdapter(Context context, String[] menuItems, MenuClickCallback menuClickCallback) {
        this.context = context;
        this.menuItems = menuItems;
        this.menuClickCallback = menuClickCallback;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_list,parent,false);
        MenuHolder holder = new MenuHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, final int position) {
        holder.menuItemTv.setText(menuItems[position]);
        holder.menuItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuClickCallback.onMenuItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.length;
    }
}
