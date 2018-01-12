package com.lingayatpanchasanagam.sbjm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.api.LoginApi;
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

public class LogInActivity extends AppCompatActivity {

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor editor;
    ProgressDialog progressBar;
    LoginApi loginApi;
    TeamMemberModule loginModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.edit_username)
    EditText editphoneNumber;

    @BindView(R.id.edit_password)
    EditText editpassword;

    @BindView(R.id.check_box)
    CheckBox checkBox;

    @BindView(R.id.btn_login)
    Button buttonLogin;


    String phoneNumber;
    String password;



    //Wifi And Data check
    ConnectivityManager connMgr;
    android.net.NetworkInfo wifi ;
    android.net.NetworkInfo mobile ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginApi = ServiceGenerator.createService(LoginApi.class);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.loginTxt));

        //Stored Vlaues in SharedPreference File
        loginPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        phoneNumber = loginPreferences.getString("username", null);
        password = loginPreferences.getString("password", null);

        if (phoneNumber == (null)) {
            editphoneNumber.setText("");
        } else {
            editphoneNumber.setText(phoneNumber);
        }

        if (password == (null)) {
            editpassword.setText("");
        } else {
            editpassword.setText(password);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Username And Password VAlidation
                phoneNumber = editphoneNumber.getText().toString();
                password = editpassword.getText().toString();

                if (phoneNumber.equals(""))
                {
                    editphoneNumber.setError(getResources().getString(R.string.mandtory_text));
                }
                else if (phoneNumber.length() != 10)
                {
                    editphoneNumber.setError(getResources().getString(R.string.number_matches));
                }
                else if (password.equals(""))
                {
                    editpassword.setError(getResources().getString(R.string.mandtory_text));
                }
                else
                {

                    connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    assert connMgr != null;
                    wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                    if( wifi.isConnected() || mobile.isConnected())
                    {
                        // API Integration

                        JSONObject cred = new JSONObject();
                        try
                        {
                            cred.put("phone", phoneNumber);
                            cred.put("password", password);
                            TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));
                            progressBar = new ProgressDialog(LogInActivity.this,R.style.MyTheme);
                            progressBar.setCancelable(false);
                            progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
                            progressBar.show();

                            loginApi.getAllLoginDetails(input,  new Callback<TeamMemberModule>()
                            {

                                @Override
                                public void success(TeamMemberModule teamMemberModule, Response response)
                                {
                                    if(teamMemberModule.getSuccess())
                                    {
                                        editor = loginPreferences.edit();
                                        if (checkBox.isChecked())
                                        {
                                            editor.putString("username", phoneNumber);
                                            editor.putString("password", password);
                                            editor.apply();
                                        }
                                        editor.putString("isLoggedIn", "yes");
                                        editor.putString("phoneNumber", phoneNumber);
                                        editor.putString("pass", password);

                                        editor.putString("memberId", teamMemberModule.getTeamMembers().get(0).getMemberId());
                                        editor.putString("registeredOn", teamMemberModule.getTeamMembers().get(0).getRegisteredOn());
                                        editor.putString("name", teamMemberModule.getTeamMembers().get(0).getName());
                                        editor.putString("phone", teamMemberModule.getTeamMembers().get(0).getPhone());
                                        editor.putString("email", teamMemberModule.getTeamMembers().get(0).getEmail());
                                        editor.putString("talukId", teamMemberModule.getTeamMembers().get(0).getTalukId());
                                        editor.putString("talukName", teamMemberModule.getTeamMembers().get(0).getTalukName());
                                        editor.putString("districtId", teamMemberModule.getTeamMembers().get(0).getDistrictId());
                                        editor.putString("districtName", teamMemberModule.getTeamMembers().get(0).getDistrictName());
                                        editor.apply();

                                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();

                                        progressBar.dismiss();
                                    }
                                    else
                                    {
                                        progressBar.dismiss();
                                        showToastMsgFun(getResources().getString(R.string.invalidCredintialsTxt));
                                    }


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
        });


    }


    //Exit Application press backButton
    public void onBackPressed() {
        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Toast Message Print Function
    private void showToastMsgFun(String s) {
        Context context = getApplicationContext();
        LayoutInflater inflater = getLayoutInflater();
        View customToastroot = inflater.inflate(R.layout.mycustom_toast, null);
        TextView toastMsg = (TextView) customToastroot.findViewById(R.id.textView1);
        toastMsg.setText(s);

        Toast customtoast = new Toast(context);
        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.BOTTOM, 0, 200);
        customtoast.setDuration(Toast.LENGTH_SHORT);
        customtoast.show();
    }


}