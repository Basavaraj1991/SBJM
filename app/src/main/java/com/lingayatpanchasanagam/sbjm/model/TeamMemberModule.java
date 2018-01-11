package com.lingayatpanchasanagam.sbjm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vaibhav on 11-01-2018.
 */

public class TeamMemberModule
{
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("teamMemberDetails")
    @Expose
    private List<TeamMembers> teamMembers;

    public Boolean getSuccess() {
        return success;
    }

    public List<TeamMembers> getTeamMembers() {
        return teamMembers;
    }
}
