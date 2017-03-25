package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Plus 3 on 17-03-2017.
 */

public class NewsBroadcastReceiver extends BroadcastReceiver {
    ArrayList<String> newsCategory = new ArrayList<>();
    CommonClass utils = new CommonClass();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(utils.haveNetworkConnection(context))
        {
            newsCategory = utils.getFollowedCategoriesLink(context, true, true, true);
            utils.insertUpdateNews(newsCategory, context, false, false);
        }
    }
}
