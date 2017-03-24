package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.CommonClass;
import com.plusonesoftwares.plusonesoftwares.bignews.R;
import com.plusonesoftwares.plusonesoftwares.bignews.NewsCategoryAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MenuFragment extends Fragment {

    CommonClass utils;
    String defaultCat;
    View menuView;
    private RecyclerView recyclerView;
    private NewsCategoryAdapter adapter;
    List<String> getFollowedCat;

    String[] colorCodes = { "#cd6155", "#DAF7A6", "#FFC300", "#FF5733", "#C70039", "#ba4a00", "#5d6d7e", "#cd6155", "#DAF7A6", "#FFC300"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        menuView = inflater.inflate(R.layout.activity_discover_view, container, false);
        utils = new CommonClass();
        defaultCat = utils.getUserPrefs(utils.NewsCategories,getContext());
        recyclerView = (RecyclerView) menuView.findViewById(R.id.recycler_view);
        getFollowedCat = getFollowedCat(defaultCat);
        adapter = new NewsCategoryAdapter(getContext(), MenuFragment.this, recyclerView, getFollowedCat);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

       /* defaultCat = utils.getUserPrefs(utils.NewsCategories,getContext());
        try {
            createButtonsDynamically(menuView, utils.mTextofButton, colorCodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return menuView;
    }

    private List<String> getFollowedCat(String value) {
        JSONObject JsonCategories = null;
        try {
            JsonCategories = new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Iterator<String> iter = JsonCategories.keys();

        int i = 0;
        List<String> categoryName = new ArrayList<>();
        while (iter.hasNext()) {

            String key = iter.next();
            try {
                categoryName.add(String.valueOf(JsonCategories.get(key)));
            } catch (JSONException e) {
                // Something went wrong!
            }
        }
            return categoryName;

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onResume() {

        defaultCat = utils.getUserPrefs(utils.NewsCategories,getContext());
        getFollowedCat = getFollowedCat(defaultCat);
        recyclerView.setAdapter(new NewsCategoryAdapter(getContext(), MenuFragment.this, recyclerView, getFollowedCat));
        recyclerView.invalidate();
        super.onResume();
    }

    /*public void createButtonsDynamically(View menuView, String[] numberOfItems, final String[] BgColor) throws JSONException {

        TableLayout mTlayout;
        TableRow tr = null;
        mTlayout = (TableLayout) menuView.findViewById(R.id.tableLayout);

        mTlayout.removeAllViews();
        TableRow.LayoutParams tableRowLayoutParams =
                new TableRow.LayoutParams((utils.getScreenWidth()) / 2, (utils.getScreenWidth()) / 2);
        tableRowLayoutParams.setMargins(0,0,5,5);

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
            button.setLines(1);
            button.setTextSize(14);
            button.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
            button.setBackgroundColor(Color.parseColor(BgColor[i])); //custom color
            button.setTextColor(Color.BLACK);
            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), NewsCategoryDetails.class);
                    intent.putExtra("categoryName",((Button) v).getText());
                    //Storing current index of flipper
                    utils.setUserPrefs(utils.flipCurrentIndex, "" ,getContext());
                    startActivity(intent);
                }
            });

            tr.addView(button);
            i++;
       }
    }*/


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}

