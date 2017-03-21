package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.UI;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class TravelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    Activity parentContext;

    private int repeatCount = 1;
    JSONArray jarray;
    List<String> newsCategory;
    ContentRepo newsRecordsClsObj;
    CommonClass clsCommon;
    List<String> selectedCategory;
    String title;

    public TravelAdapter(Context context1, Activity parentcontext, List<String> newsCategory) {
        this.context = context1;
        inflater = LayoutInflater.from(context1);
        this.parentContext = parentcontext;
        this.newsCategory = newsCategory;
    }

    public TravelAdapter(Context context) {
        this.context = context;

    }

    public TravelAdapter(Activity context, JSONArray jarray) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.jarray = jarray;
    }

    public TravelAdapter(Context context, List<String> selectedCategory, String title) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.selectedCategory = selectedCategory;
        this.title = title;
    }

    @Override
    public int getCount() {
        if (newsCategory != null) {
            return newsCategory.size();
        } else if (selectedCategory != null) {
            return selectedCategory.size();
        }
        return 0;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private Boolean getIsNext(List<String> newsCategory, int parentIndex) {

        int halfsize = newsCategory.size() / 2;

        if (parentIndex > halfsize - 1) {
            return true;
        } else {
            return false;
        }
    }

    private InterstitialAd interstitial;
    //NativeCustomTemplateAd nativeAd;

    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {
        View layout = convertView;
        newsRecordsClsObj = new ContentRepo(context);

        if (parentContext != null) {
            clsCommon = new CommonClass();

            if (newsCategory.get(position).equals("AdMob")) {
                layout = inflater.inflate(R.layout.activity_admob, null);
                final ViewGroup adView = (ViewGroup) layout.findViewById(R.id.adView);
                final TextView headline = (TextView) adView.findViewById(R.id.headline);
                final TextView caption = (TextView) adView.findViewById(R.id.caption);
                final ImageView mainImage = (ImageView) adView.findViewById(R.id.mainImage);

                AdLoader adLoader = new AdLoader.Builder(context,
                        context.getString(R.string.native_ad_unit_id))
                        .forCustomTemplateAd(context.getString(R.string.native_template_id),
                                new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
                                    @Override
                                    public void onCustomTemplateAdLoaded(final NativeCustomTemplateAd nativeAd) {
                                        headline.setText(nativeAd.getText("Headline"));
                                        caption.setText(nativeAd.getText("Caption"));
                                        mainImage.setImageDrawable(nativeAd.getImage("MainImage").getDrawable());
                                        // Record an impression
                                        nativeAd.recordImpression();
                                        // Handle clicks on image
                                        mainImage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                nativeAd.performClick("MainImage");
                                                //setting it as true to stop call of onresume of home  tab
                                                clsCommon.setUserPrefs(clsCommon.isBackKeyPressed, "true", parentContext.getApplicationContext());
                                            }
                                        });
                                    }
                                },
                                null)
                        .build();
                adLoader.loadAd(new PublisherAdRequest.Builder().build());
            } else {

                layout = inflater.inflate(R.layout.activity_home_fragment, null);
                JSONArray jsonArray1 = getNewsDataByCategory(position, newsCategory);
                UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, parentContext, jsonArray1));

            }
        } else {

            if (convertView == null) {
                layout = inflater.inflate(R.layout.activity_home_fragment, null);
            }

            JSONArray jsonArray1 = getNewsDataByCategory(position, selectedCategory);
            UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, jsonArray1, title));

        }
        return layout;
    }

    private JSONArray getNewsDataByCategory(int position, List<String> CategoryNameList) {

        Boolean isNext = getIsNext(CategoryNameList, position);
        ArrayList<HashMap<String, String>> newsList = newsRecordsClsObj.getNewsData(CategoryNameList.get(position), isNext ? "true" : "false");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(newsList));
        JSONArray jsonArray1 = new JSONArray();
        try {
            jsonArray1 = mJSONArray.getJSONArray(0);
            //System.out.println(jsonArray1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray1;
    }
}
