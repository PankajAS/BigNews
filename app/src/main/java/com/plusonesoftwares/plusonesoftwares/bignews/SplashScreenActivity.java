package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    List<String> string;
    ListView data;
    String Url = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getFirstNewsList?newsCategory=";
    String nextUrl = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getNextNewsList?newsCategory=";
    ArrayList<String> newsCategory= new ArrayList<>();



    Utils utils;
    //JSONArray jsonArray;
    JSONArray array;
    JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        newsCategory.add(Url+"indiaHeadLinesNews");
        newsCategory.add(Url+"indiaMovieNews");
        newsCategory.add(Url+"indiaBusinessNews");
        newsCategory.add(nextUrl+"indiaHeadLinesNews");
        newsCategory.add(nextUrl+"indiaMovieNews");
        newsCategory.add(nextUrl+"indiaBusinessNews");


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        utils = new Utils();



                /*int parentIndex = 0;
                for(String url:newsCategory) {
                    try {
                        new GetNewsData(getApplicationContext(),getIsNext(newsCategory,parentIndex),getCategoryName(url)).execute(new URL(url));//start async task to get all categories
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    parentIndex++;
                }*/
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);




    }

    private String getIsNext(ArrayList<String> newsCategory, int parentIndex){
       int halfsize =  newsCategory.size()/2;

        if(parentIndex>halfsize-1){
            return  "true";
        }
        else{
            return  "false";
        }
    }

    private String getCategoryName(String url){

        return url.substring(url.lastIndexOf("=") + 1);
    }

}

