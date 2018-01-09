package com.lingayatpanchasanagam.sbjm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lingayatpanchasanagam.sbjm.util.Navigator;

public class LingayatPanchasangamActivity extends AppCompatActivity implements View.OnClickListener{

    TextView listOfMembers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingayat_panchasangam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listOfMembers = findViewById(R.id.listOfMembersTv);
        listOfMembers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.listOfMembersTv:
                Navigator.navigateToMembersActivity(this);
                break;
        }
    }
}
