package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.plusonesoftwares.plusonesoftwares.bignews.NewsCategoryDetails;
import com.plusonesoftwares.plusonesoftwares.bignews.R;
import com.plusonesoftwares.plusonesoftwares.bignews.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class MenuFragment extends Fragment {

    Utils utils;
    String defaultCat;


    String[] colorCodes = { "#cd6155", "#DAF7A6", "#FFC300", "#FF5733", "#C70039", "#ba4a00", "#5d6d7e"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View menuView = inflater.inflate(R.layout.activity_menu_fragment, container, false);

        utils = new Utils();
        defaultNewsCategories();
        defaultCat = utils.getUserPrefs(utils.NewsCategories,getContext());
        try {
            createButtonsDynamically(menuView, utils.mTextofButton, colorCodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return menuView;
    }

    public void createButtonsDynamically(View menuView, String[] numberOfItems, final String[] BgColor) throws JSONException {

        TableLayout mTlayout;
        TableRow tr = null;

        mTlayout = (TableLayout) menuView.findViewById(R.id.tableLayout);

        TableRow.LayoutParams tableRowLayoutParams =
                new TableRow.LayoutParams((utils.getScreenWidth()) / 2, (utils.getScreenWidth()) / 2);
        //tableRowLayoutParams.setMargins(0,0,10,10);

        final JSONObject JsonCategories = new JSONObject(defaultCat);

        Iterator<String> iter = JsonCategories.keys();

        int i = 0;
        Object categoryName = null;
        while (iter.hasNext()) {

            String key = iter.next();
            try {
                categoryName = JsonCategories.get(key);
            } catch (JSONException e) {
                // Something went wrong!
            }
            final Button button = new Button(menuView.getContext());

            if (i % 2 == 0) {
                tr = new TableRow(menuView.getContext());
                mTlayout.addView(tr);
            }

            button.setText(categoryName.toString());
            button.setLayoutParams(tableRowLayoutParams);
            //button.setPadding(7,10,7,10);
            button.setTextSize(16);
            button.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
            //button.setBackgroundColor(Color.parseColor(BgColor[i])); //custom color
            button.setTextColor(Color.BLACK);
            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), NewsCategoryDetails.class);
                    intent.putExtra("categoryName",((Button) v).getText());
                    startActivity(intent);
                }
            });

            tr.addView(button);
            i++;
        }
    }
    private void defaultNewsCategories()
    {
        JSONObject FollowedCategories = new JSONObject();
        try {
            FollowedCategories.put("tamilHeadNews", "Tamil Head News");
            FollowedCategories.put("tamilCinemaNews", "Tamil Cinema News");
            FollowedCategories.put("tamilVikatanBusinessNews", "Tamil Vikatan Business News");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(!utils.keyExist(getContext()))
            utils.setUserPrefs(utils.NewsCategories, FollowedCategories.toString() ,getContext());
    }
}

