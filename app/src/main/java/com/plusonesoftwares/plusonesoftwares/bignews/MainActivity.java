package com.plusonesoftwares.plusonesoftwares.bignews;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.DiscoverFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.HomeFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.MenuFragment;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    //private ViewPager viewPager;
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
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
        //PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),tabBarTitles);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    setTitle("Followed Catogries");
                }else if(position == 2){
                    setTitle("All Catogries");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

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
            public CharSequence getPageTitle(int position) {
                return tabBarTitles[position];
            }

            @Override
            public int getCount() {
                return tabBarTitles.length;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        //changePager();
    }

    public void changePager(){
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void transformPage(View view, float position) {

                if (position <= -1.0F || position >= 1.0F) {
                    view.setAlpha(0.0F);
                } else if (position == 0.0F) {
                    view.setAlpha(1.0F);
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    view.setAlpha(1.0F - Math.abs(position));
                }

                int pageWidth = view.getWidth();
                float pageWidthTimesPosition = pageWidth * position;
                float absPosition = Math.abs(position);

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    view.setAlpha(1);
                    // int pagePosition = (int) page.getTag();
                    // Counteract the default slide transition
                    view.setTranslationX(view.getWidth() * -position);
                    //set Y position to swipe in from top
                    float yPosition = position * view.getHeight();
                    view.setTranslationY(yPosition);
                    view.setTranslationZ(yPosition);


                    View title = view.findViewById(R.id.title);
                    if (title != null) {
                        //title.setAlpha(1.0f - absPosition);
                        //title.setTranslationY(-100f);
                        //title.setPivotX(view.getWidth() * 0.5f);
                        title.setTranslationY(-pageWidthTimesPosition / 1f);
                        title.setAlpha(1.0f - absPosition);
                    }

                   /* final float rotation = -180f * position;
                    view.setAlpha(rotation > 90f || rotation < -90f ? 0f : 1f);
                    view.setPivotX(view.getWidth() * 0.5f);
                    view.setPivotY(view.getHeight() * 0.5f);
                    view.setRotationX(rotation);*/
                }
            }

        });
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
}
