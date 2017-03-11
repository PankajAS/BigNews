package com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase;

/**
 * Created by ashoksharma on 06/03/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ContentRepo {

    private DBHelper dbHelper;

    public ContentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert_Content(List<NewsDataModel> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (NewsDataModel newsData : list) {
                values.put(NewsDataModel.KEY_ID, newsData.ID);
                values.put(NewsDataModel.KEY_Title, newsData.Title);
                values.put(NewsDataModel.KEY_ImageUrl, newsData.ImageUrl);
                values.put(NewsDataModel.KEY_Description, newsData.Description);
                values.put(NewsDataModel.KEY_Category, newsData.Category);
                values.put(NewsDataModel.KEY_IsNext, newsData.IsNext);
                db.insert(NewsDataModel.TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
