package com.icyfruits.overapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by m09-5 on 2016-10-18.
 */

public class PageAdapter extends FragmentPagerAdapter {

    Fragment[] frags=new Fragment[3];
    public PageAdapter(FragmentManager fm) {
        super(fm);
        frags[0]=new Page1Fragment();
        frags[1]=new Page2Fragment();
        frags[2]=new Page3Fragment();


    }

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }

    @Override
    public int getCount() {
        return frags.length;
    }
}
