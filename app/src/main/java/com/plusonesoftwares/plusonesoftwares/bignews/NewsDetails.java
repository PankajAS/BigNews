package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusonesoftwares.plusonesoftwares.bignews.chromeCustomTab.CustomTabActivityHelper;
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
    String SourceUrl;
    String newsTitle;
    CommonClass clsCommon;

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;

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
        SourceUrl = intent.getStringExtra("SourceLink");
        setTitle(newsTitle);
        clsCommon = new CommonClass();

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

    public void openNewsSource(View view) {
       // mCustomTabsIntent.launchUrl(this, Uri.parse(SourceUrl));
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        CustomTabsIntent customTabsIntent = builder.build();
       // customTabsIntent.launchUrl(this, Uri.parse(SourceUrl));
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent, Uri.parse(SourceUrl),
                new CustomTabActivityHelper.CustomTabFallback() {
                    @Override
                    public void openUri(Activity activity, Uri uri) {

                        Intent intent = new Intent(NewsDetails.this,WebViewActivity.class);
                        intent.putExtra("url",String.valueOf(Uri.parse(SourceUrl)));
                        startActivity(intent);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            clsCommon.setUserPrefs(clsCommon.isBackKeyPressed, "true", getApplicationContext());
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clsCommon.setUserPrefs(clsCommon.isBackKeyPressed, "true", getApplicationContext());
    }
}
