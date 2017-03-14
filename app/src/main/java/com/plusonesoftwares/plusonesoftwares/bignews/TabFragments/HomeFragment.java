package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;
import com.plusonesoftwares.plusonesoftwares.bignews.Utils;
import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private FlipViewController flipView;
    List<String> newsCategory;
    Utils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flipView = new FlipViewController(getContext(), FlipViewController.VERTICAL);
        utils = new Utils();

        newsCategory = utils.getFollowedCategoriesLink(getContext(), false);
        flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory));

        return flipView;
    }
}
