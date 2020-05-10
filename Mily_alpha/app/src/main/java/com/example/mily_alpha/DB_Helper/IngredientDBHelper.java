package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class IngredientDBHelper extends SQLiteOpenHelper {
    private static  final String TAG = "DatabaseHelper";
    private static final int  DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "AlphaMily.db";
    public static final String TABLE_NAME = "Ingredient_table";
    public static final String COL_0 = "Ingredient_id";
    public static final String COL_1 = "Ingredient_name";
    public static final String COL_2 = "Ingredient_calories";

    public IngredientDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, Ingredient_name TEXT, Ingredient_calories INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  boolean addIngredient(String ingredient_name){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        // FUNCTIE DE CALCULAT CALORIEILE PER INGREDIENT
        int Ingredient_calories = 0;

        if(!checkIngredientExists(ingredient_name)){
            contentValues.put(COL_1, ingredient_name);
            contentValues.put(COL_2, Ingredient_calories);
            Log.d(TAG, "addData: Adding " + ingredient_name + " to " + TABLE_NAME);

            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }else{
            Log.d(TAG, "Ingredient already exists in database !");
            return false;
        }
    }

    public boolean checkIngredientExists(String ingredient_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(
                "select * from "+TABLE_NAME +" where ingredient_name = ? ", new String[]{ingredient_name},null);
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

    public ArrayList<String> getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        if(!listData.isEmpty())
            return listData;
        else
            return null;
    }

    public int getIngredientID(String ingredient_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Ingredient_id from " + TABLE_NAME + " where ingredient_name = ?", new String[]{ingredient_name}, null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return Integer.parseInt(listData.get(0));
        else
            return 0;
    }

    public String getIngredientById(String ingredientId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Ingredient_name from " + TABLE_NAME + " where Ingredient_id = ?", new String[]{ingredientId}, null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String quoery = "DELETE FROM " + TABLE_NAME;
        Log.d(TAG,"Delete data: " + quoery);
        db.execSQL(quoery);
    }
}
