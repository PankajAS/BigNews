package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    List<String> string;
    ArrayList<String> newsCategory = new ArrayList<>();

    Utils utils;
    ContentRepo contentOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        utils = new Utils();
        defaultNewsCategories();

        contentOperation = new ContentRepo(getApplicationContext());

        newsCategory = utils.getFollowedCategoriesLink(getApplicationContext(), true);

        if(!contentOperation.dataAlreadyExist()) {
            boolean isLastRequest = false;
            int parentIndex = 0;

            for (String url : newsCategory) {
                isLastRequest = (parentIndex == newsCategory.size() - 1);
                try {
                    new GetNewsData(getApplicationContext(), getIsNext(newsCategory, parentIndex), utils.getCategoryName(url), isLastRequest, SplashScreenActivity.this, true).execute(new URL(url));//start async task to get all categories
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                parentIndex++;
            }
        }
        else
        {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private String getIsNext(ArrayList<String> newsCategory, int parentIndex){
       int halfsize =  newsCategory.size()/2;

        if(parentIndex > halfsize-1){
            return  "true";
        }
        else{
            return  "false";
        }
    }

    private void defaultNewsCategories()
    {
        JSONObject FollowedCategories = new JSONObject();
        try {
            FollowedCategories.put("indiaHeadLinesNews", "India Head Lines News");
            FollowedCategories.put("indiaMovieNews", "India Movie News");
            FollowedCategories.put("indiaBusinessNews", "India Business News");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!utils.keyExist(getApplicationContext()))
            utils.setUserPrefs(utils.NewsCategories, FollowedCategories.toString() ,getApplicationContext());
    }
}

