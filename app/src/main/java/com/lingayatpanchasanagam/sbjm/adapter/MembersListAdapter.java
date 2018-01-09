package com.lingayatpanchasanagam.sbjm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.model.Child;
import com.lingayatpanchasanagam.sbjm.model.Parent;

import java.util.ArrayList;

/**
 * Created by Basavaraj Navi on 09/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public class MembersListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Parent> deptList;

    public MembersListAdapter(Context context, ArrayList<Parent> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> productList = deptList.get(groupPosition).getChildren();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Child detailInfo = (Child) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_items, null);
        }

        TextView sequence = (TextView) view.findViewById(R.id.taluk);
        sequence.setText(detailInfo.getTaluk().trim());
        TextView childItem = (TextView) view.findViewById(R.id.name);
        childItem.setText(detailInfo.getName().trim());
        TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        phoneNumber.setText(detailInfo.getNumber().trim());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<Child> productList = deptList.get(groupPosition).getChildren();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        Parent headerInfo = (Parent) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_items, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(headerInfo.getDivisionName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

