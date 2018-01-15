package com.lingayatpanchasanagam.sbjm;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity extends AppCompatActivity {

    String url;

    @BindView(R.id.webView)
    WebView webView;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public ProgressDialog progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

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

        progressBar = new ProgressDialog(WebviewActivity.this,R.style.MyTheme);
        progressBar.setCancelable(false);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
        progressBar.show();

        url = getIntent().getStringExtra("link");

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.dismiss();
            }
        }, 2000);


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
