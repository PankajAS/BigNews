package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Plus 3 on 17-03-2017.
 */

public class NewsBroadcastReceiver extends BroadcastReceiver {
    ArrayList<String> newsCategory = new ArrayList<>();
    Utils utils = new Utils();

    @Override
    public void onReceive(Context context, Intent intent) {
        newsCategory = utils.getFollowedCategoriesLink(context, true, true);
        boolean isLastRequest = false;
        int parentIndex = 0;
        Boolean isInsert = false;
        for (String url : newsCategory) {
            try {
                new GetNewsData(context, utils.getIsNext(newsCategory, parentIndex),
                        utils.getCategoryName(url), isLastRequest, null, isInsert).execute(new URL(url));//start async task to get all categories
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            parentIndex++;
        }
    }
}
