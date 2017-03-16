package com.plusonesoftwares.plusonesoftwares.bignews.sqliteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 11-03-2017.
 */

public class NewsDataModel {
    // Labels table name
    public static final String TABLE = "tblNewsData";
    // Labels Table Columns names
    public  static final String KEY_ID = "ID";
    public  static final String KEY_UniqueID = "UniqueId";
    public  static final String KEY_Title = "Title";
    public  static final String KEY_ImageUrl = "ImageUrl";
    public  static final String KEY_Description = "Description";
    public  static final String KEY_Category = "Category";
    public  static final String KEY_IsNext = "IsNext";

    //property help us to keep data
    public String ID;
    public String UniqueId;
    public String Title;
    public String ImageUrl;
    public String Description;
    public static String Category;
    public static String IsNext;
}
