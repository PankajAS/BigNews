package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.plusonesoftwares.plusonesoftwares.bignews.CommonClass;
import com.plusonesoftwares.plusonesoftwares.bignews.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscoverFragment extends Fragment {


    public DiscoverFragment(String title) {

    }
    public DiscoverFragment() {

    }

    public static DiscoverFragment newInstance(String title) {

        return new DiscoverFragment(title);
    }

    CommonClass utils;
    JSONObject JsonCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View discoverView = inflater.inflate(R.layout.activity_discover_fragment, container, false);
        setHasOptionsMenu(true);
        utils = new CommonClass();
        JsonCategories = utils.getUpdatedCategories(getContext());
        //Toast.makeText(getContext(), " Called discover Tab", Toast.LENGTH_SHORT).show();

        try {
            createButtonsDynamically(discoverView, utils.mTextofButton, utils.colorCodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return discoverView;
    }

    private void createButtonsDynamically(View menuView, final String[] numberOfItems, final String[] BgColor) throws JSONException {
        TableLayout mTlayout;
        TableRow tr = null;
        int i = 0;

        mTlayout = (TableLayout) menuView.findViewById(R.id.tableLayout);

        TableRow.LayoutParams tableRowLayoutParams =
                new TableRow.LayoutParams((utils.getScreenWidth())/2, (utils.getScreenWidth())/2);
        tableRowLayoutParams.setMargins(0,0,5,5);

        while (i < numberOfItems.length) {
            final Button button = new Button(menuView.getContext());

            if (i % 2 == 0) {
                tr = new TableRow(menuView.getContext());
                mTlayout.addView(tr);
            }
            button.setText(numberOfItems[i]);
            button.setLayoutParams(tableRowLayoutParams);
            button.setLines(1);
            button.setTextSize(14);
            button.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
            button.setTextColor(Color.BLACK);
            button.setId(i);
            if(selectedCategory(utils.catKeys[i]))
            {
                button.setBackgroundColor(Color.parseColor(utils.SelectedColor));
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

                    if(utils.SelectedColor.equals(strColor)){// un-select the category
                        v.setBackgroundColor(Color.parseColor(utils.UnSelectedColor)); // custom color
                        button.setTextColor(Color.BLACK);
                        JsonCategories = utils.getUpdatedCategories(getContext());
                        JsonCategories.remove(utils.catKeys[finalI]);
                        utils.setUserPrefs(utils.NewsCategories, JsonCategories.toString(),getContext());
                    }
                    else //selecting new category
                    {
                        v.setBackgroundColor(Color.parseColor(utils.SelectedColor)); // custom color
                        button.setTextColor(Color.WHITE);
                        String category = utils.catKeys[finalI];
                        try {
                            JsonCategories = utils.getUpdatedCategories(getContext());
                            JsonCategories.put(category, numberOfItems[finalI]);
                            utils.setUserPrefs(utils.NewsCategories, JsonCategories.toString(),getContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //******Request async data for new selected data for update or insert********
                        ArrayList<String> newsCategory = new ArrayList<>();
                        newsCategory.add(utils.Url + category);
                        newsCategory.add(utils.nextUrl + category);
                        utils.insertUpdateNews(newsCategory, getContext());
                        //******Request async data for new selected data for update or insert********
                    }
                }
            });
            tr.addView(button);
            i++;
        }
    }

    private boolean selectedCategory(String CatValue) throws JSONException {

        JsonCategories = utils.getUpdatedCategories(getContext());

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

