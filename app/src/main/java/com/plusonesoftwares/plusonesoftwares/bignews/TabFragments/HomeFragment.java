package com.plusonesoftwares.plusonesoftwares.bignews.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.TravelAdapter;
import com.plusonesoftwares.plusonesoftwares.bignews.CommonClass;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FlipViewController flipView;
    CommonClass utils;
    List<String> newsCategory1;
    boolean shouldExecuteOnResume;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shouldExecuteOnResume = false;
        utils = new CommonClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        flipView = new FlipViewController(getActivity(),getContext(), FlipViewController.VERTICAL);

        getUpdatedData();

        flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory1));

        return flipView;
    }

    private void getUpdatedData() {
        List<String> newsCategory = utils.getFollowedCategoriesLink(getContext(), false, false);

        // utils.getCatWithAdmob(utils.getFollowedCategoriesLink(getContext(), false, false));
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
                getUpdatedData();
                flipView.setAdapter(new TravelAdapter(getContext(), getActivity(), newsCategory1));//to refresh the  main activity on pressed of home button
            }

        } else{
            shouldExecuteOnResume = true;
        }
    }
}
