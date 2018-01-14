package com.lingayatpanchasanagam.sbjm.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.LogInActivity;
import com.lingayatpanchasanagam.sbjm.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LingayatPanchasangamFragment extends Fragment
{


    @BindView(R.id.aboutlps)
    TextView aboutlps;

    @BindView(R.id.registration)
    TextView registrationBtn;

    @BindView(R.id.listOfMembersTv)
    TextView listOfMembersBtn;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //shared preference
    public static final String MyPREFERENCES = "userDetails" ;
    SharedPreferences sharedpreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_lingayat_panchasangam, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



        aboutlps.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               /* String pdf = "http://gyantechsolutions.com/swamiji/about%20swamiji.pdf";
                String url = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
*/
                fragment = new AboutLPSFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                if(Objects.equals(sharedpreferences.getString("isLoggedIn", null), "yes"))
                {
                    fragment = new AddUserFragment();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    showToastMsgFun(getResources().getString(R.string.mustLoginError));
                    getContext().startActivity(new
                            Intent(getContext(), LogInActivity.class));
                }
            }
        });

        listOfMembersBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fragment = new TeamMembersDistrictsFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

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
