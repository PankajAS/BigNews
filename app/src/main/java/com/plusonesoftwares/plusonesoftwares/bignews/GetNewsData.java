package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;
import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.NewsDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 11-03-2017.
 */

public class GetNewsData extends AsyncTask<URL,Context,JSONArray> {
        StringBuilder sb = null;
        private HttpURLConnection urlConnection;
        Context context;
        ListView list;
        Activity parentContext;
        String title;
        JSONArray jarray;
        String isNext;
        String Category;
        boolean isLastRequest;

        public GetNewsData(Context context, String isNext ,String Category, boolean isLastRequest, Activity parentContext){
            this.context = context;
            this.isNext = isNext;
            this.Category = Category;
            this.isLastRequest = isLastRequest;
            this.parentContext = parentContext;
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            String line;
            String c;

            System.out.println(url.toString().substring(url.toString().lastIndexOf("=") + 1));
            System.out.println(url);
            sb = new StringBuilder();
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject jsnobject = new JSONObject(sb.toString());
                jarray = jsnobject.getJSONArray("items");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(jarray);
            return jarray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
           // list.setAdapter(new CustomViewAdapter(context,parentContext, jsonArray));
            super.onPostExecute(jsonArray);
            ContentRepo contentOperation = new ContentRepo(parentContext);

            List<NewsDataModel> newsList = new ArrayList<>();

            NewsDataModel singleCatObj;
            if(jsonArray !=null && jsonArray.length()>0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        singleCatObj = new NewsDataModel();
                        singleCatObj.Title = jsonArray.getJSONObject(i).getString("title");
                        singleCatObj.ImageUrl = jsonArray.getJSONObject(i).getString("imgURL");
                        singleCatObj.Description = jsonArray.getJSONObject(i).getString("desc");
                        singleCatObj.Category = Category;
                        singleCatObj.IsNext = isNext;
                        newsList.add(singleCatObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                contentOperation.insert_NewsData(newsList);

                if(isLastRequest)
                {
                    Intent intent = new Intent(parentContext, MainActivity.class);
                    parentContext.startActivity(intent);
                }
            }
        }
    }

