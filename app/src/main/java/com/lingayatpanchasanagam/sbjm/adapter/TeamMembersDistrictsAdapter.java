package com.lingayatpanchasanagam.sbjm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingayatpanchasanagam.sbjm.api.TeamMemberInteractive;
import com.lingayatpanchasanagam.sbjm.model.DistrictsModel;
import com.lingayatpanchasanagam.sbjm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vaibhav on 10-01-2018.
 */

public class TeamMembersDistrictsAdapter extends RecyclerView.Adapter<TeamMembersDistrictsAdapter.ViewHolder>
{
    Context context;
    DistrictsModel productModel;
    LayoutInflater inflater;
    private int lastPosition = -1;
    private TeamMemberInteractive onItemClick;


    public TeamMembersDistrictsAdapter(Context context, DistrictsModel productModel, TeamMemberInteractive teamMemberInteractive)
    {
        this.context = context;
        this.productModel = productModel;
        this.inflater = LayoutInflater.from(context);
        this.onItemClick = teamMemberInteractive;
    }

    @Override
    public TeamMembersDistrictsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.districts_recyclerview_items, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        holder.name.setText(productModel.getDistricts().get(position).getDistrictName());
        holder.distRl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onItemClick.teamMemberDetailsByDistrict(productModel.getDistricts().get(position).getDistrictId());
            }
        });


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
        return productModel.getDistricts().size();
    }



    static class ViewHolder  extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.distRl)
        RelativeLayout distRl;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

}
