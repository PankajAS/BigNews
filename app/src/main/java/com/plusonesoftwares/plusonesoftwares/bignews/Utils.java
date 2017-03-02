package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

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
}
