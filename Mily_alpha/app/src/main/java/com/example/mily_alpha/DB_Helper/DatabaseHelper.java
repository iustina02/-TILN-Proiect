package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static  final String TAG = "DatabaseHelper";
    private static final int  DATABASE_VERSION = 12;
    public static final String DATABASE_NAME = "AlphaMily.db";

    public static final String USER_TABLE = "User_table";
    public static final String USER_TABLE_COL_0 = "User_id";
    public static final String USER_TABLE_COL_1 = "User_email";
    public static final String USER_TABLE_COL_2 = "User_name";

    public static final String INGREDIENT_TABLE = "Ingredient_table";
    public static final String INGREDIENT_TABLE_COL_0 = "Ingredient_id";
    public static final String INGREDIENT_TABLE_COL_1 = "Ingredient_name";
    public static final String INGREDIENT_TABLE_COL_2 = "Ingredient_calories";

    public static final String USER_INGR_TABLE = "User_Ingredient_table";
    public static final String USER_INGR_TABLE_COL_0 = "User_Ingredient_id";
    public static final String USER_INGR_TABLE_COL_1 = "User_id";
    public static final String USER_INGR_TABLE_COL_2 = "Ingredient_id";
    public static final String USER_INGR_TABLE_COL_3 = "Date";
    public static final String USER_INGR_TABLE_COL_4 = "InShoppingList";
    public static final String USER_INGR_TABLE_COL_5 = "Categorie";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " ( User_id INTEGER PRIMARY KEY AUTOINCREMENT, User_email TEXT, User_name TEXT)");
        db.execSQL("create table " + INGREDIENT_TABLE + " ( Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, Ingredient_name TEXT, Ingredient_calories INTEGER)");
        db.execSQL("create table " + USER_INGR_TABLE + " (User_Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, User_id INTEGER NOT NULL,  Ingredient_id INTEGER NOT NULL, Date TEXT, InShoppingList INTEGER, Categorie TEXT, FOREIGN KEY (User_id) REFERENCES User_table (User_id), FOREIGN KEY (Ingredient_Id) REFERENCES Ingredient_table (Ingredient_Id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_INGR_TABLE);
        onCreate(db);
    }


//    USER ACTIONS
    public  boolean addUser(String user_email, String user_name){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if(!checkUserExists(user_email,user_name)){
            contentValues.put(USER_TABLE_COL_1, user_email);
            contentValues.put(USER_TABLE_COL_2, user_name);
            Log.d(TAG, "addData: Adding " + user_name + " to " + USER_TABLE);

            long result = db.insert(USER_TABLE, null, contentValues);
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
                "select * from "+USER_TABLE +" where User_email = ? and User_name = ? ", new String[]{user_email, user_name},null);
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

    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + USER_TABLE;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public int getUserID(String user_email, String user_name){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select user_id from "+USER_TABLE +" where User_email = ? and User_name = ? ", new String[]{user_email, user_name},null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return Integer.parseInt(listData.get(0));
        else
            return 0;
    }

    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String quoery = "DELETE FROM " + USER_TABLE;
        Log.d(TAG,"Delete data: " + quoery);
        db.execSQL(quoery);
    }



//    INGREDIENT ACTIONS
    public  boolean addIngredient(String ingredient_name){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        // FUNCTIE DE CALCULAT CALORIEILE PER INGREDIENT
        int Ingredient_calories = 0;

        if(!checkIngredientExists(ingredient_name)){
            contentValues.put(INGREDIENT_TABLE_COL_1, ingredient_name);
            contentValues.put(INGREDIENT_TABLE_COL_2, Ingredient_calories);
            Log.d(TAG, "addData: Adding " + ingredient_name + " to " + INGREDIENT_TABLE);

            db.beginTransaction();
            try {
                db.insert(INGREDIENT_TABLE, null, contentValues);
                db.setTransactionSuccessful();

                return true;
            }
            finally {
                db.endTransaction();
            }

        }else{
            Log.d(TAG, "Ingredient already exists in database !");
            return false;
        }
    }

    public boolean checkIngredientExists(String ingredient_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor data;
        try {
            data = db.rawQuery("select * from " + INGREDIENT_TABLE + " where ingredient_name = ? ", new String[]{ingredient_name}, null);
            db.setTransactionSuccessful();

            ArrayList<String> listData = new ArrayList<>();
            while (data.moveToNext()) {
                listData.add(data.getString(1));
            }
            if (!listData.isEmpty()) {
                //daca exista un rezultat in lista => exista acel user
                return true;
            } else {
                //daca exista nu un rezultat in lista
                return false;
            }
        }
        finally {
            db.endTransaction();
        }
    }

    public ArrayList<String> getIngredients(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + INGREDIENT_TABLE;
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
        Cursor data;

        db.beginTransaction();
        try {
            data = db.rawQuery("select Ingredient_id from " + INGREDIENT_TABLE + " where ingredient_name = ?", new String[]{ingredient_name}, null);
            db.setTransactionSuccessful();

            ArrayList<String> listData = new ArrayList<>();
            while (data.moveToNext()) {
                listData.add(data.getString(0));
            }
            if (!listData.isEmpty())
                return Integer.parseInt(listData.get(0));
            else
                return 0;
        }
        finally {
            db.endTransaction();
        }
    }

    public void deleteIngredients(){
        SQLiteDatabase db = this.getWritableDatabase();
        String quoery = "DELETE FROM " + INGREDIENT_TABLE;
        Log.d(TAG,"Delete data: " + quoery);
        db.execSQL(quoery);
    }

    public String getIngredientById(String ingredientId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Ingredient_name from " + INGREDIENT_TABLE + " where Ingredient_id = ?", new String[]{ingredientId}, null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }



//    USER_INGREDIENTS ACTIONS
    public  boolean addUser_Ingredient(int User_id, int Ingredient_id, String Date, String Categorie){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_INGR_TABLE_COL_1, User_id);
        contentValues.put(USER_INGR_TABLE_COL_2, Ingredient_id);
        contentValues.put(USER_INGR_TABLE_COL_3, Date);
        contentValues.put(USER_INGR_TABLE_COL_4, 0);
        contentValues.put(USER_INGR_TABLE_COL_5, Categorie);
        Log.d(TAG, "addData: Adding complete USER-INGREDIENT " + Categorie + " "+ USER_INGR_TABLE + " " + Date +" " + Ingredient_id);

        long result = db.insert(USER_INGR_TABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<String> getCategoriesFromUser(String User_id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("select DISTINCT Categorie from "+USER_INGR_TABLE +" where User_id = ?", new String[]{User_id},null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData;
        else
            return null;
    }

    public ArrayList<String> GetUserIngIDByCategAndUser(int UserID, String Categorie){
        ArrayList<String> listData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+USER_INGR_TABLE_COL_0+" from "+USER_INGR_TABLE +" where User_id = ? and Categorie = ? ", new String[]{UserID+"", Categorie},null);

        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty()) {
            return listData;
        }
        else
            return null;
    }

    public String GetIngIdByUserIngId(int user_ing_id){
        String listData = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+USER_INGR_TABLE_COL_2+" from "+USER_INGR_TABLE +" where "+USER_INGR_TABLE_COL_0+ " = ? ", new String[]{user_ing_id+""},null);

        while(data.moveToNext()){
            listData = data.getString(0);
        }
        if(!listData.isEmpty()) {
            return listData;
        }
        else
            return null;
    }

    public String GetProdusDataExpiration(int User_Ingredient_Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Date from "+USER_INGR_TABLE +" where "+USER_INGR_TABLE_COL_0+" = ? ", new String[]{User_Ingredient_Id+""},null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }

    public boolean AddIngredientInShoopingList(int user_id, int ingredient_id){
        SQLiteDatabase db = this.getWritableDatabase();


        String quoery = "UPDATE " + USER_INGR_TABLE + " set " + USER_INGR_TABLE_COL_4 + " = 1 where " + USER_INGR_TABLE_COL_1 + " = " + user_id+ " and " + USER_INGR_TABLE_COL_2+" = " + ingredient_id;
        try{
            db.execSQL(quoery);
            Log.d(TAG,"Add Ingredient to shopping list: " + "COMPLETE !");
            return true;
        }
        catch (Exception e){
            Log.d(TAG,"Add Ingredient to shopping list: EROARE!");
            return  false;
        }
    }

    public  ArrayList<String> GetProductsIDThatAreInShoppingList(int user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Ingredient_id from "+USER_INGR_TABLE +" where User_id = ? and "+USER_INGR_TABLE_COL_4+" = 1", new String[]{user_id+""},null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData;
        else
            return null;
    }

    public String GetCategByUserIdIngrId(int user_id,int ingredient_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Categorie from "+USER_INGR_TABLE +" where User_id = ? and Ingredient_id = ?", new String[]{user_id+"",ingredient_id+""},null);

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        if(!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }

    public void DeleteIngredient(int user_id, int ingredient_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String quoery = "DELETE FROM " + USER_INGR_TABLE + " where User_id = " +user_id+ " and Ingredient_id = " +ingredient_id;
        Log.d(TAG,"Delete data: " + "COMPLETE !");
        db.execSQL(quoery);
    }
}
