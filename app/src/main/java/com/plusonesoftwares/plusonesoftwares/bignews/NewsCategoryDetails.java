package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoryDetails extends AppCompatActivity {
    private FlipViewController flipView;
    String category;
    List<String> urls;
    CommonClass utils;
    List<String> selectedCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flipView = new FlipViewController(NewsCategoryDetails.this,getApplicationContext(), FlipViewController.VERTICAL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedCategory = new ArrayList<>();
        utils = new CommonClass();
        Intent intent = getIntent();
        urls = new ArrayList<>();
        category = intent.getStringExtra("categoryName");
        setTitle(category);
        selectedCategory.add(utils.getCatIdByCatName(category));//first News item
        selectedCategory.add(utils.getCatIdByCatName(category));//next News item

        flipView.setAdapter(new TravelAdapter(NewsCategoryDetails.this, selectedCategory, category));
        setContentView(flipView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
