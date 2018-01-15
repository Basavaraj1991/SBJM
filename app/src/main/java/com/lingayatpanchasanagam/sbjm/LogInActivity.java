package com.lingayatpanchasanagam.sbjm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.api.ApiKey;
import com.lingayatpanchasanagam.sbjm.api.LoginApi;
import com.lingayatpanchasanagam.sbjm.api.SMSApi;
import com.lingayatpanchasanagam.sbjm.model.ForgotPasswordOtpModule;
import com.lingayatpanchasanagam.sbjm.model.TeamMemberModule;
import com.lingayatpanchasanagam.sbjm.model.UpdatePasswordModule;
import com.lingayatpanchasanagam.sbjm.service.SMSServiceGenerator;
import com.lingayatpanchasanagam.sbjm.service.ServiceGenerator;

import org.json.JSONObject;

import java.util.Random;

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

    @BindView(R.id.btnResetPwd)
    TextView resetButton;


    String phoneNumber;
    String password;



    //Wifi And Data check
    ConnectivityManager connMgr;
    android.net.NetworkInfo wifi ;
    android.net.NetworkInfo mobile ;





    private Context context;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText phoneNum;
    Button proceedBtn;
    RelativeLayout forgotFirstrl;
    RelativeLayout forgotSecondrl;
    RelativeLayout newPassrl;
    EditText updatePassword;
    Button updateBtn;
    TextView otpText;
    EditText otpNum;
    Button submitBtn;
    String forgotPassPhoneNum;
    String otpValue;

    SMSApi smsApi;










    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        random = new Random();
        int otp = random.nextInt(1000)+100;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginApi = ServiceGenerator.createService(LoginApi.class);
        smsApi = SMSServiceGenerator.createService(SMSApi.class);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.loginTxt));

        //Stored Vlaues in SharedPreference File
        loginPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        phoneNumber = loginPreferences.getString("username", null);
        password = loginPreferences.getString("password", null);



        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("CutPasteId")
            @Override
            public void onClick(View v)
            {

                builder = new AlertDialog.Builder(LogInActivity.this);
                View forgotPassDialogView = LayoutInflater.from(LogInActivity.this).inflate(R.layout.fogot_password_dailog, null);
                builder.setView(forgotPassDialogView);
                phoneNum = (EditText) forgotPassDialogView.findViewById(R.id.phone);
                proceedBtn = (Button) forgotPassDialogView.findViewById(R.id.proceedBtn);
                forgotFirstrl = (RelativeLayout) forgotPassDialogView.findViewById(R.id.phonerl);
                forgotSecondrl = (RelativeLayout) forgotPassDialogView.findViewById(R.id.otprl);
                newPassrl = (RelativeLayout) forgotPassDialogView.findViewById(R.id.newPassrl);

                otpText = (TextView) forgotPassDialogView.findViewById(R.id.otptext);
                otpNum = (EditText) forgotPassDialogView.findViewById(R.id.phoneOtp);
                submitBtn = (Button) forgotPassDialogView.findViewById(R.id.submitBtn);


                updatePassword = (EditText) forgotPassDialogView.findViewById(R.id.newPassword);
                updateBtn = (Button) forgotPassDialogView.findViewById(R.id.updatePassword);


                dialog = builder.show();
                dialog.setCanceledOnTouchOutside(false);

                proceedBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        assert connMgr != null;
                        wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                        if( wifi.isConnected() || mobile.isConnected())
                        {
                            if(phoneNum.getText().toString().equals(""))
                            {
                                phoneNum.setError(context.getString(R.string.mandatoryTxt));
                            }
                            else
                            {
                                if(phoneNum.getText().toString().length() != 10)
                                {
                                    phoneNum.setError(context.getString(R.string.mobileNumError));
                                }
                                else
                                {
                                    if(phoneNum.getText().toString().matches(getString(R.string.onlydigitsPattern)))
                                    {

                                        forgotPassPhoneNum = phoneNum.getText().toString();


                                        // API Integration
                                        progressBar = new ProgressDialog(LogInActivity.this, R.style.MyTheme);
                                        progressBar.setCancelable(false);
                                        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
                                        progressBar.show();

                                        JSONObject cred = new JSONObject();
                                        try
                                        {
                                            cred.put("phone", forgotPassPhoneNum);
                                            TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));


                                            loginApi.checkPhoneExistOrNot(input, new Callback<ForgotPasswordOtpModule>()
                                            {


                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void success(ForgotPasswordOtpModule forgotPasswordOtpModule, Response response)
                                                {


                                                    if (forgotPasswordOtpModule.isSuccess())
                                                    {
                                                        otpValue = String.valueOf(forgotPasswordOtpModule.getOtp());
                                                        progressBar.dismiss();


                                                        smsApi.sendSms(ApiKey.SMS_API_KEY, forgotPassPhoneNum, otpValue, new Callback<com.squareup.okhttp.Response>() {
                                                            @Override
                                                            public void success(com.squareup.okhttp.Response response, Response response2) {

                                                            }

                                                            @Override
                                                            public void failure(RetrofitError error) {

                                                            }
                                                        });




                                                        otpText.setText("We Have Sent Otp to your Number "+phoneNum.getText().toString().substring(0,2)+"******"+phoneNum.getText().toString().substring(phoneNum.getText().toString().length() -2));
                                                        forgotFirstrl.setVisibility(View.GONE);
                                                        phoneNum.setVisibility(View.GONE);
                                                        proceedBtn.setVisibility(View.GONE);
                                                        forgotSecondrl.setVisibility(View.VISIBLE);






                                                        Log.d("otpFP", otpValue);

                                                    }
                                                    else
                                                    {
                                                        progressBar.dismiss();
                                                        dialog.cancel();
                                                        showToastMsgFun(getResources().getString(R.string.phoneNotReg));
                                                    }




                                                }

                                                @Override
                                                public void failure(RetrofitError error)
                                                {
                                                    progressBar.dismiss();
                                                    dialog.cancel();
                                                    showToastMsgFun(getResources().getString(R.string.phoneNotReg));
                                                }
                                            });

                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    else
                                    {
                                        phoneNum.setError(context.getString(R.string.errorOnlyDigits));
                                    }
                                }
                            }

                        }
                        else
                        {
                            showToastMsgFun(getResources().getString(R.string.noInternet));
                            dialog.cancel();
                        }

                    }
                });

                submitBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                            if (otpNum.getText().toString().equals(""))
                            {
                                otpNum.setError(getString(R.string.mandatoryTxt));
                            }
                            else
                            {
                                if (otpNum.getText().toString().length() != 6)
                                {
                                    otpNum.setError(getString(R.string.otpDigitError));
                                }
                                else
                                {
                                    if (otpNum.getText().toString().matches(getString(R.string.onlydigitsPattern)))
                                    {

                                        if(otpNum.getText().toString().equals(otpValue))
                                        {
                                            forgotSecondrl.setVisibility(View.GONE);
                                            newPassrl.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            otpNum.setError(getString(R.string.otpNotMatched));
                                        }

                                    }
                                    else
                                    {
                                        otpNum.setError(getString(R.string.errorOnlyDigits));
                                    }
                                }
                            }

                    }
                });

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (updatePassword.getText().toString().equals(""))
                        {
                            updatePassword.setError(getString(R.string.mandatoryTxt));
                        }
                        else
                        {

                            connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                            assert connMgr != null;
                            wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                            mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                            if (wifi.isConnected() || mobile.isConnected())
                            {
                                // API Integration
                                progressBar = new ProgressDialog(LogInActivity.this, R.style.MyTheme);
                                progressBar.setCancelable(false);
                                progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
                                progressBar.show();

                                JSONObject cred = new JSONObject();
                                try
                                {
                                    cred.put("phone", forgotPassPhoneNum);
                                    cred.put("password", updatePassword.getText());
                                    TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));


                                    loginApi.updatePassword(input, new Callback<UpdatePasswordModule>()
                                    {

                                        @Override
                                        public void success(UpdatePasswordModule updatePasswordModule, Response response) {
                                            progressBar.dismiss();
                                            dialog.cancel();
                                            showToastMsgFun(getString(R.string.passwordUpdated));
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            progressBar.dismiss();
                                            dialog.cancel();
                                            Log.d("error", String.valueOf(error));
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                showToastMsgFun(getResources().getString(R.string.noInternet));
                            }





                        }
                    }
                });




            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editpassword.getText().toString().equalsIgnoreCase("1234")) {
                    Toast.makeText(LogInActivity.this, "Please Reset your password before login", Toast.LENGTH_LONG).show();

                } else {
                    // Username And Password VAlidation
                    phoneNumber = editphoneNumber.getText().toString();
                    password = editpassword.getText().toString();

                    if (phoneNumber.equals("")) {
                        editphoneNumber.setError(getResources().getString(R.string.mandtory_text));
                    } else if (phoneNumber.length() != 10) {
                        editphoneNumber.setError(getResources().getString(R.string.number_matches));
                    } else if (password.equals("")) {
                        editpassword.setError(getResources().getString(R.string.mandtory_text));
                    } else {

                        connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        assert connMgr != null;
                        wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                        if (wifi.isConnected() || mobile.isConnected()) {
                            // API Integration
                            progressBar = new ProgressDialog(LogInActivity.this, R.style.MyTheme);
                            progressBar.setCancelable(false);
                            progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
                            progressBar.show();

                            JSONObject cred = new JSONObject();
                            try {
                                cred.put("phone", phoneNumber);
                                cred.put("password", password);
                                TypedInput input = new TypedByteArray("application/json", cred.toString().getBytes("UTF-8"));


                                loginApi.getAllLoginDetails(input, new Callback<TeamMemberModule>() {

                                    @Override
                                    public void success(TeamMemberModule teamMemberModule, Response response) {
                                        if (teamMemberModule.getSuccess()) {
                                            editor = loginPreferences.edit();
                                            if (checkBox.isChecked()) {
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
                                        } else {
                                            progressBar.dismiss();
                                            showToastMsgFun(getResources().getString(R.string.invalidCredintialsTxt));
                                        }


                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        progressBar.dismiss();
                                        Log.d("error", String.valueOf(error));
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            showToastMsgFun(getResources().getString(R.string.noInternet));
                        }


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

    private void showResetPasswordDialog(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }


}