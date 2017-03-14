package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;
import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    DiscoverFragment home;
    FragmentManager manger;
    private FlipViewController flipView;
    String Url = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getFirstNewsList?newsCategory=";
    String nextUrl = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getNextNewsList?newsCategory=";

    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject;
    ArrayList<String> urllist;
    List<String> newsCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


       // View homeView = inflater.inflate(R.layout.activity_home_fragment, container, false);
        urllist = new ArrayList<>();
        newsCategory= new ArrayList<>();
        urllist.add(Url+"indiaHeadLinesNews");
        urllist.add(Url+"indiaMovieNews");
        urllist.add(Url+"indiaBusinessNews");
       /*urllist.add(Url+"MalayalamHeadLinesNews");
        urllist.add(Url+"MalayalamBusinessNews");
        urllist.add(Url+"teluguBusinessNews");
        urllist.add(Url+"MalayalamMovieNews");
        urllist.add(Url+"MalayalamSportsNews");
        urllist.add(Url+"MalayalamWorldNews");
        urllist.add(Url+"MalayalamNationalNews");
         urllist.add(nextUrl+"MalayalamHeadLinesNews");*/
        urllist.add(nextUrl+"indiaHeadLinesNews");
        urllist.add(nextUrl+"indiaMovieNews");
        urllist.add(nextUrl+"indiaBusinessNews");


        //Heading news category name
        newsCategory.add("indiaHeadLinesNews");
        newsCategory.add("indiaMovieNews");
        newsCategory.add("indiaBusinessNews");
      /*  newsCategory.add("Malayalam HeadLines News");
        newsCategory.add("Malayalam Business News");
        newsCategory.add("Telugu Business News");
        newsCategory.add("Malayalam Movie News");
        newsCategory.add("Malayalam Sports News");
        newsCategory.add("Malayalam World News");
        newsCategory.add("Malayalam National News");
        newsCategory.add("Tamil Head News");
        newsCategory.add("Tamil Cinema News");
        newsCategory.add("Tamil Vikatan Business News");
        newsCategory.add("Malayalam HeadLines News");*/
        newsCategory.add("indiaHeadLinesNews");
        newsCategory.add("indiaMovieNews");
        newsCategory.add("indiaBusinessNews");

        flipView = new FlipViewController(getContext(), FlipViewController.VERTICAL);

        ContentRepo data = new ContentRepo(getContext());
        ArrayList<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();
        newsList = data.getNewsData("","");

        JSONArray mJSONArray = new JSONArray(Arrays.asList(newsList));
        JSONArray jsonArray1 = new JSONArray();
        try {
            System.out.println(mJSONArray.toString(2));
            jsonArray1 = mJSONArray.getJSONArray(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        flipView.setAdapter(new TravelAdapter(getContext(),getActivity(), urllist, newsCategory, jsonArray1));

        return flipView;
    }


}
