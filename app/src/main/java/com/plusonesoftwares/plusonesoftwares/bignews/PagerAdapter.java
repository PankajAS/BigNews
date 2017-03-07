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


                HomeFragment tab = new HomeFragment();
                return  tab;

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

