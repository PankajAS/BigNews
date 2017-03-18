package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
    CommonClass utils;
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
        if(newsCategory !=null){
        return newsCategory.size();
        }
        else if(selectedCategory !=null){
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
        newsRecordsClsObj = new ContentRepo(context);

        int titlePosition = 0;
        if(parentContext!=null) {

            utils = new CommonClass();
            titlePosition = (position > 0 ? position-1 : position);
            if(newsCategory.get(position).equals("AdMob"))
            {
                //if (convertView == null) {
                    layout = inflater.inflate(R.layout.activity_admob, null);
                    //UI.<ListView>findViewById(layout, R.id.list)); will be used to set add data here
                //}
            }
            else {

               // if (convertView == null) {
                    layout = inflater.inflate(R.layout.activity_home_fragment, null);
               // }
                JSONArray jsonArray1 = getNewsDataByCategory(position, newsCategory);

                UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, parentContext, jsonArray1));

            }
            parentContext.setTitle(utils.getCatNameByCatId(newsCategory.get(titlePosition)));
            utils.setUserPrefs(utils.CategroyTitle,utils.getCatNameByCatId(newsCategory.get(titlePosition)),parentContext);
        }
        else
        {

            if(convertView == null){
                layout = inflater.inflate(R.layout.activity_home_fragment,null);
            }

            JSONArray jsonArray1 = getNewsDataByCategory(position, selectedCategory);
            UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, jsonArray1,title));

        }
        return layout;
    }

    private JSONArray getNewsDataByCategory(int position, List<String> CategoryNameList) {

        Boolean isNext = getIsNext(CategoryNameList,position);
        ArrayList<HashMap<String, String>> newsList = newsRecordsClsObj.getNewsData(CategoryNameList.get(position), isNext? "true":"false");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(newsList));
        JSONArray jsonArray1 = new JSONArray();
        try {
            jsonArray1 = mJSONArray.getJSONArray(0);

           System.out.println(jsonArray1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray1;
    }
}
