package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ashoksharma on 02/03/17.
 */

 /*1. indiaHeadLinesNews
   2. indiaMovieNews
   3. indiaBusinessNews
   4. MalayalamHeadLinesNews
   5. MalayalamBusinessNews
   6. teluguBusinessNews
   7. MalayalamMovieNews
   8. MalayalamSportsNews
   9. MalayalamWorldNews
   10. MalayalamNationalNews
 */

public class CommonClass {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String NewsCategories = "NewsCategories";
    public static final String CategroyTitle = "CategroyTitle";
    public static final String isBackKeyPressed = "isBackKeyPressed";
    public static final String flipCurrentIndex = "flipCurrentIndex";
    public static final String isPendingRequest = "isPendingRequest";


    public static final  String[] mTextofButton = { "India Head Lines News", "India Movie News", "India Business News", "Malayalam Head Lines News", "Malayalam Business News", "Telugu Business News",
            "Malayalam Movie News", "Malayalam Sports News", "Malayalam World News" ,"Malayalam National News"};

    public static final String[] catKeys = { "indiaHeadLinesNews", "indiaMovieNews", "indiaBusinessNews", "MalayalamHeadLinesNews", "MalayalamBusinessNews", "teluguBusinessNews",
            "MalayalamMovieNews", "MalayalamSportsNews", "MalayalamWorldNews" ,"MalayalamNationalNews"};

    public static final   String[] colorCodes = { "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8",
            "#aab7b8", "#aab7b8", "#aab7b8" ,"#aab7b8"};

    public static final String SelectedColor = "#04A94A";
    public static final String UnSelectedColor = "#aab7b8";
    public  String Url = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getFirstNewsList?newsCategory=";
    public  String nextUrl = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getNextNewsList?newsCategory=";
    public static final String offlineUrl = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getOfflineFirstNewsList?newsCategory=";
    public static final String offlineNextUrl = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getOfflineNextNewsList?newsCategory=";

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
               // Toast.makeText(activity, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });
    }

   /* public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/

    public boolean haveNetworkConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

//get image from cat id

    /*public int getImagebyId(String CategoryId){
        int CatImg = 0;
        switch (CategoryId) {
            case "indiaHeadLinesNews":
                CatImg =  R.drawable.indiaheadlinesnews;
                break;
            case "indiaMovieNews":
                CatImg = R.drawable.indiamovienews;
                break;
            case "indiaBusinessNews":
                CatImg = R.drawable.indiabusinessnews;
                break;
            case "MalayalamHeadLinesNews":
                CatImg = R.drawable.malayalamheadlinesnews;
                break;
            case "MalayalamBusinessNews":
                CatImg = R.drawable.malayalambusinessnews;
                break;
            case "teluguBusinessNews":
                CatImg = R.drawable.telugubusinessnews;
                break;
            case "MalayalamMovieNews":
                CatImg = R.drawable.malayalammovienews;
                break;
            case "MalayalamSportsNews":
                CatImg = R.drawable.malayalamsportsnews;
                break;
            case "MalayalamWorldNews":
                CatImg = R.drawable.malayalamworldnews;
                break;
            case "MalayalamNationalNews":
                CatImg = R.drawable.malayalamnationalnews;
                break;
        }
        return CatImg;

    }*/

    public String getCategoryName(String url){
        return url.substring(url.lastIndexOf("=") + 1);
    }

    public String getCatNameByCatId(String CategoryId)
    {
        String CatName = "";
        switch (CategoryId) {
            case "indiaHeadLinesNews":
                CatName =  "India Head Lines News";
                break;
            case "indiaMovieNews":
                CatName = "India Movie News";
                break;
            case "indiaBusinessNews":
                CatName = "India Business News";
                break;
            case "MalayalamHeadLinesNews":
                CatName = "Malayalam Head Lines News";
                break;
            case "MalayalamBusinessNews":
                CatName = "Malayalam Business News";
                break;
            case "teluguBusinessNews":
                CatName = "Telugu Business News";
                break;
            case "MalayalamMovieNews":
                CatName = "Malayalam Movie News";
                break;
            case "MalayalamSportsNews":
                CatName = "Malayalam Sports News";
                break;
            case "MalayalamWorldNews":
                CatName = "Malayalam World News";
                break;
            case "MalayalamNationalNews":
                CatName = "Malayalam National News";
                break;
        }
        return CatName;
    }

    public String getCatIdByCatName(String CatName)
    {
        String category = CatName.replaceAll("\\s+","");

        if(!category.substring(0, 1).equals("M")){
            category =  category.substring(0, 1).toLowerCase() + category.substring(1);
        }
        return category;
    }

    public ArrayList<String> getFollowedCategoriesLink(Context context, boolean isUrl, boolean isAll, Boolean isFromScheduler) {
        JSONObject JsonCategories = getUpdatedCategories(context);

        ArrayList<String> categoriesLink = new ArrayList<>();
        Iterator<String> iter = JsonCategories.keys();
        String key;

        if(isFromScheduler){
            Url = offlineUrl;
            nextUrl = offlineNextUrl;
        }

       if (!isAll) {
            //Adding url for first news items
            while (iter.hasNext()) {
                key = iter.next();
                categoriesLink.add(isUrl ? (Url + key) : key);
            }
            //Adding url for next news items
            Iterator<String> iter1 = JsonCategories.keys();
            while (iter1.hasNext()) {
                key = iter1.next();
                categoriesLink.add(isUrl ? (nextUrl + key) : key);
            }
        } else {
            for (String catName : catKeys) {
                categoriesLink.add(isUrl ? (Url + catName) : catName);
            }
            for (String catName : catKeys) {
                categoriesLink.add(isUrl ? (nextUrl + catName) : catName);
            }
        }

        return categoriesLink;
    }

    public void defaultNewsCategories(Context context) {

        JSONObject FollowedCategories = new JSONObject();
        try {
            FollowedCategories.put("indiaHeadLinesNews", "India Head Lines News");
            FollowedCategories.put("indiaMovieNews", "India Movie News");
            FollowedCategories.put("indiaBusinessNews", "India Business News");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setUserPrefs(NewsCategories, FollowedCategories.toString(), context);
    }

    public String getIsNext(ArrayList<String> newsCategory, int parentIndex) {
        int halfsize = newsCategory.size() / 2;
        if (parentIndex > halfsize - 1) {
            return "true";
        } else {
            return "false";
        }
    }

    public void insertUpdateNews(ArrayList<String> newsCategory, Context context, boolean lastRequest, boolean isRefresh) {
        boolean isLastRequest = false;

        int parentIndex = 0;
        for (String url : newsCategory) {
            isLastRequest = lastRequest ? (parentIndex == newsCategory.size()-1): false;
            try {
                new GetNewsData(context, getIsNext(newsCategory, parentIndex), getCategoryName(url), isLastRequest, isRefresh, null).execute(new URL(url));//start async task to get all categories
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            parentIndex++;
        }
        newsCategory.clear();
    }


    public List<String> getCatWithAdmob(List<String> newsCategory) {
       List<String> newsCategory1 = new ArrayList<>();
        int index = 0;

        for(String cat : newsCategory)
        {
            if(index!=0 && index%3==0) {
                newsCategory1.add(index, "AdMob");
                index++;
                newsCategory1.add(index, cat);
            }
            else {
                newsCategory1.add(index, cat);
            }
            index++;
        }
        return newsCategory1;
    }
 }
