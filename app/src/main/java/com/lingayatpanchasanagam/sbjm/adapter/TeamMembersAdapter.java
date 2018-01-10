package com.lingayatpanchasanagam.sbjm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vaibhav on 11-01-2018.
 */

public class TeamMembersAdapter extends RecyclerView.Adapter<TeamMembersAdapter.ViewHolder>
{
    Context context;
    TeamMemberModule productModel;
    LayoutInflater inflater;
    private int lastPosition = -1;


    public TeamMembersAdapter(Context context, TeamMemberModule productModel)
    {
        this.context = context;
        this.productModel = productModel;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public TeamMembersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.team_member_recyclerview_items, parent, false);
        return new TeamMembersAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TeamMembersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        holder.name.setText(productModel.getTeamMembers().get(position).getName());
        holder.phone.setText("Phone : "+productModel.getTeamMembers().get(position).getPhone());
        holder.email.setText("Email : "+productModel.getTeamMembers().get(position).getEmail());
        holder.taluk.setText("Taluk : "+productModel.getTeamMembers().get(position).getTalukName());
        holder.district.setText("District : "+productModel.getTeamMembers().get(position).getDistrictName());

        setAnimation(holder.itemView, position);

    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount()
    {
        return productModel.getTeamMembers().size();
    }



    static class ViewHolder  extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.email)
        TextView email;
        @BindView(R.id.taluk)
        TextView taluk;
        @BindView(R.id.district)
        TextView district;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

}

