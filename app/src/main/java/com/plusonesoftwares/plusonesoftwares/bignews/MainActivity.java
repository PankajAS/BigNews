package com.plusonesoftwares.plusonesoftwares.bignews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utils = new Utils();

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.pager);
        String[] tabBarTitles = new String[]{
                getString(R.string.Home),
                getString(R.string.Menu),
                getString(R.string.Discover)
        };
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Home)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Menu)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Discover)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);//to enable the scrolling of pager
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),tabBarTitles);
        viewPager.setAdapter(pagerAdapter);

        defaultNewsCategories();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void defaultNewsCategories()
    {
        JSONObject FollowedCategories = new JSONObject();
        try {
            FollowedCategories.put("tamilHeadNews", "Tamil Head News");
            FollowedCategories.put("tamilCinemaNews", "Tamil Cinema News");
            FollowedCategories.put("tamilVikatanBusinessNews", "Tamil Vikatan Business News");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(!utils.keyExist(getApplicationContext()))
            utils.setUserPrefs(utils.NewsCategories, FollowedCategories.toString() ,getApplicationContext());
    }
}
