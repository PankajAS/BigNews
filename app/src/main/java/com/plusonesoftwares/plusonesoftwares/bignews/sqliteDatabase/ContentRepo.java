package com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase;

/**
 * Created by ashoksharma on 06/03/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentRepo {

    private DBHelper dbHelper;

    public ContentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert_NewsData(List<NewsDataModel> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (NewsDataModel newsData : list) {
                //values.put(NewsDataModel.KEY_ID, newsData.ID);
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

    public ArrayList<HashMap<String, String>> getNewsData(String categoryName, String isNext) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String categoryWhereClause = "";
        if(!categoryName.equals(""))
        categoryWhereClause = " WHERE " + NewsDataModel.KEY_Category + " = ? AND "+NewsDataModel.KEY_IsNext + " = ?";
        //categoryWhereClause = " WHERE " + NewsDataModel.KEY_Category + " = ?";

        String selectQuery =  " SELECT  " +
                NewsDataModel.KEY_ID + "," +
                NewsDataModel.KEY_Title + "," +
                NewsDataModel.KEY_ImageUrl + "," +
                NewsDataModel.KEY_Description + "," +
                NewsDataModel.KEY_Category + "," +
                NewsDataModel.KEY_IsNext +
                " FROM " + NewsDataModel.TABLE + categoryWhereClause ;

        Cursor cursor;
        if(!categoryName.equals("")) {
            String[] params = new String[]{categoryName, isNext};
            //String[] params = new String[]{categoryName};
            cursor = db.rawQuery(selectQuery, params);
        }else {
            cursor = db.rawQuery(selectQuery, null);
        }


        //Student student = new Student();
        ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();


        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> category = new HashMap<String, String>();
                category.put("id", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_ID)));
                category.put("Title", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_Title)));
                category.put("ImageUrl", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_ImageUrl)));
                category.put("Description", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_Description)));
                category.put("Category", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_Category)));
                category.put("IsNext", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_IsNext)));
                categoryList.add(category);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoryList;
    }
}
