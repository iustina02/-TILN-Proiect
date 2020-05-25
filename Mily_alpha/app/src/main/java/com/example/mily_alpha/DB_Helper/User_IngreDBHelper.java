package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

public class User_IngreDBHelper extends SQLiteOpenHelper {

    private static  final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "AlphaMily.db";
    private static final int  DATABASE_VERSION = 10;
    public static final String TABLE_NAME = "User_Ingredient_table";
    public static final String COL_0 = "User_Ingredient_id";
    public static final String COL_1 = "User_id";
    public static final String COL_2 = "Ingredient_id";
    public static final String COL_3 = "Date";
    public static final String COL_4 = "Amount";
    public static final String COL_5 = "Categorie";

    public User_IngreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (User_Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, User_id INTEGER NOT NULL,  Ingredient_id INTEGER NOT NULL, Date TEXT, Amount TEXT, Categorie TEXT, FOREIGN KEY (User_id) REFERENCES User_table (User_id), FOREIGN KEY (Ingredient_Id) REFERENCES Ingredient_table (Ingredient_Id) )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  boolean addUser_Ingredient(int User_id, int Ingredient_id, String Categorie){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, User_id);
        contentValues.put(COL_2, Ingredient_id);
        contentValues.put(COL_3, "Date");
        contentValues.put(COL_4, "Amount");
        contentValues.put(COL_5, Categorie);
        Log.d(TAG, "addData: Adding complete USER-INGREDIENT " + Categorie + " "+ TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public  boolean addUser_Ingredient(int User_id, int Ingredient_id, String Date, String Categorie){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, User_id);
        contentValues.put(COL_2, Ingredient_id);
        contentValues.put(COL_3, Date);
        contentValues.put(COL_5, Categorie);
        Log.d(TAG, "addData: Adding complete USER-INGREDIENT " + Categorie + " "+ TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<String> getCategoriesFromUser(String User_id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("select Ingredient_id from "+TABLE_NAME +" where User_id = ?", new String[]{User_id},null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData;
        else
            return null;
    }

    public ArrayList<String> GetProdusByCategAndUser(int UserID, String Categorie){
        ArrayList<String> listData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Ingredient_id from "+TABLE_NAME +" where User_id = ? and Categorie = ? ", new String[]{UserID+"", Categorie},null);

        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty()) {
            return listData;
        }
        else
            return null;
    }
}
