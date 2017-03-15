package com.plusonesoftwares.plusonesoftwares.bignews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.DiscoverFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.HomeFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.MenuFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Plus 3 on 01-03-2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int tabCount;
    String[] tabBarTitles;
    FragmentManager fm;
    Map<Integer, String> mFragmentTags = new HashMap<>();
    FragmentManager mFragmentManager;

    public PagerAdapter(FragmentManager fm, String[] tabBarTitles) {
        super(fm);
        this.mFragmentManager = fm;
        this.tabBarTitles = tabBarTitles;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment flip = new HomeFragment();
                return flip;
            case 1:
                MenuFragment menu = new MenuFragment();

                return menu;
            case 2:
                DiscoverFragment discover = new DiscoverFragment();

                return discover;
            default:
                return null;
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if(obj instanceof Fragment){
            Fragment f = (Fragment)obj;
            String tag = f.getTag();
            mFragmentTags.put(position,tag);

        }
        return obj;
    }

    public Fragment getFragment(int position){
        String tag  = mFragmentTags.get(position);
        if(tag==null){
            return null;
        }
        return mFragmentManager.findFragmentByTag(tag);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabBarTitles[position];
    }

    @Override
    public int getCount() {
        return tabBarTitles.length;
    }
}

