package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {

    private static  final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "AlphaMily.db";
    public static final String TABLE_NAME = "User_table";
    public static final String COL_0 = "User_id";
    public static final String COL_1 = "User_email";
    public static final String COL_2 = "User_name";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( User_id INTEGER PRIMARY KEY AUTOINCREMENT, User_email TEXT, User_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public  boolean addUser(String user_email, String user_name){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if(!checkUserExists(user_email,user_name)){
            contentValues.put(COL_1, user_email);
            contentValues.put(COL_2, user_name);
            Log.d(TAG, "addData: Adding " + user_name + " to " + TABLE_NAME);

            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }else{
            Log.d(TAG, "User already exists in database !");
            return false;
        }
    }


    public boolean checkUserExists(String user_email, String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(
                "select * from "+TABLE_NAME +" where User_email = ? and User_name = ? ", new String[]{user_email, user_name},null);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        if(!listData.isEmpty()) {
            //daca exista un rezultat in lista => exista acel user
            return true;
        }else{
            //daca exista nu un rezultat in lista
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String quoery = "DELETE FROM " + TABLE_NAME;
        Log.d(TAG,"Delete data: " + quoery);
        db.execSQL(quoery);
    }
}
