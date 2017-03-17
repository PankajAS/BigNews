package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;
import com.plusonesoftwares.plusonesoftwares.bignews.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FlipViewController flipView;
    Utils utils;
    List<String> newsCategory1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flipView = new FlipViewController(getActivity(), FlipViewController.VERTICAL);
        utils = new Utils();

        List<String> newsCategory = utils.getFollowedCategoriesLink(getContext(), false, false);
        newsCategory1 = new ArrayList<>();
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

        flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory1));

        return flipView;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(utils.getUserPrefs(utils.CategroyTitle, getContext()));
        flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory1));//to refresh the  main activity on pressed of home button
        super.onResume();
    }
}
