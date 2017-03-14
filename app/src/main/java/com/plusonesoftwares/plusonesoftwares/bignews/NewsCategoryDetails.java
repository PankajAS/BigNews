package com.plusonesoftwares.plusonesoftwares.bignews;

import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NewsCategoryDetails extends AppCompatActivity {
    private FlipViewController flipView;
    String category;
    List<String> urls;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flipView = new FlipViewController(getApplicationContext(), FlipViewController.VERTICAL);
        setContentView(flipView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = new Utils();
        Intent intent = getIntent();
        urls = new ArrayList<>();
        setTitle(intent.getStringExtra("categoryName"));

        category = utils.getCatIdByCatName(intent.getStringExtra("categoryName"));
        ContentRepo newsRecords = new ContentRepo(getApplicationContext());
        ArrayList<HashMap<String, String>> newsList = newsRecords.getAllNewsDataByCategory(category);

        JSONArray mJSONArray = new JSONArray(Arrays.asList(newsList));
        try {
            JSONArray jsonArray1 = mJSONArray.getJSONArray(0);
            flipView.setAdapter(new TravelAdapter(NewsCategoryDetails.this, jsonArray1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
