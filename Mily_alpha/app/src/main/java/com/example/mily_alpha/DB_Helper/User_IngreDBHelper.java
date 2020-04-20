package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class User_IngreDBHelper extends SQLiteOpenHelper {

    private static  final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "AlphaMily.db";
    public static final String TABLE_NAME = "User_Ingredient_table";
    public static final String COL_0 = "User_Ingredient_id";
    public static final String COL_1 = "User_id";
    public static final String COL_2 = "Ingredient_id";
    public static final String COL_3 = "Date";
    public static final String COL_4 = "Amount";
    public static final String COL_5 = "Categorie";

    public User_IngreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (User_Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, User_id INTEGER NOT NULL,  Ingredient_id INTEGER NOT NULL, Date TEXT, Amount TEXT, Categorie TEXT" +
                "FOREIGN KEY (User_id) REFERENCES User_table (User_id), FOREIGN KEY (Ingredient_Id) REFERENCES Ingredient_table (Ingredient_Id) )");
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
        contentValues.put(COL_5, Categorie);
        Log.d(TAG, "addData: Adding complete USER-INGREDIENT " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public  boolean addUser_Ingredient(int User_id, int Ingredient_id, String Date, String Amount, String Categorie){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, User_id);
        contentValues.put(COL_2, Ingredient_id);
        Log.d(TAG, "addData: Adding complete USER-INGREDIENT " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

}
