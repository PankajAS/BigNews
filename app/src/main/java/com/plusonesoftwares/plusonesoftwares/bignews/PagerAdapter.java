package com.plusonesoftwares.plusonesoftwares.bignews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.DiscoverFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.HomeFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.MenuFragment;

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
                MenuFragment menu = new MenuFragment();
                return  menu;
            case 2:
                DiscoverFragment discover = new DiscoverFragment();
                return  discover;
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

