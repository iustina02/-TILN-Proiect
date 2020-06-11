package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "AlphaMily.db";

    public static final String USER_TABLE = "User_table";
    public static final String USER_TABLE_COL_0 = "User_id";
    public static final String USER_TABLE_COL_1 = "User_email";
    public static final String USER_TABLE_COL_2 = "User_name";

    public static final String INGREDIENT_TABLE = "Ingredient_table";
    public static final String INGREDIENT_TABLE_COL_0 = "Ingredient_id";
    public static final String INGREDIENT_TABLE_COL_1 = "Ingredient_name";
    public static final String INGREDIENT_TABLE_COL_2 = "Ingredient_calories";

    public static final String CALORIES_TABLE = "Calories_table";
    public static final String CALORIES_TABLE_COL_0 = "ID";
    public static final String CALORIES_TABLE_COL_1 = "Calories_name";
    public static final String CALORIES_TABLE_COL_2 = "Calories_number";

    public static final String USER_INGR_TABLE = "User_Ingredient_table";
    public static final String USER_INGR_TABLE_COL_0 = "User_Ingredient_id";
    public static final String USER_INGR_TABLE_COL_1 = "User_id";
    public static final String USER_INGR_TABLE_COL_2 = "Ingredient_id";
    public static final String USER_INGR_TABLE_COL_3 = "Date";
    public static final String USER_INGR_TABLE_COL_4 = "InShoppingList";
    public static final String USER_INGR_TABLE_COL_5 = "Categorie";

    public static final String RECIPE_INGREDIENTS_TABLE = "Recipe_Ingredients_table";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_0 = "Recipe_Ingredients_id";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_1 = "Ingredient_1";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_2 = "Ingredient_2";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_3 = "Ingredient_3";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_4 = "Ingredient_4";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_5 = "Ingredient_5";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_6 = "Ingredient_6";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_7 = "Ingredient_7";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_8 = "Ingredient_8";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_9 = "Ingredient_9";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_10 = "Ingredient_10";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_11 = "Ingredient_11";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_12 = "Ingredient_12";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_13 = "Ingredient_13";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_14 = "Ingredient_14";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_15 = "Ingredient_15";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_16 = "Ingredient_16";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_17 = "Ingredient_17";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_18 = "Ingredient_18";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_19 = "Ingredient_19";
    public static final String RECIPE_INGREDIENTS_TABLE_COL_20 = "Ingredient_20";

    public static final String RECIPE_STEPS_TABLE = "Recipe_Steps_table";
    public static final String RECIPE_STEPS_TABLE_COL_0 = "Recipe_Steps_id";
    public static final String RECIPE_STEPS_TABLE_COL_1 = "Step_1";
    public static final String RECIPE_STEPS_TABLE_COL_2 = "Step_2";
    public static final String RECIPE_STEPS_TABLE_COL_3 = "Step_3";
    public static final String RECIPE_STEPS_TABLE_COL_4 = "Step_4";
    public static final String RECIPE_STEPS_TABLE_COL_5 = "Step_5";
    public static final String RECIPE_STEPS_TABLE_COL_6 = "Step_6";
    public static final String RECIPE_STEPS_TABLE_COL_7 = "Step_7";
    public static final String RECIPE_STEPS_TABLE_COL_8 = "Step_8";
    public static final String RECIPE_STEPS_TABLE_COL_9 = "Step_9";
    public static final String RECIPE_STEPS_TABLE_COL_10 = "Step_10";
    public static final String RECIPE_STEPS_TABLE_COL_11 = "Step_11";
    public static final String RECIPE_STEPS_TABLE_COL_12 = "Step_12";
    public static final String RECIPE_STEPS_TABLE_COL_13 = "Step_13";
    public static final String RECIPE_STEPS_TABLE_COL_14 = "Step_14";
    public static final String RECIPE_STEPS_TABLE_COL_15 = "Step_15";
    public static final String RECIPE_STEPS_TABLE_COL_16 = "Step_16";
    public static final String RECIPE_STEPS_TABLE_COL_17 = "Step_17";
    public static final String RECIPE_STEPS_TABLE_COL_18 = "Step_18";
    public static final String RECIPE_STEPS_TABLE_COL_19 = "Step_19";
    public static final String RECIPE_STEPS_TABLE_COL_20 = "Step_20";

    public static final String RECIPE_CATEG_TABLE = "Recipe_Categ_table";
    public static final String RECIPE_CATEG_TABLE_COL_0 = "Recipe_Categ_id";
    public static final String RECIPE_CATEG_TABLE_COL_1 = "Categ_1";
    public static final String RECIPE_CATEG_TABLE_COL_2 = "Categ_2";
    public static final String RECIPE_CATEG_TABLE_COL_3 = "Categ_3";
    public static final String RECIPE_CATEG_TABLE_COL_4 = "Categ_4";
    public static final String RECIPE_CATEG_TABLE_COL_5 = "Categ_5";
    public static final String RECIPE_CATEG_TABLE_COL_6 = "Categ_6";
    public static final String RECIPE_CATEG_TABLE_COL_7 = "Categ_7";

    public static final String RECIPE_TABLE = "Recipe_table";
    public static final String RECIPE_TABLE_COL_0 = "Recipe_id";
    public static final String RECIPE_TABLE_COL_1 = "Tip";
    public static final String RECIPE_TABLE_COL_2 = "Timp";
    public static final String RECIPE_TABLE_COL_3 = "Recipe_Ingredients_id";
    public static final String RECIPE_TABLE_COL_4 = "Recipe_Steps_id";
    public static final String RECIPE_TABLE_COL_5 = "Recipe_Categ_id";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " ( User_id INTEGER PRIMARY KEY AUTOINCREMENT, User_email TEXT, User_name TEXT)");

        db.execSQL("create table " + INGREDIENT_TABLE + " ( Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, Ingredient_name TEXT, Ingredient_calories INTEGER)");

        db.execSQL("create table " + CALORIES_TABLE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, Calories_name TEXT, Calories_number INTEGER)");

        db.execSQL("create table " + USER_INGR_TABLE + " (User_Ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, User_id INTEGER NOT NULL,  Ingredient_id INTEGER NOT NULL, Date DATETIME, InShoppingList INTEGER, Categorie TEXT, FOREIGN KEY (User_id) REFERENCES User_table (User_id), FOREIGN KEY (Ingredient_Id) REFERENCES Ingredient_table (Ingredient_Id) )");

        db.execSQL("create table " + RECIPE_INGREDIENTS_TABLE + " ( " + RECIPE_INGREDIENTS_TABLE_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_INGREDIENTS_TABLE_COL_1 + "  TEXT,  " + RECIPE_INGREDIENTS_TABLE_COL_2 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_3 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_4 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_5 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_6 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_7 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_8 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_9 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_10 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_11 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_12 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_13 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_14 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_15 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_16 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_17 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_18 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_19 + " TEXT, " + RECIPE_INGREDIENTS_TABLE_COL_20 + " TEXT)");

        db.execSQL("create table " + RECIPE_STEPS_TABLE + " ( " + RECIPE_STEPS_TABLE_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_STEPS_TABLE_COL_1 + "  TEXT,  " + RECIPE_STEPS_TABLE_COL_2 + " TEXT, " + RECIPE_STEPS_TABLE_COL_3 + " TEXT, " + RECIPE_STEPS_TABLE_COL_4 + " TEXT, " + RECIPE_STEPS_TABLE_COL_5 + " TEXT, " + RECIPE_STEPS_TABLE_COL_6 + " TEXT, " + RECIPE_STEPS_TABLE_COL_7 + " TEXT, " + RECIPE_STEPS_TABLE_COL_8 + " TEXT, " + RECIPE_STEPS_TABLE_COL_9 + " TEXT, " + RECIPE_STEPS_TABLE_COL_10 + " TEXT, " + RECIPE_STEPS_TABLE_COL_11 + " TEXT, " + RECIPE_STEPS_TABLE_COL_12 + " TEXT, " + RECIPE_STEPS_TABLE_COL_13 + " TEXT, " + RECIPE_STEPS_TABLE_COL_14 + " TEXT, " + RECIPE_STEPS_TABLE_COL_15 + " TEXT, " + RECIPE_STEPS_TABLE_COL_16 + " TEXT, " + RECIPE_STEPS_TABLE_COL_17 + " TEXT, " + RECIPE_STEPS_TABLE_COL_18 + " TEXT, " + RECIPE_STEPS_TABLE_COL_19 + " TEXT, " + RECIPE_STEPS_TABLE_COL_20 + " TEXT)");

        db.execSQL("create table " + RECIPE_CATEG_TABLE + " ( " + RECIPE_CATEG_TABLE_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_CATEG_TABLE_COL_1 + "  TEXT,  " + RECIPE_CATEG_TABLE_COL_2 + " TEXT, " + RECIPE_CATEG_TABLE_COL_3 + " TEXT, " + RECIPE_CATEG_TABLE_COL_4 + " TEXT, " + RECIPE_CATEG_TABLE_COL_5 + " TEXT, " + RECIPE_CATEG_TABLE_COL_6 + " TEXT, " + RECIPE_CATEG_TABLE_COL_7 + " TEXT)");

        db.execSQL("create table " + RECIPE_TABLE + " (" + RECIPE_TABLE_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_TABLE_COL_1 + " TEXT,  " + RECIPE_TABLE_COL_2 + " TEXT, " + RECIPE_TABLE_COL_3 + " INTEGER, " + RECIPE_TABLE_COL_4 + " INTEGER, " + RECIPE_TABLE_COL_5 + " INTEGER, FOREIGN KEY (" + RECIPE_TABLE_COL_3 + ") REFERENCES " + RECIPE_INGREDIENTS_TABLE + " (Recipe_Ingredients_id), FOREIGN KEY (Recipe_Steps_id) REFERENCES " + RECIPE_STEPS_TABLE + " (Recipe_Steps_id),FOREIGN KEY (Recipe_Categ_id) REFERENCES " + RECIPE_CATEG_TABLE + " (Recipe_Categ_id) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CALORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_INGR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_INGREDIENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_STEPS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_CATEG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE);

        onCreate(db);
    }


    //    USER ACTIONS
    public boolean addUser(String user_email, String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (!checkUserExists(user_email, user_name)) {
            populate_calories();
            populate_recipes();
            contentValues.put(USER_TABLE_COL_1, user_email);
            contentValues.put(USER_TABLE_COL_2, user_name);
            Log.d(TAG, "addData: Adding " + user_name + " to " + USER_TABLE);

            long result = db.insert(USER_TABLE, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        } else {
            Log.d(TAG, "User already exists in database !");
            return false;
        }
    }

    public boolean checkUserExists(String user_email, String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(
                "select * from " + USER_TABLE + " where User_email = ? and User_name = ? ", new String[]{user_email, user_name}, null);
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

    public Cursor getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + USER_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getUserID(String user_email, String user_name) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select user_id from " + USER_TABLE + " where User_email = ? and User_name = ? ", new String[]{user_email, user_name}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return Integer.parseInt(listData.get(0));
        else
            return 0;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String quoery = "DELETE FROM " + USER_TABLE;
        Log.d(TAG, "Delete data: " + quoery);
        db.execSQL(quoery);
    }


    //    INGREDIENT ACTIONS
    public boolean addIngredient(String ingredient_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        // FUNCTIE DE CALCULAT CALORIEILE PER INGREDIENT
        int Ingredient_calories = GetIngCaloriesByName(ingredient_name);

        if (!checkIngredientExists(ingredient_name)) {
            contentValues.put(INGREDIENT_TABLE_COL_1, ingredient_name);
            contentValues.put(INGREDIENT_TABLE_COL_2, Ingredient_calories);
            Log.d(TAG, "addData: Adding " + ingredient_name + " to " + INGREDIENT_TABLE);

            db.beginTransaction();
            try {
                db.insert(INGREDIENT_TABLE, null, contentValues);
                db.setTransactionSuccessful();

                return true;
            } finally {
                db.endTransaction();
            }

        } else {
            Log.d(TAG, "Ingredient already exists in database !");
            return false;
        }
    }

    public boolean checkIngredientExists(String ingredient_name) {
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
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<String> getIngredients() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + INGREDIENT_TABLE;
        Cursor data = db.rawQuery(query, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        if (!listData.isEmpty())
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
        } finally {
            db.endTransaction();
        }
    }

    public void deleteIngredients() {
        SQLiteDatabase db = this.getWritableDatabase();
        String quoery = "DELETE FROM " + INGREDIENT_TABLE;
        Log.d(TAG, "Delete data: " + quoery);
        db.execSQL(quoery);
    }

    public String getIngredientById(String ingredientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Ingredient_name from " + INGREDIENT_TABLE + " where Ingredient_id = ?", new String[]{ingredientId}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }

    // CALORIES ACTIONS
    public int addCalories(String name, int number) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CALORIES_TABLE_COL_1, name);
        contentValues.put(CALORIES_TABLE_COL_2, number);

        long result = db.insert(CALORIES_TABLE, null, contentValues);
//        if (result == -1)
//            return false;
//        else
//            return true;
        return (int) result;
    }

    public void populate_calories() {
        int ind = addCalories("GRANIA FAINOOO 1KG", 358);
        Log.d("Calories id", "Calories id: " + ind);
    }

    public int GetIngCaloriesByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select " + CALORIES_TABLE_COL_2 + " from " + CALORIES_TABLE + " where " + CALORIES_TABLE_COL_1 + " = ? ", new String[]{name + ""}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return Integer.parseInt(listData.get(0));
        else
            return 0;
    }

    public String GetIngCaloriesByID(int ing_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select " + INGREDIENT_TABLE_COL_2 + " from " + INGREDIENT_TABLE + " where " + INGREDIENT_TABLE_COL_0 + " = ? ", new String[]{ing_id + ""}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData.get(0);
        else
            return "0";
    }


    //    USER_INGREDIENTS ACTIONS
    public boolean addUser_Ingredient(int User_id, int Ingredient_id, String Date, String Categorie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_INGR_TABLE_COL_1, User_id);
        contentValues.put(USER_INGR_TABLE_COL_2, Ingredient_id);
        contentValues.put(USER_INGR_TABLE_COL_3, Date);
        contentValues.put(USER_INGR_TABLE_COL_4, 0);
        contentValues.put(USER_INGR_TABLE_COL_5, Categorie);
        Log.d(TAG, "addData: Adding complete USER-INGREDIENT " + Categorie + " " + USER_INGR_TABLE + " " + Date + " " + Ingredient_id);

        long result = db.insert(USER_INGR_TABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<String> getCategoriesFromUser(String User_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("select DISTINCT Categorie from " + USER_INGR_TABLE + " where User_id = ?", new String[]{User_id}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData;
        else
            return null;
    }

    public ArrayList<String> GetUserIngIDByCategAndUser(int UserID, String Categorie) {
        ArrayList<String> listData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select " + USER_INGR_TABLE_COL_0 + " from " + USER_INGR_TABLE + " where User_id = ? and Categorie = ? ORDER BY " + USER_INGR_TABLE_COL_3, new String[]{UserID + "", Categorie}, null);

        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty()) {
            return listData;
        } else
            return null;
    }

    public String GetIngIdByUserIngId(int user_ing_id) {
        String listData = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select " + USER_INGR_TABLE_COL_2 + " from " + USER_INGR_TABLE + " where " + USER_INGR_TABLE_COL_0 + " = ? ", new String[]{user_ing_id + ""}, null);

        while (data.moveToNext()) {
            listData = data.getString(0);
        }
        if (!listData.isEmpty()) {
            return listData;
        } else
            return null;
    }

    public String GetProdusDataExpiration(int User_Ingredient_Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Date from " + USER_INGR_TABLE + " where " + USER_INGR_TABLE_COL_0 + " = ? ", new String[]{User_Ingredient_Id + ""}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }

    public boolean AddIngredientInShoopingList(int user_ing_id) {
        SQLiteDatabase db = this.getWritableDatabase();


        String quoery = "UPDATE " + USER_INGR_TABLE + " set " + USER_INGR_TABLE_COL_4 + " = 1 where " + USER_INGR_TABLE_COL_0 + " = " + user_ing_id;
        try {
            db.execSQL(quoery);
            Log.d(TAG, "Add Ingredient to shopping list: " + "COMPLETE !");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Add Ingredient to shopping list: EROARE!");
            return false;
        }
    }

    public boolean DeleteIngredientFromShoopingList(int user_id, int ingredient_id) {
        SQLiteDatabase db = this.getWritableDatabase();


        String quoery = "UPDATE " + USER_INGR_TABLE + " set " + USER_INGR_TABLE_COL_4 + " = 0 where " + USER_INGR_TABLE_COL_1 + " = " + user_id + " and " + USER_INGR_TABLE_COL_2 + " = " + ingredient_id;
        try {
            db.execSQL(quoery);
            Log.d(TAG, "Delete Ingredient from shopping list: " + "COMPLETE !");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Delete Ingredient to shopping list: EROARE!");
            return false;
        }
    }

    public ArrayList<String> GetProductsIDThatAreInShoppingList(int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select DISTINCT Ingredient_id from " + USER_INGR_TABLE + " where User_id = ? and " + USER_INGR_TABLE_COL_4 + " = 1", new String[]{user_id + ""}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData;
        else
            return null;
    }

    public String GetCategByUserIdIngrId(int user_id, int ingredient_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Categorie from " + USER_INGR_TABLE + " where User_id = ? and Ingredient_id = ?", new String[]{user_id + "", ingredient_id + ""}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData.get(0);
        else
            return null;
    }

    public void DeleteIngredientByUserIngID(int user_ing_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String quoery = "DELETE FROM " + USER_INGR_TABLE + " where " + USER_INGR_TABLE_COL_0 + "=" + user_ing_id;
        Log.d(TAG, "Delete data: " + "COMPLETE !");
        db.execSQL(quoery);
    }

    public void DeleteIngredient(int user_id, int ingredient_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String quoery = "DELETE FROM " + USER_INGR_TABLE + " where User_id = " + user_id + " and Ingredient_id = " + ingredient_id;
        Log.d(TAG, "Delete data: " + "COMPLETE !");
        db.execSQL(quoery);
    }



    // RECIPES INGREDIENTS ACTIONS
    public int addRecipeIngredient(String Ingredient_1, String Ingredient_2, String Ingredient_3, String Ingredient_4, String Ingredient_5, String Ingredient_6, String Ingredient_7, String Ingredient_8, String Ingredient_9, String Ingredient_10, String Ingredient_11, String Ingredient_12, String Ingredient_13, String Ingredient_14, String Ingredient_15, String Ingredient_16, String Ingredient_17, String Ingredient_18, String Ingredient_19, String Ingredient_20) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_1, Ingredient_1);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_2, Ingredient_2);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_3, Ingredient_3);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_4, Ingredient_4);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_5, Ingredient_5);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_6, Ingredient_6);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_7, Ingredient_7);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_8, Ingredient_8);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_9, Ingredient_9);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_10, Ingredient_10);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_11, Ingredient_11);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_12, Ingredient_12);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_13, Ingredient_13);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_14, Ingredient_14);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_15, Ingredient_15);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_16, Ingredient_16);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_17, Ingredient_17);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_18, Ingredient_18);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_19, Ingredient_19);
        contentValues.put(RECIPE_INGREDIENTS_TABLE_COL_20, Ingredient_20);


        long result = db.insert(RECIPE_INGREDIENTS_TABLE, null, contentValues);
        return (int) result;

    }


    // RECIPES STEPS ACTIONS
    public int addRecipeSteps(String Step_1, String Step_2, String Step_3, String Step_4, String Step_5, String Step_6, String Step_7, String Step_8, String Step_9, String Step_10, String Step_11, String Step_12, String Step_13, String Step_14, String Step_15, String Step_16, String Step_17, String Step_18, String Step_19, String Step_20) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_STEPS_TABLE_COL_1, Step_1);
        contentValues.put(RECIPE_STEPS_TABLE_COL_2, Step_2);
        contentValues.put(RECIPE_STEPS_TABLE_COL_3, Step_3);
        contentValues.put(RECIPE_STEPS_TABLE_COL_4, Step_4);
        contentValues.put(RECIPE_STEPS_TABLE_COL_5, Step_5);
        contentValues.put(RECIPE_STEPS_TABLE_COL_6, Step_6);
        contentValues.put(RECIPE_STEPS_TABLE_COL_7, Step_7);
        contentValues.put(RECIPE_STEPS_TABLE_COL_8, Step_8);
        contentValues.put(RECIPE_STEPS_TABLE_COL_9, Step_9);
        contentValues.put(RECIPE_STEPS_TABLE_COL_10, Step_10);
        contentValues.put(RECIPE_STEPS_TABLE_COL_11, Step_11);
        contentValues.put(RECIPE_STEPS_TABLE_COL_12, Step_12);
        contentValues.put(RECIPE_STEPS_TABLE_COL_13, Step_13);
        contentValues.put(RECIPE_STEPS_TABLE_COL_14, Step_14);
        contentValues.put(RECIPE_STEPS_TABLE_COL_15, Step_15);
        contentValues.put(RECIPE_STEPS_TABLE_COL_16, Step_16);
        contentValues.put(RECIPE_STEPS_TABLE_COL_17, Step_17);
        contentValues.put(RECIPE_STEPS_TABLE_COL_18, Step_18);
        contentValues.put(RECIPE_STEPS_TABLE_COL_19, Step_19);
        contentValues.put(RECIPE_STEPS_TABLE_COL_20, Step_20);


        long result = db.insert(RECIPE_STEPS_TABLE, null, contentValues);
        return (int) result;
    }



    // RECIPES CATEG ACTIONS
    public int addRecipeCateg(String Categ_1, String Categ_2, String Categ_3, String Categ_4, String Categ_5, String Categ_6, String Categ_7) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_CATEG_TABLE_COL_1, Categ_1);
        contentValues.put(RECIPE_CATEG_TABLE_COL_2, Categ_2);
        contentValues.put(RECIPE_CATEG_TABLE_COL_3, Categ_3);
        contentValues.put(RECIPE_CATEG_TABLE_COL_4, Categ_4);
        contentValues.put(RECIPE_CATEG_TABLE_COL_5, Categ_5);
        contentValues.put(RECIPE_CATEG_TABLE_COL_6, Categ_6);
        contentValues.put(RECIPE_CATEG_TABLE_COL_7, Categ_7);


        long result = db.insert(RECIPE_CATEG_TABLE, null, contentValues);
        return (int) result;
    }



    // RECIPES ACTIONS
    public int addRecipe(String Tip, String Timp, int Recipe_Ingredients_id, int Recipe_Steps_id, int Recipe_Categ_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_TABLE_COL_1, Tip);
        contentValues.put(RECIPE_TABLE_COL_2, Timp);
        contentValues.put(RECIPE_TABLE_COL_3, Recipe_Ingredients_id);
        contentValues.put(RECIPE_TABLE_COL_4, Recipe_Steps_id);
        contentValues.put(RECIPE_TABLE_COL_5, Recipe_Categ_id);

        long result = db.insert(RECIPE_TABLE, null, contentValues);
        return (int) result;
    }

    public void populate_recipes(){
        int recipe_ing_id = addRecipeIngredient("Avocado x2","Ceapa x1","Lamaie / Lime x1 lingura","Ardei iute x2","Coriandru","Rosie x1/2","Sare x1/2 lingurita","Piper","","","","","","","","","","","","");
        int recipe_steps_id = addRecipeSteps("Curata fructele de avocado de coaja, scoate samburele si zdrobeste-le intr-un bol cu ajutorul unei furculite.","Adauga rosia, sarea si sucul de lamaie.","Toaca ceapa marunt si adaug-o in pasta impreuna cu piperul, coriandrul si ardeiul iute.","Amesteca bine toate ingredientele pana se omogenizeaza.","Acopera bolul de guacamole cu o folie de plastic si tine-l la frigider","Este indicat sa il consumi proaspat, deoarece pulpa fructului se oxideaza foarte usor.","","","","","","","","","","","","","","");
        int recipe_categ_id = addRecipeCateg("fruct","condimente","legume","","","","");

        int recipe_id = addRecipe("gustare","20 min",recipe_ing_id,recipe_steps_id,recipe_categ_id);
        Log.d("DB","Reteta id: " + recipe_id);
    }

}