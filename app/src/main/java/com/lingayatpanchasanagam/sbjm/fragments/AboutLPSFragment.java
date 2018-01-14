package com.lingayatpanchasanagam.sbjm.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.lingayatpanchasanagam.sbjm.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutLPSFragment extends Fragment
{
    @BindView(R.id.webView)
    WebView webView;


    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_about_l, container, false);
        ButterKnife.bind(this, rootView);

        progressBar = new ProgressDialog(getActivity(),R.style.MyTheme);
        progressBar.setCancelable(false);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_bar_style));
        progressBar.show();

        return rootView;
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);


        //webView.loadUrl("https://cloudspace.idrsolutions.com:8181/HTML_Page_Extraction/output/19ac3d70-f4aa-4740-bcd7-f4e45b34ede2/about_swamiji/index.html?page=1");

        webView.loadUrl("https://cloudspace.idrsolutions.com:8181/HTML_Page_Extraction/output/b0c3f884-9b5a-43b4-8404-17679f69f389/inner_lps/index.html?page=1");
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.dismiss();
            }
        }, 2000);


    }



}
