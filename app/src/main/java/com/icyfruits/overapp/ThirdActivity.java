package com.icyfruits.overapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Third");

        getSupportActionBar().setTitle("third here");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }
}
