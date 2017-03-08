package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpConnection {
    private HttpURLConnection urlConnection;
    private Context mContext;
    StringBuilder sb = null;
    JSONObject jsonData;

    public class FetchData extends AsyncTask<URL,Context,JSONArray>{
        private Context context;
        List<String> list = new ArrayList<>();
        HashMap<String, String> contentId = new HashMap<>();
        ProgressDialog dialog;
        JSONArray array;

        public FetchData(Context context1){
            context = context1;
        }

        @Override
        protected void onPreExecute() {
          //dialog = ProgressDialog.show(context, "","Loading...", true);
            super.onPreExecute();
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
                array = jsnobject.getJSONArray("items");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray strings) {
            //dialog.dismiss();
            super.onPostExecute(strings);
        }
    }
}
