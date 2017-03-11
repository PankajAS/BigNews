package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.DataCollection;
import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;
import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;
import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.NewsDataModel;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.UI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    HttpConnection httpConnection = new HttpConnection();

    private int repeatCount = 1;
    private List<Travels.Data> travelData;
    private List<DataCollection.Data> dataCollectionData;
    JSONArray jarray;
    List<String> urls;
    List<String> newsCategory;
    List<NewsDataModel.Data> newsDataList;
    JSONArray allNewsData;
    ContentRepo newsRecords;

    public TravelAdapter(Context context1, Activity parentcontext, ArrayList<String> urllist, List<String> newsCategory, JSONArray allNewsData) {
        this.context = context1;
        inflater = LayoutInflater.from(context1);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
        //dataCollectionData = new ArrayList<DataCollection.Data>(DataCollection.IMG_DESCRIPTIONS);
        this.parentContext = parentcontext;
        this.allNewsData = allNewsData;
        this.urls = urllist;
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
        //return travelData.size() * repeatCount;
        if(urls !=null){
        return urls.size();
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
        if (convertView == null) {
            layout = inflater.inflate(R.layout.activity_home_fragment, null);

        }
        newsRecords = new ContentRepo(context);
        ArrayList<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();
        Boolean isNext = getIsNext(newsCategory,position);
        newsList =newsRecords.getNewsData(newsCategory.get(position),isNext? "true":"false");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(newsList));
        JSONArray jsonArray1 = new JSONArray();
        try {
            System.out.println(mJSONArray.toString(2));
            jsonArray1 = mJSONArray.getJSONArray(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println();



        UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, parentContext, jsonArray1));

        //System.out.println(position);
        /*if(parentContext!=null) {
            if (convertView == null) {
                layout = inflater.inflate(R.layout.activity_home_fragment,null);
                AphidLog.d("created new view from adapter: %d", position);
            }
            //final Travels.Data data = travelData.get(position % travelData.size());
           //DataCollection.Data data = dataCollectionData.get(position);


            try {
                new DownloadData(context, parentContext, newsCategory.get(position), UI.<ListView>findViewById(layout, R.id.list)).execute(new URL(urls.get(position)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }else{

            if(convertView==null){
                layout = inflater.inflate(R.layout.activity_news_category_details,null);
            }
            try {
                JSONObject jobject = jarray.getJSONObject(position);
                UI.<TextView>findViewById(layout, R.id.headline).setText(jobject.getString("title"));
               // new ImageDownloader(UI.<ImageView>findViewById(layout, R.id.newsImage)).execute(jobject.getString("imgURL"));
                UI.<TextView>findViewById(layout, R.id.description).setText(jobject.getString("desc"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/

        return layout;
    }

    public class DownloadData extends AsyncTask<URL,Context,JSONArray>{
        StringBuilder sb = null;
        private HttpURLConnection urlConnection;
        Context context;
        ListView list;
        Activity parentContext;
        String title;


        public DownloadData(Context context, Activity parentContext, String title, ListView list){
            this.context = context;
            this.list = list;
            this.parentContext = parentContext;
            this.title = title;
        }



        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            String line;
            String c;

           // System.out.println(url.toString().substring(url.toString().lastIndexOf("=") + 1));
           // System.out.println(url);
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
           // System.out.println(jarray);
            return jarray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {




            list.setAdapter(new CustomViewAdapter(context,parentContext, jsonArray));

            parentContext.setTitle(title);

            super.onPostExecute(jsonArray);
        }
    }
}
