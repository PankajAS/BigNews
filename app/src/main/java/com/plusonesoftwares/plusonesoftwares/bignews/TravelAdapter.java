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

import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.UI;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    Utils utils;

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

    @Override
    public int getCount() {
        if(newsCategory !=null){
        return newsCategory.size();
        }
        else if(jarray !=null){
            return jarray.length();
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


    private Boolean getIsNext(List<String> newsCategory, int parentIndex){

        int halfsize =  newsCategory.size()/2;

        if(parentIndex>halfsize-1){
            return  true;
        }
        else{
            return  false;
        }
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {
        View layout = convertView;

        if(parentContext!=null) {

            if (convertView == null) {
                layout = inflater.inflate(R.layout.activity_home_fragment, null);

            }
            newsRecordsClsObj = new ContentRepo(context);
            utils = new Utils();
            Boolean isNext = getIsNext(newsCategory,position);
            ArrayList<HashMap<String, String>> newsList = newsRecordsClsObj.getNewsData(newsCategory.get(position), isNext? "true":"false");

            JSONArray mJSONArray = new JSONArray(Arrays.asList(newsList));
            JSONArray jsonArray1 = new JSONArray();
            try {
                jsonArray1 = mJSONArray.getJSONArray(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, parentContext, jsonArray1));
            parentContext.setTitle(utils.getCatNameByCatId(newsCategory.get(position)));

            utils.setUserPrefs(utils.CategroyTitle,utils.getCatNameByCatId(newsCategory.get(position)),parentContext);

        }else{

            if(convertView == null){
                layout = inflater.inflate(R.layout.activity_news_category_details,null);
            }
            try {
                JSONObject jobject = jarray.getJSONObject(position);
                UI.<TextView>findViewById(layout, R.id.headline).setText(jobject.getString("Title"));
                Picasso.with(parentContext)
                        .load("http:" + jobject.getString("ImageUrl"))
                        .into(UI.<ImageView>findViewById(layout, R.id.newsImage));
                JSONObject descObject = new JSONObject(jobject.getString("Description"));
                UI.<TextView>findViewById(layout, R.id.description).setText(descObject.getString("value"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return layout;
    }
}
