package com.lingayatpanchasanagam.sbjm.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
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

import com.google.gson.Gson;
import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.adapter.TeamMembersDistrictsAdapter;
import com.lingayatpanchasanagam.sbjm.api.AllScreenAPIs;
import com.lingayatpanchasanagam.sbjm.api.TeamMemberInteractive;
import com.lingayatpanchasanagam.sbjm.model.DistrictsModel;
import com.lingayatpanchasanagam.sbjm.service.ServiceGenerator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class TeamMembersDistrictsFragment extends Fragment
{
    @BindView(R.id.rvAllUsers1)
    RecyclerView recyclerView;

    AllScreenAPIs allScreenAPIs;
    TeamMembersDistrictsAdapter adapter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    DistrictsModel districtsModel;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public ProgressDialog progressBar;
    ConnectivityManager connMgr;
    android.net.NetworkInfo wifi ;
    android.net.NetworkInfo mobile ;
    Bundle args;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_team_members_districts, container, false);
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
            TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));
            progressBar = new ProgressDialog(getActivity(),R.style.MyTheme);
            progressBar.setCancelable(false);
            progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
            progressBar.show();

            allScreenAPIs.getAllDistricts(input,  new Callback<DistrictsModel>()
            {
                @Override
                public void success(DistrictsModel districtsModel, Response response)
                {
                    if(districtsModel.getSuccess())
                    {
                        adapter = new TeamMembersDistrictsAdapter(getActivity(),districtsModel, new TeamMemberInteractive() {
                            @Override
                            public void teamMemberDetailsByDistrict(String districtId)
                            {

                                fragment = new TeamMembersFragment();
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                args = new Bundle();
                                args.putString("districtId", districtId);
                                fragment.setArguments(args);
                                fragmentTransaction.replace(R.id.fragment_frame, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();



                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        showToastMsgFun(getResources().getString(R.string.noDist));
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
