package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.R;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;
import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    DiscoverFragment home;
    FragmentManager manger;
    private FlipViewController flipView;
    ArrayList<Travels.Data> array;
    ListView list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View homeView = inflater.inflate(R.layout.activity_home_fragment, container, false);


        flipView = new FlipViewController(getContext(), FlipViewController.VERTICAL);
        flipView.setAdapter(new TravelAdapter(getContext()));




        return flipView;
    }

}
