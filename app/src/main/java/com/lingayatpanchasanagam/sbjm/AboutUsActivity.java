package com.lingayatpanchasanagam.sbjm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends AppCompatActivity
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.aboutPanchamsali)
    TextView aboutPanchamsaliBtn;


    @BindView(R.id.aboutSBJM)
    TextView aboutSBJM;

    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        /* Tool bar starts here */
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.back_arrow_white);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(getString(R.string.back));
		/* Toolbar Ends here */


        aboutPanchamsaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://cloudspace.idrsolutions.com:8181/HTML_Page_Extraction/output/32df7e3c-18b5-4776-8d25-0a7bf82927e5/about_panchamsali/index.html?page=1";
                openWebViewFun(url);
            }
        });

        aboutSBJM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://cloudspace.idrsolutions.com:8181/HTML_Page_Extraction/output/19ac3d70-f4aa-4740-bcd7-f4e45b34ede2/about_swamiji/index.html?page=1";
                openWebViewFun(url);
            }
        });

    }




    //Web view connect
    private void openWebViewFun(String url)
    {
        Intent i = new Intent(this,WebviewActivity.class);
        i.putExtra("link",url);
        startActivity(i);
    }

    //Exit Application press backButton
    public void onBackPressed() {
        Intent intent = new Intent(AboutUsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.only_back_btn, menu);
        return super.onCreateOptionsMenu(menu);
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




}
