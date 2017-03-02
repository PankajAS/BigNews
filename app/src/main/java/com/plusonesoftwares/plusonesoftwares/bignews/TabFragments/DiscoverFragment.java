package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.R;

public class DiscoverFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View discoverView = inflater.inflate(R.layout.activity_discover_fragment, container, false);

        return discoverView;
    }
}
