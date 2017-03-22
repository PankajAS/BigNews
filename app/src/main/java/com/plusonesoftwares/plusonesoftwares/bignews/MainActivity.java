package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private TabLayout tabLayout;
    ViewPager viewPager;
    CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        clsCommon = new CommonClass();
        final String[] tabBarTitles = new String[]{
                getString(R.string.Home),
                getString(R.string.Menu),
                getString(R.string.Discover)
        };
        tabLayout.addTab(tabLayout.newTab().setText(tabBarTitles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabBarTitles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tabBarTitles[2]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                android.support.v4.app.Fragment fragment= ((PagerAdapter)viewPager.getAdapter()).getFragment(position);

                if(position == 0 && fragment!=null){
                    setTitle(clsCommon.getUserPrefs(clsCommon.CategroyTitle, getApplicationContext()));
                }
                else if(position == 1 && fragment!=null){
                    setTitle("Followed Catogries");
                }else if(position == 2 && fragment!=null){
                    setTitle("All Catogries");
                }
                //Storing current index of flipper
                clsCommon.setUserPrefs(clsCommon.flipCurrentIndex, "0" ,getApplicationContext());
            }
            @Override
            public void onPageSelected(int position) {
                android.support.v4.app.Fragment fragment = ((PagerAdapter) viewPager.getAdapter()).getFragment(position);

                if (position == 0 && fragment != null) {
                    fragment.onResume();
                    setTitle(clsCommon.getUserPrefs(clsCommon.CategroyTitle, getApplicationContext()));
                } else if (position == 1 && fragment != null) {
                    fragment.onResume();
                    setTitle("Followed Catogries");
                } else if (position == 2 && fragment != null) {
                    setTitle("All Catogries");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
            final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabBarTitles);
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                String flipIndex = clsCommon.getUserPrefs(clsCommon.flipCurrentIndex, getApplicationContext());

                if (flipIndex.equals("0")) {
                    if(viewPager.getCurrentItem()==0) {
                        moveTaskToBack(true);
                        return true;
                    }
                    else
                    {
                        viewPager.setCurrentItem(0, true);
                    }
                }
                else
                {
                    Toast.makeText(this, R.string.flipBackMsg, Toast.LENGTH_LONG).show();
                }
        }
        return false;
    }

//    @Override
//    public void onBackPressed() {
//        String flipIndex = clsCommon.getUserPrefs(clsCommon.flipCurrentIndex, getApplicationContext());
//
//        if (flipIndex.equals("0")) {
//            super.onBackPressed();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//        else
//        {
//            Toast.makeText(this, R.string.flipBackMsg, Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        switch (item.getItemId()) {
            case R.id.menu_Share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                //shareIntent.putExtra(Intent.EXTRA_TEXT, "For best Sahayri & Jokes please download : "+"https://play.google.com/store/apps/details?id=" + appPackageName);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));
                return true;
            case R.id.menu_RateUS:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/")));
                return true;
            case R.id.menu_AboutUS:
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
