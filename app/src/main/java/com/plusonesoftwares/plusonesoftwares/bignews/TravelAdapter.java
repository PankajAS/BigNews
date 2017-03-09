package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.AphidLog;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.UI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class TravelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    Activity parentContext;
    HttpConnection httpConnection = new HttpConnection();

    private int repeatCount = 1;
    private List<Travels.Data> travelData;
    JSONArray jarray;
    List<String> urls;
    List<String> newsCategory;

    public TravelAdapter(Context context1, Activity parentcontext, ArrayList<String> urllist, List<String> newsCategory) {
        this.context = context1;
        inflater = LayoutInflater.from(context1);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
        this.parentContext = parentcontext;
        this.urls = urllist;
        this.newsCategory = newsCategory;
    }

    public TravelAdapter(Context context, JSONArray jarray) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
        this.jarray = jarray;
    }

    public TravelAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);

    }

    @Override
    public int getCount() {
        //return travelData.size() * repeatCount;
        return urls.size();
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



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View layout = convertView;
        if (convertView == null) {
            layout = inflater.inflate(R.layout.activity_home_fragment, null);
            AphidLog.d("created new view from adapter: %d", position);
        }

        final Travels.Data data = travelData.get(position % travelData.size());
        try {
            new DownloadData(context,UI.<ListView>findViewById(layout, R.id.list)).execute(new URL(urls.get(position)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        parentContext.setTitle(newsCategory.get(position));

        return layout;
    }

    public List<Travels.Data> getTravelData() {
        return travelData;
    }

    public class DownloadData extends AsyncTask<URL,Context,JSONArray>{
        StringBuilder sb = null;
        private HttpURLConnection urlConnection;
        Context context;
        ListView list;

        public DownloadData(Context context, ListView list){
            this.context = context;
            this.list = list;
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            String line;
            String c;
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
            return jarray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            list.setAdapter(new CustomViewAdapter(context,parentContext, jsonArray));
            super.onPostExecute(jsonArray);
        }
    }
}
