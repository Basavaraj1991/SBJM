package com.lingayatpanchasanagam.sbjm;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lingayatpanchasanagam.sbjm.adapter.DashboardMenuAdapter;
import com.lingayatpanchasanagam.sbjm.adapter.ViewPagerAdapter;
import com.lingayatpanchasanagam.sbjm.callback.MenuClickCallback;
import com.lingayatpanchasanagam.sbjm.util.Navigator;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Navigator.navigateToLingayatPanchasangam(this);
                break;
            case 4:
                break;
            default:
                break;
        }
    }
}
