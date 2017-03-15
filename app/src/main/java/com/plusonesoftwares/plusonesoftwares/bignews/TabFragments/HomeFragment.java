package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;
import com.plusonesoftwares.plusonesoftwares.bignews.Utils;

public class HomeFragment extends Fragment {
    private FlipViewController flipView;
    Utils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flipView = new FlipViewController(getContext(), FlipViewController.VERTICAL);
        utils = new Utils();
        flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), utils.getFollowedCategoriesLink(getContext(), false, false)));

        return flipView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
