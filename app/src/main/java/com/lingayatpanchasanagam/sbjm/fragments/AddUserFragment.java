package com.lingayatpanchasanagam.sbjm.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.api.AllScreenAPIs;
import com.lingayatpanchasanagam.sbjm.service.ServiceGenerator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class AddUserFragment extends Fragment
{

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.subscription)
    EditText subscription;

    @BindView(R.id.addUser)
    Button addUserBtn;

    String userName;
    String userPhone;
    String userEmail;
    String userAddress;
    String userSubscriptionDetails;

    AllScreenAPIs allScreenAPIs;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public ProgressDialog progressBar;
    ConnectivityManager connMgr;
    android.net.NetworkInfo wifi ;
    android.net.NetworkInfo mobile ;


    //shared preference
    public static final String MyPREFERENCES = "userDetails" ;
    SharedPreferences sharedpreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(getActivity());
        getActivity().setTitle("Shop By Brands");
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        allScreenAPIs = ServiceGenerator.createService(AllScreenAPIs.class);

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = name.getText().toString();
                userPhone = phone.getText().toString();
                userEmail = email.getText().toString();
                userAddress = address.getText().toString();
                userSubscriptionDetails = subscription.getText().toString();

                validationFun();
            }
        });
    }

    //validate details
    private void validationFun()
    {
        if (userName.equals(""))
        {
            name.setError(getResources().getString(R.string.mandtory_text));
        }
        else if (userPhone.equals(""))
        {
            phone.setError(getResources().getString(R.string.mandtory_text));
        }
        else if (userPhone.length() != 10)
        {
            phone.setError(getResources().getString(R.string.number_matches));
        }
        else if (userEmail.equals(""))
        {
            email.setError(getResources().getString(R.string.mandtory_text));
        }
        else if(!userEmail.matches(getResources().getString(R.string.emailPattern)))
        {
            email.setError(getResources().getString(R.string.invalidEmailError));
        }
        else if (userAddress.equals(""))
        {
            address.setError(getResources().getString(R.string.mandtory_text));
        }
        else
        {

            connMgr = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connMgr != null;
            wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if( wifi.isConnected() || mobile.isConnected())
            {
                // API Integration
                progressBar = new ProgressDialog(getActivity(),R.style.MyTheme);
                progressBar.setCancelable(false);
                progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
                progressBar.show();

                JSONObject cred = new JSONObject();
                try
                {
                    cred.put("team_member_id", sharedpreferences.getString("memberId", null));
                    cred.put("name", userName);
                    cred.put("phone", userPhone);
                    cred.put("email", userEmail);
                    cred.put("address", userAddress);
                    cred.put("subscription_details", userSubscriptionDetails);
                    TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));


                    allScreenAPIs.addUser(input,  new Callback<Response>()
                    {
                        @Override
                        public void success(Response response, Response response2)
                        {
                            progressBar.dismiss();
                            showToastMsgFun(getResources().getString(R.string.userAddedTxt));

                            fragment = new LingayatPanchasangamFragment();
                            fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_frame, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
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
            else
            {
                showToastMsgFun(getResources().getString(R.string.noInternet));
            }
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
