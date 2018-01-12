package com.lingayatpanchasanagam.sbjm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lingayatpanchasanagam.sbjm.adapter.DashboardMenuAdapter;
import com.lingayatpanchasanagam.sbjm.adapter.ViewPagerAdapter;
import com.lingayatpanchasanagam.sbjm.callback.MenuClickCallback;
import com.lingayatpanchasanagam.sbjm.util.Navigator;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, MenuClickCallback {

    int currentPage = 0;
    int NUM_PAGES = 8;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    private ViewPager intro_images;
    Runnable update = new Runnable() {
        public void run() {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            intro_images.setCurrentItem(currentPage++, true);
        }
    };
    Handler handler = new Handler();
    private int[] mImageResources = {
            R.drawable.swamy1,
            R.drawable.swamy2,
            R.drawable.swamy3,
            R.drawable.swamy5,
            R.drawable.swamy6,
            R.drawable.swamy7,
            R.drawable.swamy8,
            R.drawable.swamy9
    };

    RecyclerView mMenuList;
    RecyclerView.LayoutManager layoutManager;
    DashboardMenuAdapter menuAdapter;



    //shared preference
    public static final String MyPREFERENCES = "userDetails" ;
    SharedPreferences sharedpreferences;


    //Wifi And Data check
    ConnectivityManager connMgr;
    android.net.NetworkInfo wifi ;
    android.net.NetworkInfo mobile ;

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mMenuList = (RecyclerView) findViewById(R.id.menuList);
        mAdapter = new ViewPagerAdapter(this, mImageResources);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 2000);
        layoutManager = new LinearLayoutManager(this);
        mMenuList.setHasFixedSize(true);
        mMenuList.setLayoutManager(layoutManager);
        menuAdapter = new DashboardMenuAdapter(this,getResources().getStringArray(R.array.menu_array),this);
        mMenuList.setAdapter(menuAdapter);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onMenuItemClick(int position) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                Navigator.navigateToLingayatPanchamsangamScreenActivity(this);
                break;
            case 4:
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);

        if(Objects.equals(sharedpreferences.getString("isLoggedIn", null), "yes"))
        {
            menu.findItem(R.id.logout).setVisible(true);
            menu.findItem(R.id.sign_in_up).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.sign_in_up).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.sign_in_up)
        {
            connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connMgr != null;
            wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if( wifi.isConnected() || mobile.isConnected())
            {
                Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                startActivity(intent);
            }
            else
            {
                showToastMsgFun(getResources().getString(R.string.noInternet));
            }
        }
        else if (item.getItemId() == R.id.logout)
        {
            connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connMgr != null;
            wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if( wifi.isConnected() || mobile.isConnected())
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("isLoggedIn","no");

                editor.putString("memberId", "");
                editor.putString("registeredOn", "");
                editor.putString("name", "");
                editor.putString("phone", "");
                editor.putString("email", "");
                editor.putString("talukId", "");
                editor.putString("talukName", "");
                editor.putString("districtId", "");
                editor.putString("districtName", "");
                editor.apply();


                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            else
            {
                showToastMsgFun(getResources().getString(R.string.noInternet));
            }
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

    // on back press exit the app
    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis())
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }


    @Override
    protected void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}
