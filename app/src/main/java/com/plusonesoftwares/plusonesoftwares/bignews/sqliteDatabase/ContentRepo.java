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
                values.put(NewsDataModel.KEY_UniqueID, newsData.UniqueId);
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
            db.close();
        }
    }

    public void update_NewsData(List<NewsDataModel> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (NewsDataModel newsData : list) {
                values.put(NewsDataModel.KEY_Title, newsData.Title);
                values.put(NewsDataModel.KEY_ImageUrl, newsData.ImageUrl);
                values.put(NewsDataModel.KEY_Description, newsData.Description);
                //db.update(NewsDataModel.TABLE, values, NewsDataModel.KEY_Category + " = '"+newsData.Category.toString()+ "' AND " + NewsDataModel.KEY_IsNext + " = '"+newsData.IsNext+"'",null);
                db.update(NewsDataModel.TABLE,
                        values,
                       // NewsDataModel.KEY_Category + " = ? AND " + NewsDataModel.KEY_IsNext + " = ? AND " + NewsDataModel.KEY_UniqueID + " = ? ",
                        NewsDataModel.KEY_UniqueID + " = ? ",
                        //new String[]{String.valueOf(newsData.Category), String.valueOf(newsData.IsNext), String.valueOf(newsData.UniqueId)});
                        new String[]{String.valueOf(newsData.UniqueId)});

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void delete_NewsData(String categoryName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(NewsDataModel.TABLE, NewsDataModel.KEY_Category + "=?", new String[]{categoryName});
        db.close();
    }

    public boolean dataAlreadyExist() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = " SELECT ID FROM "+ NewsDataModel.TABLE;
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public boolean dataAlreadyExist(String Category, String isNext) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = " SELECT ID FROM "+ NewsDataModel.TABLE + " WHERE " + NewsDataModel.KEY_Category + " = ? AND "+ NewsDataModel.KEY_IsNext + " = ?";
        Cursor cursor;
        String[] params = new String[]{Category, isNext};
        cursor = db.rawQuery(sql, params);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public ArrayList<HashMap<String, String>> getNewsData(String categoryName, String isNext) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String categoryWhereClause = "";
        if(categoryName != null && !categoryName.isEmpty())
            categoryWhereClause = " WHERE " + NewsDataModel.KEY_Category + " = ? AND "+ NewsDataModel.KEY_IsNext + " = ?";

        String selectQuery =  " SELECT  " +
                NewsDataModel.KEY_ID + "," +
                NewsDataModel.KEY_Title + "," +
                NewsDataModel.KEY_ImageUrl + "," +
                NewsDataModel.KEY_Description + "," +
                NewsDataModel.KEY_Category + "," +
                NewsDataModel.KEY_IsNext + "," +
                NewsDataModel.KEY_UniqueID +
                " FROM " + NewsDataModel.TABLE + categoryWhereClause ;

        Cursor cursor;
        if(categoryName != null && !categoryName.isEmpty()) {
            String[] params = new String[]{categoryName, isNext};
            cursor = db.rawQuery(selectQuery, params);
        }else {
            cursor = db.rawQuery(selectQuery, null);
        }
        ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> category = new HashMap<String, String>();
                category.put("id", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_ID)));
                category.put("Title", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_Title)));
                category.put("ImageUrl", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_ImageUrl)));
                category.put("Description", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_Description)));
                category.put("Category", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_Category)));
                category.put("IsNext", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_IsNext)));
                category.put("UniqueID", cursor.getString(cursor.getColumnIndex(NewsDataModel.KEY_UniqueID)));
                categoryList.add(category);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoryList;
    }

    public ArrayList<HashMap<String, String>> getAllNewsDataByCategory(String categoryName) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String categoryWhereClause = " WHERE " + NewsDataModel.KEY_Category + " = ? ORDER BY ID";

        String selectQuery =  " SELECT  " +
                NewsDataModel.KEY_ID + "," +
                NewsDataModel.KEY_Title + "," +
                NewsDataModel.KEY_ImageUrl + "," +
                NewsDataModel.KEY_Description + "," +
                NewsDataModel.KEY_Category + "," +
                NewsDataModel.KEY_IsNext +
                " FROM " + NewsDataModel.TABLE + categoryWhereClause ;

        Cursor cursor;
        String[] params = new String[]{categoryName};
        cursor = db.rawQuery(selectQuery, params);

        ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

        //looping through all rows and adding to list
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
