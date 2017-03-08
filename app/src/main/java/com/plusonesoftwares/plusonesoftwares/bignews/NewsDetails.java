package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsDetails extends AppCompatActivity {
    JSONObject jobject, descObject;
    JSONArray jarray;
    TextView headline, description;
    ImageView newsImage;
    String jsonarray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        headline = (TextView)findViewById(R.id.headline);
        description = (TextView)findViewById(R.id.description);
        newsImage = (ImageView)findViewById(R.id.newsImage);
        Intent intent = getIntent();
        jsonarray=intent.getStringExtra("Data");

        try {
            jobject = new JSONObject(jsonarray);
            headline.setText(jobject.getString("title"));
            descObject = new JSONObject(jobject.getString("desc"));
            new ImageDownloader(newsImage).execute(jobject.getString("imgURL"));
            description.setText(descObject.getString("value"));
            System.out.println(jobject.toString(2));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
