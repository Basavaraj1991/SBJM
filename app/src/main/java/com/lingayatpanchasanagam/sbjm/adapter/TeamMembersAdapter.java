package com.lingayatpanchasanagam.sbjm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;

import java.util.Objects;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TeamMembersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        holder.name.setText(productModel.getTeamMembers().get(position).getName());
        holder.phone.setText("Phone : "+productModel.getTeamMembers().get(position).getPhone());
        holder.email.setText("Email : "+productModel.getTeamMembers().get(position).getEmail());
        holder.taluk.setText("Taluk : "+productModel.getTeamMembers().get(position).getTalukName());
        holder.district.setText("District : "+productModel.getTeamMembers().get(position).getDistrictName());

        setAnimation(holder.itemView, position);

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                if(!Objects.equals(productModel.getTeamMembers().get(position).getPhone(), ""))
                {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+productModel.getTeamMembers().get(position).getPhone())));
                }
                else
                {
                    showToastMsgFun(context.getResources().getString(R.string.numempty));
                }
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                if(!Objects.equals(productModel.getTeamMembers().get(position).getEmail(), ""))
                {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("application/octet-stream");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {productModel.getTeamMembers().get(position).getEmail()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry");
                    intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                    context.startActivity(Intent.createChooser(intent, "Send Email"));
                }
                else
                {
                    showToastMsgFun(context.getResources().getString(R.string.invalidEmailError));
                }
            }
        });

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




    //Toast Message Print Function
    private void showToastMsgFun(String s)
    {
        View customToastroot =LayoutInflater.from(context).inflate(R.layout.mycustom_toast, null);
        TextView toastMsg = (TextView) customToastroot.findViewById(R.id.textView1);
        toastMsg.setText(s);

        Toast customtoast=new Toast(context);
        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.BOTTOM,0,200);
        customtoast.setDuration(Toast.LENGTH_SHORT);
        customtoast.show();
    }




}

