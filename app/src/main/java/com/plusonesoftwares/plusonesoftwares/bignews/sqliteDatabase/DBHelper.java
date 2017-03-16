package com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashoksharma on 06/03/17.
 */

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        //All necessary tables you like to create will create here
        String CREATE_TABLE_NEWS = "CREATE TABLE " + NewsDataModel.TABLE  + "("
                + NewsDataModel.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + NewsDataModel.KEY_UniqueID + " TEXT ,"
                + NewsDataModel.KEY_Title + " TEXT ,"
                + NewsDataModel.KEY_ImageUrl + " TEXT ,"
                + NewsDataModel.KEY_Description + " TEXT ,"
                + NewsDataModel.KEY_Category + " INTEGER ,"
                + NewsDataModel.KEY_IsNext + " TEXT ) ";
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + NewsDataModel.TABLE);
        // Create tables again
        onCreate(db);
    }
}