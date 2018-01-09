package com.lingayatpanchasanagam.sbjm.model;

import java.util.ArrayList;

/**
 * Created by Basavaraj Navi on 09/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */

public class Parent {

    String divisionName;
    private ArrayList<Child> children;

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }
}
