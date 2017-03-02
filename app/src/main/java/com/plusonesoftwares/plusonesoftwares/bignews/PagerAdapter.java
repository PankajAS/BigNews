package com.plusonesoftwares.plusonesoftwares.bignews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.HomeFragment;

/**
 * Created by Plus 3 on 01-03-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    String[] tabBarTitles;

    public PagerAdapter(FragmentManager fm, int tabCount, String[] tabBarTitles) {
        super(fm);
        this.tabCount = tabCount;
        this.tabBarTitles = tabBarTitles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                HomeFragment home = new HomeFragment();
                return  home;
            case 1:
                HomeFragment home1 = new HomeFragment();
                return  home1;
            case 2:
                HomeFragment home2 = new HomeFragment();
                return  home2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabBarTitles[position];
    }
}

