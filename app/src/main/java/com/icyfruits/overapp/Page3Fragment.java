package com.icyfruits.overapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by m09-5 on 2016-10-18.
 */

public class Page3Fragment extends Fragment {
    private com.google.android.gms.ads.AdView adview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_page3,container,false);
        this.adview = (AdView) view.findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        return view;
    }
}
