package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.HttpConnection;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    DiscoverFragment home;
    FragmentManager manger;
    private FlipViewController flipView;
    String Url = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getFirstNewsList?newsCategory=";
    HttpConnection httpConnection;
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject;
    ArrayList<String> urllist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        httpConnection = new HttpConnection();
        //View homeView = inflater.inflate(R.layout.activity_home_fragment, container, false);
        flipView = new FlipViewController(getContext(), FlipViewController.VERTICAL);
        urllist = new ArrayList<>();
        urllist.add(Url+"tamilHeadNews");
        urllist.add(Url+"tamilCinemaNews");
        urllist.add(Url+"tamilVikatanBusinessNews");
        urllist.add(Url+"MalayalamHeadLinesNews");
        urllist.add(Url+"MalayalamBusinessNews");
        urllist.add(Url+"teluguBusinessNews");
        urllist.add(Url+"MalayalamMovieNews");
        urllist.add(Url+"MalayalamSportsNews");
        urllist.add(Url+"MalayalamWorldNews");
        urllist.add(Url+"MalayalamNationalNews");

        // jsonArray = httpConnection.new FetchData(getContext()).execute(new URL(Url+"tamilVikatanBusinessNews")).get();
        flipView.setAdapter(new TravelAdapter(getContext(), urllist));

        return flipView;
    }

}
