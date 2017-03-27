package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.CommonClass;
import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.GetNewsData;
import com.plusonesoftwares.plusonesoftwares.bignews.R;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment {
    private FlipViewController flipView;
    CommonClass utils;
    List<String> newsCategory1;
    boolean shouldExecuteOnResume;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        shouldExecuteOnResume = false;
        utils = new CommonClass();
        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flipView = new FlipViewController(getActivity(),getContext(), FlipViewController.VERTICAL);

        newsCategory1 = utils.getUpdatedData(getContext());

        flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory1));

        return flipView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(shouldExecuteOnResume){
            if( utils.getUserPrefs(utils.isBackKeyPressed,getContext())!=null && utils.getUserPrefs(utils.isBackKeyPressed,getContext()).equals("true")) {
                utils.setUserPrefs(utils.isBackKeyPressed, "false", getContext());
            }
            else
            {
                //System.out.println("onResume");
                getActivity().setTitle(utils.getUserPrefs(utils.CategroyTitle, getContext()));
                utils.getUpdatedData(getContext());
                flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory1));//to refresh the  main activity on pressed of home button
            }

        } else{
            shouldExecuteOnResume = true;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_refresh:
                if(utils.haveNetworkConnection(getContext()))
                {
                    if(utils.getUserPrefs(utils.isPendingRequest, getContext()) == null || utils.getUserPrefs(utils.isPendingRequest, getContext()).equals("false")) {
                        utils.setUserPrefs(utils.isPendingRequest, "true", getContext());
                        ArrayList<String> newsCategory = new ArrayList<>();
                        newsCategory = utils.getFollowedCategoriesLink(getContext(), true, false, false);
                        progressDialog.setMessage(getString(R.string.splashMessage2));
                        progressDialog.show();//updating only followed categories.

                        utils.insertUpdateNews(newsCategory, getContext(), true, true, progressDialog, getActivity(), flipView);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
