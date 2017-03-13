package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashoksharma on 02/03/17.
 */

 /*1. tamilHeadNews
   2. tamilCinemaNews
   3. tamilVikatanBusinessNews
   4. MalayalamHeadLinesNews
   5. MalayalamBusinessNews
   6. teluguBusinessNews
   7. MalayalamMovieNews
   8. MalayalamSportsNews
   9. MalayalamWorldNews
   10. MalayalamNationalNews
 */

public class Utils {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String NewsCategories = "NewsCategories";

    public static final  String[] mTextofButton = { "Tamil Head News", "Tamil Cinema News", "Tamil Vikatan Business News", "Malayalam Head Lines News", "Malayalam Business News", "Telugu Business News",
            "Malayalam Movie News", "Malayalam Sports News", "Malayalam World News" ,"Malayalam National News"};

    public static final String[] catKeys = { "tamilHeadNews", "tamilCinemaNews", "tamilVikatanBusinessNews", "MalayalamHeadLinesNews", "MalayalamBusinessNews", "teluguBusinessNews",
            "MalayalamMovieNews", "MalayalamSportsNews", "MalayalamWorldNews" ,"MalayalamNationalNews"};

    public static final   String[] colorCodes = { "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8",
            "#aab7b8", "#aab7b8", "#aab7b8" ,"#aab7b8"};

    public static final String SelectedColor = "#04A94A";
    public static final String UnSelectedColor = "#aab7b8";

    SharedPreferences sharedpreferences;
    
    public void setUserPrefs(String key, String value, Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getUserPrefs(String key, Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String value = sharedpreferences.getString(key, null);
        editor.commit();
        return value;
    }
    public boolean keyExist(Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        return sharedpreferences.contains(NewsCategories);
    }
    public void clearUserPrefs(Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void showNetworkConnectionMsg(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public JSONObject getUpdatedCategories(Context context)
    {
        JSONObject JsonCategories = null;
        try {
            JsonCategories = new JSONObject(getUserPrefs(NewsCategories,context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonCategories;
    }

    public String getCategoryName(String url){
        return url.substring(url.lastIndexOf("=") + 1);
    }

}
