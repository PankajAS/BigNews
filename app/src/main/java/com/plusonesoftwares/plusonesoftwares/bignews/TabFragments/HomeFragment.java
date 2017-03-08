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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
    DiscoverFragment home;
    FragmentManager manger;
    private FlipViewController flipView;
    String Url = "https://flip-dev-app.appspot.com/_ah/api/flipnewsendpoint/v1/getFirstNewsList?newsCategory=tamilHeadNews";
    HttpConnection httpConnection;
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        httpConnection = new HttpConnection();
        //View homeView = inflater.inflate(R.layout.activity_home_fragment, container, false);
        flipView = new FlipViewController(getContext(), FlipViewController.VERTICAL);


        try {

            jsonArray = httpConnection.new FetchData(getContext()).execute(new URL(Url)).get();
            flipView.setAdapter(new TravelAdapter(getContext(), jsonArray));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return flipView;
    }

}
