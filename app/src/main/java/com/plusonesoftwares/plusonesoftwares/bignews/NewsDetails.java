package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsDetails extends AppCompatActivity {
    JSONObject jobject, descObject;
    JSONArray jarray;
    TextView headline, description;
    ImageView newsImage;
    String jsonarray;
    String newsTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        headline = (TextView)findViewById(R.id.headline);
        description = (TextView)findViewById(R.id.description);
        newsImage = (ImageView)findViewById(R.id.newsImage);
        Intent intent = getIntent();
        jsonarray = intent.getStringExtra("Data");
        newsTitle = intent.getStringExtra("NewsCategory");
        setTitle(newsTitle);

        try {
            jobject = new JSONObject(jsonarray);
            headline.setText(jobject.getString("Title"));
            descObject = new JSONObject(jobject.getString("Description"));
            Picasso.with(this)
                    .load("http:" + jobject.getString("ImageUrl"))
                    .into(newsImage);
            description.setText(descObject.getString("value"));
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
