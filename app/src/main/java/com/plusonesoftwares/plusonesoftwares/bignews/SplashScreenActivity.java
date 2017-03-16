package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    List<String> string;
    ArrayList<String> newsCategory = new ArrayList<>();
    TextView txtViewMessage;
    Utils utils;
    ContentRepo contentOperation;
    JobScheduler jobScheduler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtViewMessage = (TextView) findViewById(R.id.txtViewMessage);
        jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);

        contentOperation = new ContentRepo(getApplicationContext());
        utils = new Utils();

        if (!utils.keyExist(getApplicationContext())) {
            utils.defaultNewsCategories(getApplicationContext());//setting default news categories for first time when user install the app
        }

        if (utils.haveNetworkConnection(getApplicationContext())) {
            newsCategory = utils.getFollowedCategoriesLink(getApplicationContext(), true, true);

            boolean isLastRequest = false;
            int parentIndex = 0;
            Boolean isInsert = true;

            if (contentOperation.dataAlreadyExist()) {
                isInsert = false;
                txtViewMessage.setText(R.string.splashMessage2);
            }

            for (String url : newsCategory) {
                isLastRequest = (parentIndex == 3);
                try {
                    new GetNewsData(getApplicationContext(), utils.getIsNext(newsCategory, parentIndex), utils.getCategoryName(url), isLastRequest, SplashScreenActivity.this, isInsert).execute(new URL(url));//start async task to get all categories
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                parentIndex++;
            }
        } else {
            utils.showNetworkConnectionMsg(SplashScreenActivity.this);
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
        jobSchedulerService();//initialization of job Scheduler
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void jobSchedulerService() {
        ComponentName mServiceComponent = new ComponentName(getApplicationContext(), NewsUpdateJobScheduler.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, mServiceComponent);
        builder.setPeriodic(43200000);//total time of scheduler is 12 hours in milliseconds(1000*60*60*12)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(true);
        JobInfo jobInfo = builder.build();
        jobScheduler.schedule(jobInfo);
    }
}

