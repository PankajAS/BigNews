package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

public class NewsCategoryDetails extends AppCompatActivity {
    private FlipViewController flipView;
    String Tcategory;
    String category;
    List<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flipView = new FlipViewController(getApplicationContext(), FlipViewController.VERTICAL);
        //flipView.setAdapter(new TravelAdapter(getApplicationContext(), urls));
        setContentView(flipView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        urls = new ArrayList<>();
        setTitle(intent.getStringExtra("categoryName"));
        category = intent.getStringExtra("categoryName").replaceAll("\\s+","");

        if(!category.substring(0, 1).equals("M")){
        category =  category.substring(0, 1).toLowerCase() + category.substring(1);}
        urls.add("https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getFirstNewsList?newsCategory="+category);
        urls.add("https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getNextNewsList?newsCategory="+category);



        try {
            new FetchData(getApplicationContext(), flipView).execute(new URL(urls.get(0)), new URL(urls.get(1)));
        } catch (MalformedURLException e) {
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

    public class FetchData extends AsyncTask<URL,Context,JSONArray>{
        StringBuilder sb = null;
        StringBuilder sb1 = null;
        private HttpURLConnection urlConnection, urlConnection1;
        Context context;
        JSONArray firstArray;
        JSONArray nextArray;
        JSONArray result;
        FlipViewController flipView;

        public FetchData(Context context, FlipViewController flipView){
            this.context = context;
            this.flipView = flipView;
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            URL url = urls[0];
            URL url2 = urls[1];
            String line;
            String c;
            sb = new StringBuilder();
            sb1 = new StringBuilder();
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject jsnobject = new JSONObject(sb.toString());
                firstArray = jsnobject.getJSONArray("items");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                urlConnection1 = (HttpURLConnection) url2.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection1.getInputStream()));

                while ((line = in.readLine()) != null) {
                    sb1.append(line);
                }
                JSONObject jsnobject1 = new JSONObject(sb1.toString());
                nextArray = jsnobject1.getJSONArray("items");

               result = concatArray(firstArray,nextArray);


               // JSONObject margeArray = new JSONObject();
                //margeArray.putOpt("item1",firstArray);
               /// margeArray.putOpt("item1",nextArray);
              //  System.out.println(margeArray.toString(2));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return result;
        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {

           flipView.setAdapter(new TravelAdapter(NewsCategoryDetails.this, jsonArray));
            super.onPostExecute(jsonArray);
        }

        private JSONArray concatArray(JSONArray arr1, JSONArray arr2)
                throws JSONException {
            JSONArray result = new JSONArray();
            for (int i = 0; i < arr1.length(); i++) {
                result.put(arr1.get(i));
            }
            for (int i = 0; i < arr2.length(); i++) {
                result.put(arr2.get(i));
            }
            return result;
        }
    }
}
