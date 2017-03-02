package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.plusonesoftwares.plusonesoftwares.bignews.R;
import com.plusonesoftwares.plusonesoftwares.bignews.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static android.R.attr.layout_width;

public class DiscoverFragment extends Fragment {

   /*1. tamilHeadNews
   2. tamilCinemaNews
   3. tamilVikatanBusinessNews
   4. MalayalamHeadLinesNews
   5. MalayalamBusinessNews
   6. teluguBusinessNews
   7. MalayalamMovieNews
   8. MalayalamSportsNews
   9. MalayalamWorldNews
   10. MalayalamNationalNews*/

    Utils utils;
    String defaultCat;
    JSONObject JsonCategories;

    String[] mTextofButton = { "Tamil Head News", "Tamil Cinema News", "Tamil Vikatan Business News", "Malayalam Head Lines News", "Malayalam Business News", "Telugu Business News",
            "Malayalam Movie News", "Malayalam Sports News", "Malayalam World News" ,"Malayalam National News"};

    String[] catKeys = { "tamilHeadNews", "tamilCinemaNews", "tamilVikatanBusinessNews", "MalayalamHeadLinesNews", "MalayalamBusinessNews", "teluguBusinessNews",
            "MalayalamMovieNews", "MalayalamSportsNews", "MalayalamWorldNews" ,"MalayalamNationalNews"};

    String[] colorCodes = { "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8", "#aab7b8",
            "#aab7b8", "#aab7b8", "#aab7b8" ,"#aab7b8"};

    final String SelectedColor = "#04A94A";
    final String UnSelectedColor = "#aab7b8";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View discoverView = inflater.inflate(R.layout.activity_discover_fragment, container, false);

        utils = new Utils();

        getUpdatedCategories();

        try {
            createButtonsDynamically(discoverView, mTextofButton, colorCodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return discoverView;
    }

    private void getUpdatedCategories()
    {
        defaultCat = utils.getUserPrefs(utils.NewsCategories,getContext());
        try {
            JsonCategories = new JSONObject(defaultCat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createButtonsDynamically(View menuView, final String[] numberOfItems, final String[] BgColor) throws JSONException {
        TableLayout mTlayout;
        TableRow tr = null;
        int minusMargin = 0;
        int i = 0;

        mTlayout = (TableLayout) menuView.findViewById(R.id.tableLayout);

        TableRow.LayoutParams tableRowLayoutParams =
                new TableRow.LayoutParams((utils.getScreenWidth()- minusMargin)/2, (utils.getScreenWidth()-minusMargin)/2);
        tableRowLayoutParams.setMargins(0,0,10,10);

        while (i < numberOfItems.length) {
            final Button button = new Button(menuView.getContext());

            if (i % 2 == 0) {
                tr = new TableRow(menuView.getContext());
                mTlayout.addView(tr);
            }

//            TableLayout.LayoutParams rowParam =
//                    new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
//            tr.setLayoutParams(rowParam);

            button.setText(numberOfItems[i]);
            button.setLayoutParams(tableRowLayoutParams);
           // button.setPadding(0,0,5,5);
            button.setTextSize(16);
            button.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
            button.setTextColor(Color.BLACK);
            button.setId(i);
            if(selectedCategory(catKeys[i]))
            {
                button.setBackgroundColor(Color.parseColor(SelectedColor));
                button.setTextColor(Color.WHITE);
            }
            else
            {
                button.setBackgroundColor(Color.parseColor(BgColor[i]));
            }

            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int color = ((ColorDrawable)v.getBackground()).getColor();
                    String strColor = String.format("#%06X", 0xFFFFFF & color);

                    if(SelectedColor.equals(strColor)){
                        v.setBackgroundColor(Color.parseColor(UnSelectedColor)); // custom color
                        button.setTextColor(Color.BLACK);
                        getUpdatedCategories();
                        JsonCategories.remove(catKeys[finalI]);
                        utils.setUserPrefs(utils.NewsCategories, JsonCategories.toString(),getContext());
                    }
                    else
                    {
                        v.setBackgroundColor(Color.parseColor(SelectedColor)); // custom color
                        button.setTextColor(Color.WHITE);
                        try {
                            getUpdatedCategories();
                            JsonCategories.put(catKeys[finalI], numberOfItems[finalI]);
                            utils.setUserPrefs(utils.NewsCategories, JsonCategories.toString(),getContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            tr.addView(button);
            i++;
        }
    }

    private boolean selectedCategory(String CatValue) throws JSONException {

        JSONObject JsonCategories = new JSONObject(defaultCat);

        Iterator<String> iter = JsonCategories.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = JsonCategories.get(key);
                if(key.toString().equals(CatValue))
                    return key.toString().equals(CatValue);
            } catch (JSONException e) {
                // Something went wrong!
            }
        }
        return false;
    }
}

