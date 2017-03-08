package com.plusonesoftwares.plusonesoftwares.bignews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Plus 3 on 08-03-2017.
 */

public class ImageDownloader extends AsyncTask<String, Void , Bitmap> {

    private ImageView imv;
    // private String path;

    public ImageDownloader(ImageView imv) {
        this.imv = imv;
        //this.path = imv.getTag().toString();
    }


    @Override
    protected Bitmap doInBackground(String... strings) {

        try {

            URL url = new URL("http:"+strings[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStream inputStream = connection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

            return myBitmap;

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imv.setImageBitmap(bitmap);
        super.onPostExecute(bitmap);
    }
}

