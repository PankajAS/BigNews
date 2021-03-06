package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase.ContentRepo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    List<String> string;
    ArrayList<String> newsCategory = new ArrayList<>();
    TextView txtViewMessage;
    CommonClass utils;
    ContentRepo contentOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startAlert();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtViewMessage = (TextView) findViewById(R.id.txtViewMessage);

        contentOperation = new ContentRepo(getApplicationContext());
        utils = new CommonClass();

        if (!utils.keyExist(getApplicationContext())) {
            utils.defaultNewsCategories(getApplicationContext());//setting default news categories for first time when user install the app
        }

        if (utils.haveNetworkConnection(getApplicationContext())) {

            boolean isLastRequest = false;
            int parentIndex = 0;

            if (contentOperation.dataAlreadyExist()) {
                txtViewMessage.setText(R.string.splashMessage2);
                newsCategory = utils.getFollowedCategoriesLink(getApplicationContext(), true, false,false);
            }
            else
            {
                newsCategory = utils.getFollowedCategoriesLink(getApplicationContext(), true, true, false);
            }

            for (String url : newsCategory) {
                isLastRequest = (parentIndex == 2);
                try {
                    new GetNewsData(getApplicationContext(), utils.getIsNext(newsCategory, parentIndex), utils.getCategoryName(url), isLastRequest,false, SplashScreenActivity.this).execute(new URL(url));//start async task to get all categories
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
    }

    public void startAlert() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(SplashScreenActivity.this, NewsBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SplashScreenActivity.this, 0, intent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
        //Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
}

