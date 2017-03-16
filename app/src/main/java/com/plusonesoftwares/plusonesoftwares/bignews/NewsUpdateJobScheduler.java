package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Plus 3 on 16-03-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NewsUpdateJobScheduler extends JobService {
    ArrayList<String> newsCategory = new ArrayList<>();
    Utils utils = new Utils();
    Activity activity;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        newsCategory = utils.getFollowedCategoriesLink(getApplicationContext(), true, true);
        boolean isLastRequest = false;
        int parentIndex = 0;
        Boolean isInsert = false;
        for (String url : newsCategory) {
            try {
                new GetNewsData(getApplicationContext(), utils.getIsNext(newsCategory, parentIndex),
                        utils.getCategoryName(url), isLastRequest, null, isInsert).execute(new URL(url));//start async task to get all categories
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            parentIndex++;
        }
        //Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
