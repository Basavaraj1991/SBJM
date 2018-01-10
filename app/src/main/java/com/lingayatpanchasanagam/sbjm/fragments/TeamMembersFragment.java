package com.lingayatpanchasanagam.sbjm.fragments;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.adapter.TeamMembersAdapter;
import com.lingayatpanchasanagam.sbjm.api.AllScreenAPIs;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;
import com.lingayatpanchasanagam.sbjm.service.ServiceGenerator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class TeamMembersFragment extends Fragment
{
    @BindView(R.id.rvAllUsers1)
    RecyclerView recyclerView;

    AllScreenAPIs allScreenAPIs;
    TeamMembersAdapter adapter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    TeamMemberModule teamMemberModule;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public ProgressDialog progressBar;
    ConnectivityManager connMgr;
    android.net.NetworkInfo wifi ;
    android.net.NetworkInfo mobile ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_team_members, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(getActivity());
        getActivity().setTitle("Shop By Brands");

        allScreenAPIs = ServiceGenerator.createService(AllScreenAPIs.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvAllUsers1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getItems();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getItems();
                swipeRefresh.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void getItems()
    {
        JSONObject cred = new JSONObject();
        try
        {
            cred.put("districtId", getArguments().getString("districtId"));
            TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));
            progressBar = new ProgressDialog(getActivity(),R.style.MyTheme);
            progressBar.setCancelable(false);
            progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
            progressBar.show();

            allScreenAPIs.getTeamMembersByDistrict(input,  new Callback<TeamMemberModule>()
            {

                @Override
                public void success(TeamMemberModule teamMemberModule, Response response)
                {
                    if(teamMemberModule.getSuccess())
                    {
                        adapter = new TeamMembersAdapter(getActivity(),teamMemberModule);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        showToastMsgFun(getResources().getString(R.string.noTeamMember));
                    }

                    progressBar.dismiss();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressBar.dismiss();
                    Log.d("error",String.valueOf(error));
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //Toast Message Print Function
    private void showToastMsgFun(String s)
    {
        View customToastroot =LayoutInflater.from(getContext()).inflate(R.layout.mycustom_toast, null);
        TextView toastMsg = (TextView) customToastroot.findViewById(R.id.textView1);
        toastMsg.setText(s);

        Toast customtoast=new Toast(getContext());
        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.BOTTOM,0,200);
        customtoast.setDuration(Toast.LENGTH_SHORT);
        customtoast.show();
    }

}
