package com.plusonesoftwares.plusonesoftwares.bignews;

import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.Toast;

import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.DiscoverFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.HomeFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.MenuFragment;

/**
 * Created by Plus 3 on 01-03-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    String[] tabBarTitles;
    FragmentManager fm;

    public PagerAdapter(FragmentManager fm, int tabCount, String[] tabBarTitles) {
        super(fm);
        this.tabCount = tabCount;
        this.tabBarTitles = tabBarTitles;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
                return null;
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

