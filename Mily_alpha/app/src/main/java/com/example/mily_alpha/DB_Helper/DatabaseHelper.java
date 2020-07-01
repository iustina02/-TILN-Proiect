package com.example.mily_alpha.DB_Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance = null;

    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 14;
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
    public static final String RECIPE_TABLE_COL_6 = "Img_number";
    public static final String RECIPE_TABLE_COL_7 = "Name";


    private DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
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

        db.execSQL("create table " + RECIPE_TABLE + " (" + RECIPE_TABLE_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_TABLE_COL_1 + " TEXT,  " + RECIPE_TABLE_COL_2 + " TEXT, " + RECIPE_TABLE_COL_3 + " INTEGER, " + RECIPE_TABLE_COL_4 + " INTEGER, " + RECIPE_TABLE_COL_5 + " INTEGER, " + RECIPE_TABLE_COL_6 + " INTEGER, "+RECIPE_TABLE_COL_7 +" TEXT, FOREIGN KEY (" + RECIPE_TABLE_COL_3 + ") REFERENCES " + RECIPE_INGREDIENTS_TABLE + " (Recipe_Ingredients_id), FOREIGN KEY (Recipe_Steps_id) REFERENCES " + RECIPE_STEPS_TABLE + " (Recipe_Steps_id),FOREIGN KEY (Recipe_Categ_id) REFERENCES " + RECIPE_CATEG_TABLE + " (Recipe_Categ_id) )");

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

    public static DatabaseHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }



    //    USER ACTIONS
    public boolean addUser(String user_email, String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (!checkUserExists(user_email, user_name)) {
            contentValues.put(USER_TABLE_COL_1, user_email);
            contentValues.put(USER_TABLE_COL_2, user_name);
            Log.d(TAG, "addData: Adding " + user_name + " to " + USER_TABLE);
            populate_calories();
            populate_recipes();

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
        SQLiteDatabase db = mInstance.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        // FUNCTIE DE CALCULAT CALORIEILE PER INGREDIENT
        int Ingredient_calories = GetIngCaloriesByName(ingredient_name);

        if (!checkIngredientExists(ingredient_name)) {
            contentValues.put(INGREDIENT_TABLE_COL_1, ingredient_name);
            contentValues.put(INGREDIENT_TABLE_COL_2, Ingredient_calories);
            Log.d(TAG, "addData: Adding " + ingredient_name + " to " + INGREDIENT_TABLE);

            db.insert(INGREDIENT_TABLE, null, contentValues);


            return true;

        } else {
            Log.d(TAG, "Ingredient already exists in database !");
            return false;
        }
    }

    public boolean checkIngredientExists(String ingredient_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data;
        data = db.rawQuery("select * from " + INGREDIENT_TABLE + " where ingredient_name = ? ", new String[]{ingredient_name}, null);

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


        data = db.rawQuery("select Ingredient_id from " + INGREDIENT_TABLE + " where ingredient_name = ?", new String[]{ingredient_name}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return Integer.parseInt(listData.get(0));
        else
            return 0;
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
        addCalories("GRANIA FAIN000 1KG", 358);
        addCalories("GRANIA FAINO0O 1KG", 358);
        addCalories("GRANIA FAIN00O 1KG", 358);
        addCalories("GRANIA FAINOO0 1KG", 358);
        addCalories(" GRANIA FAINOOO 1KG", 358);
        addCalories("ZUZU TAURT 0.1%", 54);
        addCalories("ZUZU IAURT 0.1%", 54);
        addCalories("2UZU IAURT 0.1%", 54);
        addCalories("2UZU TAURT 0.1%", 54);
        addCalories("LADORNA UNT65%200G", 746);
        addCalories("PAMBAC MELCI 400G", 350);
        addCalories("FOX SUNCA PRAGA", 105);
        addCalories("K-PUR TOCATA 1KG", 316);
        addCalories("ARIPI PUI TAVA", 205);
        addCalories("ZUZU LAPTE 3.5% 1L", 62);
        addCalories("BOR FAINA 000 1KG", 346);
        addCalories("LAPT CU CAIM LAPTE", 70);
        addCalories("USTUROI", 40);
        addCalories("ARDEI ROSU", 31);
        addCalories("CHIO CHIPS100G", 538);
        addCalories("LAY S CHIPS 100G", 490);
        addCalories("LAY S BARBECUE 70 G", 490);
        addCalories("MUESLI FRUCTE", 370);
        addCalories("BNS SPAGHETTI 600G", 335);


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

    public ArrayList<String> GetAllDataExpirationFromUser(int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select Date from " + USER_INGR_TABLE + " where " + USER_INGR_TABLE_COL_1 + " = ? ", new String[]{user_id + ""}, null);

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        if (!listData.isEmpty())
            return listData;
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
    public ArrayList<String> searchRecipesAfterIngredient_returnListIdRetete(String tip, ArrayList<String> ingredients){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ids_retete_1;
        if(tip.equals("Selecteaza:")){
            ids_retete_1 = db.rawQuery("select " + RECIPE_TABLE_COL_0 +", "+ RECIPE_TABLE_COL_3  + " from " + RECIPE_TABLE , new String[]{}, null);
        }
        else {

            String type = tip.toLowerCase().replace(" ", "");

            ids_retete_1 = db.rawQuery("select " + RECIPE_TABLE_COL_0 + ", " + RECIPE_TABLE_COL_3 + " from " + RECIPE_TABLE + " where " + RECIPE_TABLE_COL_1 + " = ? ", new String[]{type}, null);
        }

        ArrayList<String> ids_retete_return = new ArrayList<>();

        ArrayList<String> ids_retete_cu_acel_tip = new ArrayList<>();
        ArrayList<String> ids_retete_ingrediente_cu_acel_tip = new ArrayList<>();

        while (ids_retete_1.moveToNext()) {
            ids_retete_cu_acel_tip.add(ids_retete_1.getString(0));
            ids_retete_ingrediente_cu_acel_tip.add(ids_retete_1.getString(1));
        }

        int count = 0;
        for(String id_reteta_ingrediente: ids_retete_ingrediente_cu_acel_tip){
            ArrayList<String> ingrediente_from_recipe = getAllIngredientsFromRecipe(id_reteta_ingrediente);

            for(String ing: ingrediente_from_recipe){
                if(ingredients.contains(ing) && !ids_retete_return.contains(ids_retete_cu_acel_tip.get(count))){
                    ids_retete_return.add(ids_retete_cu_acel_tip.get(count));
                }
            }
             count++;

        }

        return ids_retete_return;
    }

    public ArrayList<String> searchRecipesAfterCategory_returnListIdRetete(String tip, ArrayList<String> categories){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ids_retete_1;


        if(tip.equals("Selecteaza:")){
            ids_retete_1 = db.rawQuery("select " + RECIPE_TABLE_COL_0 +", "+ RECIPE_TABLE_COL_3  + " from " + RECIPE_TABLE, new String[]{}, null);
        }
        else {

            String type = tip.toLowerCase().replace(" ", "");

            ids_retete_1 = db.rawQuery("select " + RECIPE_TABLE_COL_0 +", "+ RECIPE_TABLE_COL_3  + " from " + RECIPE_TABLE + " where " + RECIPE_TABLE_COL_1 + " = ? ", new String[]{type}, null);
        }

        ArrayList<String> ids_retete_return = new ArrayList<>();

        ArrayList<String> ids_retete_cu_acel_tip = new ArrayList<>();
        ArrayList<String> ids_retete_category_cu_acel_tip = new ArrayList<>();

        while (ids_retete_1.moveToNext()) {
            ids_retete_cu_acel_tip.add(ids_retete_1.getString(0));
            ids_retete_category_cu_acel_tip.add(ids_retete_1.getString(1));
        }

        int count = 0;
        for(String id_reteta_category: ids_retete_category_cu_acel_tip){
            ArrayList<String> category_from_recipe = getAllCategoryFromRecipe(id_reteta_category);

            for(String ing: category_from_recipe){
                if(categories.contains(ing) && !ids_retete_return.contains(ids_retete_cu_acel_tip.get(count))){
                    ids_retete_return.add(ids_retete_cu_acel_tip.get(count));
                }
            }
            count++;

        }

        return ids_retete_return;
    }

    public ArrayList<String> getAllCategoryFromRecipe(String recipe_ing_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + RECIPE_CATEG_TABLE +" where " + RECIPE_CATEG_TABLE_COL_0 +"= ? ",new String[]{recipe_ing_id});

        ArrayList<String> allCategories = new ArrayList<>();
        ArrayList<String> distinctCategories = new ArrayList<>();

        while(data.moveToNext()){
            allCategories.add(data.getString(1));
            allCategories.add(data.getString(2));
            allCategories.add(data.getString(3));
            allCategories.add(data.getString(4));
            allCategories.add(data.getString(5));
            allCategories.add(data.getString(6));
            allCategories.add(data.getString(7));

        }

        for(String category: allCategories){
            if(!category.equals("")){
                distinctCategories.add(category);
            }
        }
        return distinctCategories;
    }

    public ArrayList<String> getAllIngredientsFromRecipe(String recipe_ing_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + RECIPE_INGREDIENTS_TABLE +" where " + RECIPE_INGREDIENTS_TABLE_COL_0 +"= ? ",new String[]{recipe_ing_id});

        ArrayList<String> allIngredients = new ArrayList<>();
        ArrayList<String> distinctIngredients = new ArrayList<>();

        while(data.moveToNext()){
            allIngredients.add(data.getString(1));
            allIngredients.add(data.getString(2));
            allIngredients.add(data.getString(3));
            allIngredients.add(data.getString(4));
            allIngredients.add(data.getString(5));
            allIngredients.add(data.getString(6));
            allIngredients.add(data.getString(7));
            allIngredients.add(data.getString(8));
            allIngredients.add(data.getString(9));
            allIngredients.add(data.getString(10));
            allIngredients.add(data.getString(11));
            allIngredients.add(data.getString(12));
            allIngredients.add(data.getString(13));
            allIngredients.add(data.getString(14));
            allIngredients.add(data.getString(15));
            allIngredients.add(data.getString(16));
            allIngredients.add(data.getString(17));
            allIngredients.add(data.getString(18));
            allIngredients.add(data.getString(19));
            allIngredients.add(data.getString(20));
        }

        for(String ing: allIngredients){
            String[] words = ing.split(" ");
            ArrayList<String> ingrediente_final = new ArrayList<>();

            for(String word: words){
                if(!hasNumbers(word) && !word.equals("/") && !word.equals("lingura") && !word.equals("lingurita") && !word.equals("")){
                    ingrediente_final.add(word);
                }
            }

            for(String ingre_final: ingrediente_final) {
                if (!distinctIngredients.contains(ingre_final)) {
                    distinctIngredients.add(ingre_final);
                }
            }
        }
        return distinctIngredients;
    }

    public ArrayList<String> getAllIngredientsFullFromRecipe(String id_recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+RECIPE_TABLE_COL_3+" from " + RECIPE_TABLE +" where " + RECIPE_TABLE_COL_0 +"= ? ",new String[]{id_recipe});

        String id_recipe_ing= "";
        while(data.moveToNext()){
            id_recipe_ing = data.getString(0);
        }

        data = db.rawQuery("select * from " + RECIPE_INGREDIENTS_TABLE +" where " + RECIPE_INGREDIENTS_TABLE_COL_0 +"= ? ",new String[]{id_recipe_ing});

        ArrayList<String> allIngredients = new ArrayList<>();

        while(data.moveToNext()){
            allIngredients.add(data.getString(1));
            allIngredients.add(data.getString(2));
            allIngredients.add(data.getString(3));
            allIngredients.add(data.getString(4));
            allIngredients.add(data.getString(5));
            allIngredients.add(data.getString(6));
            allIngredients.add(data.getString(7));
            allIngredients.add(data.getString(8));
            allIngredients.add(data.getString(9));
            allIngredients.add(data.getString(10));
            allIngredients.add(data.getString(11));
            allIngredients.add(data.getString(12));
            allIngredients.add(data.getString(13));
            allIngredients.add(data.getString(14));
            allIngredients.add(data.getString(15));
            allIngredients.add(data.getString(16));
            allIngredients.add(data.getString(17));
            allIngredients.add(data.getString(18));
            allIngredients.add(data.getString(19));
            allIngredients.add(data.getString(20));
        }

        return allIngredients;
    }

    public ArrayList<String> getAllStepsFromRecipe(String id_recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+RECIPE_TABLE_COL_4+" from " + RECIPE_TABLE +" where " + RECIPE_TABLE_COL_0 +"= ? ",new String[]{id_recipe});

        String id_recipe_steps= "";
        while(data.moveToNext()){
            id_recipe_steps = data.getString(0);
        }

        data = db.rawQuery("select * from " + RECIPE_STEPS_TABLE +" where " + RECIPE_STEPS_TABLE_COL_0 +"= ? ",new String[]{id_recipe_steps});

        ArrayList<String> allSteps = new ArrayList<>();

        while(data.moveToNext()){
            allSteps.add(data.getString(1));
            allSteps.add(data.getString(2));
            allSteps.add(data.getString(3));
            allSteps.add(data.getString(4));
            allSteps.add(data.getString(5));
            allSteps.add(data.getString(6));
            allSteps.add(data.getString(7));
            allSteps.add(data.getString(8));
            allSteps.add(data.getString(9));
            allSteps.add(data.getString(10));
            allSteps.add(data.getString(11));
            allSteps.add(data.getString(12));
            allSteps.add(data.getString(13));
            allSteps.add(data.getString(14));
            allSteps.add(data.getString(15));
            allSteps.add(data.getString(16));
            allSteps.add(data.getString(17));
            allSteps.add(data.getString(18));
            allSteps.add(data.getString(19));
            allSteps.add(data.getString(20));
        }

        return allSteps;

    }

    public ArrayList<String> getAllIngredientsFromRecipes (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + RECIPE_INGREDIENTS_TABLE,new String[]{});

        ArrayList<String> allIngredients = new ArrayList<>();
        ArrayList<String> distinctIngredients = new ArrayList<>();

        while(data.moveToNext()){
            allIngredients.add(data.getString(1));
            allIngredients.add(data.getString(2));
            allIngredients.add(data.getString(3));
            allIngredients.add(data.getString(4));
            allIngredients.add(data.getString(5));
            allIngredients.add(data.getString(6));
            allIngredients.add(data.getString(7));
            allIngredients.add(data.getString(8));
            allIngredients.add(data.getString(9));
            allIngredients.add(data.getString(10));
            allIngredients.add(data.getString(11));
            allIngredients.add(data.getString(12));
            allIngredients.add(data.getString(13));
            allIngredients.add(data.getString(14));
            allIngredients.add(data.getString(15));
            allIngredients.add(data.getString(16));
            allIngredients.add(data.getString(17));
            allIngredients.add(data.getString(18));
            allIngredients.add(data.getString(19));
            allIngredients.add(data.getString(20));
        }

        for(String ing: allIngredients){
            String[] words = ing.split(" ");
            ArrayList<String> ingrediente_final = new ArrayList<>();

            for(String word: words){
                if(!hasNumbers(word) && !word.equals("/") && !word.equals("lingura") && !word.equals("lingurita") && !word.equals("")){
                    ingrediente_final.add(word);
                }
            }

            for(String ingre_final: ingrediente_final) {
                if (!distinctIngredients.contains(ingre_final)) {
                    distinctIngredients.add(ingre_final);
                }
            }
        }
        return distinctIngredients;

    }

    public ArrayList<String> getNamesFromRecipes(ArrayList<String> ids_recipes){
        ArrayList<String> Names = new ArrayList<>();

        for(String id_recipe: ids_recipes){
            Names.add(getNameFromRecipe(id_recipe));
        }

        return Names;

    }

    public String getNameFromRecipe(String id_recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+RECIPE_TABLE_COL_7+" from " + RECIPE_TABLE + " where "+RECIPE_TABLE_COL_0+" = ?", new String[]{id_recipe});

        String name = null;
        while(data.moveToNext()) {
            name = data.getString(0);
        }
        return name;
    }

    public ArrayList<String> getTimeFromRecipes(ArrayList<String> id_recipes){
        ArrayList<String> Times = new ArrayList<>();

        for(String id_recipe: id_recipes){
            Times.add(getTimeFromRecipe(id_recipe));
        }

        return Times;
    }

    public String getTimeFromRecipe(String id_recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+RECIPE_TABLE_COL_2+" from " + RECIPE_TABLE + " where "+RECIPE_TABLE_COL_0+" = ?", new String[]{id_recipe});

        String name = null;
        while(data.moveToNext()) {
            name = data.getString(0);
        }
        return name;
    }

    public ArrayList<Integer> getImgNumberFromRecipes(ArrayList<String> id_recipes){
        ArrayList<Integer> Img_number = new ArrayList<>();

        for(String id_recipe: id_recipes){
            Img_number.add(getImgNumberFromRecipe(id_recipe));
        }

        return Img_number;
    }

    public Integer getImgNumberFromRecipe(String id_recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+RECIPE_TABLE_COL_6+" from " + RECIPE_TABLE + " where "+RECIPE_TABLE_COL_0+" = ?", new String[]{id_recipe});

        Integer name = null;
        while(data.moveToNext()) {
            name = Integer.parseInt(data.getString(0));
        }
        return name;
    }


    public int addRecipe(String Tip, String Timp, int Recipe_Ingredients_id, int Recipe_Steps_id, int Recipe_Categ_id, int Img_Number, String Name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_TABLE_COL_1, Tip);
        contentValues.put(RECIPE_TABLE_COL_2, Timp);
        contentValues.put(RECIPE_TABLE_COL_3, Recipe_Ingredients_id);
        contentValues.put(RECIPE_TABLE_COL_4, Recipe_Steps_id);
        contentValues.put(RECIPE_TABLE_COL_5, Recipe_Categ_id);
        contentValues.put(RECIPE_TABLE_COL_6, Img_Number);
        contentValues.put(RECIPE_TABLE_COL_7, Name);

        long result = db.insert(RECIPE_TABLE, null, contentValues);
        return (int) result;
    }

    public void populate_recipes(){
        int recipe_ing_id = addRecipeIngredient("Avocado x2","Ceapa x1","Lamaie / Lime x1 lingura","Ardei-iute x2","Coriandru","Rosie x1/2","Sare x1/2 lingurita","Piper","","","","","","","","","","","","");
        int recipe_steps_id = addRecipeSteps("Curata fructele de avocado de coaja, scoate samburele si zdrobeste-le intr-un bol cu ajutorul unei furculite.","Adauga rosia, sarea si sucul de lamaie.","Toaca ceapa marunt si adaug-o in pasta impreuna cu piperul, coriandrul si ardeiul iute.","Amesteca bine toate ingredientele pana se omogenizeaza.","Acopera bolul de guacamole cu o folie de plastic si tine-l la frigider","Este indicat sa il consumi proaspat, deoarece pulpa fructului se oxideaza foarte usor.","","","","","","","","","","","","","","");
        int recipe_categ_id = addRecipeCateg("fruct","condiment","leguma","","","","");

        int recipe_id = addRecipe("gustare","20 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,1,"Guacamole");
        Log.d("DB","Reteta id: " + recipe_id);



        recipe_ing_id = addRecipeIngredient("Faina 250g","Lapte 550ml","Oua x3","Ulei / Unt 40ml","Sare","Vanilie","","","","","","","","","","","","","","");
        recipe_steps_id = addRecipeSteps("Punem ouale intr-un vas, adaugam sarea si amestecam pana se omogenizeaza. Cernem faina si o adaugam treptat in vas, cate 2-3 linguri deodata.","Amestecam laptele la temperatura camerei cu uleiul.","Cand aluatul incepe sa capete consistenta, adaugam cate 50-60 ml din mixul de lapte si ulei. Amestecam pana cand aluatul devine din nou fluid.","Continuam, adaugand alternativ faina si lapte, si amestecam bine dupa fiecare portie adaugata aluatului.","In compozitia finala, incorporam semintele unei pastai de vanilie, apoi lasam aluatul 20 -30 de minute pe masa din bucatarie.","EPunem un polonic de aluat de in tigaia incinsa. Coacem clatitele, in tigaia incinsa si unsa de fiecare data cand adaugam aluat. Timpul de coacere este de cate 1-2 minute","","","","","","","","","","","","","","");
        recipe_categ_id = addRecipeCateg("cereale","condiment","lactate","","","","");

        recipe_id = addRecipe("desert","45 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,2,"Clatite");
        Log.d("DB","Reteta id: " + recipe_id);



        recipe_ing_id = addRecipeIngredient("Oua x3","Sunca 50g","Verdeata","Unt x1 lingura","Sare","Piper","","","","","","","","","","","","","","");
        recipe_steps_id = addRecipeSteps("Într-un vas se sparg 2-3 ouă, în funcție de cât de mare dorim să fie porția pregătită.","Se bat foarte bine cu un tel sau cu o furculiță Prin baterea ouălor foarte bine se va încorpora aer, rezultând o omletă pufoasă și aerată.","Se asezonează cu sare și piper. Șunca se taie după preferințe. ","Se pune șunca în tigaia încinsă, se ajustează focul și se amestecă cu o lingură de lemn, până se rumenește puțin.","Se împrăștie șunca cât mai uniform în tigaie și deasupra, se toarnă ouăle bătute, turnându-le cât mai omogen pe toată suprafața.","Se agită viguros tigaia înainte și înapoi deasupra focului timp de un minut, timp în care cu o spatulă termorezistentă se amestecă ouăle continuu.","O transferăm pe farfurie, lăsând-o să alunece ușor din tigaie.","","","","","","","","","","","","","");
        recipe_categ_id = addRecipeCateg("carne","condiment","lactate","","","","");

        recipe_id = addRecipe("micdejun","15 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,3,"Omleta cu sunca");
        Log.d("DB","Reteta id: " + recipe_id);

        recipe_ing_id = addRecipeIngredient("Faina integrala 600g","Faina alba 60g","Praf de copt 2g","Zahar vanilat x1","Oua x2","Unt topit 30g","Lapte 175ml","Sare","","","","","","","","","","","","");
//        recipe_steps_id = addRecipeSteps("","","","","","","","","","","","","","","","","","","","");
        recipe_steps_id = addRecipeSteps(" Într-un castron, făina se amestecă bine cu praful de copt. Separat, ouăle întregi se bat cu sarea și zahărul vanilat, cu un tel în formă de pară.","Se adaugă succesiv peste ouă câte 2-3 linguri de făină și câte puțin lapte, amestecând viguros după fiecare nouă tranșă de făină adăugată, evitând formarea cocoloașelor."," În final, se adaugă untul topit, dar nu fierbinte, și se omogenizează. Aluatul pentru vafe belgiene trebuie să fie cu ceva mai dens decât cel pentru clătite."," Am copt imediat vafele în aparatul deja încins. Opțional, se pot unge plăcile cu puțin unt topit sau ulei, dar nu e obligatoriu. ","","","","","","","","","","","","","","","","");
        recipe_categ_id = addRecipeCateg("cereale","condimente","lactate","","","","");

        recipe_id = addRecipe("micdejun","30 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,6,"Vafe belgiene");
        Log.d("DB","Reteta id: " + recipe_id);

        recipe_ing_id = addRecipeIngredient("Tortilla x2","Branza 100g","Ardei x2","Ceapa.verde x2","Rosii x6","Porumb","Chilli","Sare","Piper","","","","","","","","","","","");
        recipe_steps_id = addRecipeSteps("Cașcavalul se rade pe răzătoarea cu ochiuri mari. Se gustă pentru a vedea dacă e sărat și dacă are nevoie de asezonare . ","Se spală și se curăță legumele. Am scos cotorul de la ardei, am curățat de semințele și am tăiat pulpa cubulețe.","Se curăță și se taie ceapa verde, am adăugat în umplutură inclusiv frunzele verzi.","Se spală roșiile și se feliază.","Se pune tortilla la încălzit. După 10 secunde, se întoarce pe cealaltă parte. Se repetă această întoarcere de pe o parte pe alta la 10 secunde distanță de câteva ori. ","Se presară jumătate din cantitatea de brânză pe toată suprafața aluatului. Peste brânză, se presară restul ingredientelor. Nu se pune un strat prea gros de ingrediente.","Se reduce flacăra și se acoperă cu un capac.","Când brânza s-a topit, folosind o spatulă, se întoarce o parte a tortillei peste cealaltă jumătate ca la omletă."," Se ia de pe foc, se pune pe platoul de servire.Servită alături de guacamole, quesadilla este un adevărat deliciu.","","","","","","","","","","","");
        recipe_categ_id = addRecipeCateg("leguma","condimente","","","","","");

        recipe_id = addRecipe("micdejun","25 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,7,"Quesadilla mexicana");
        Log.d("DB","Reteta id: " + recipe_id);



        recipe_ing_id = addRecipeIngredient("Paste 400g","Ton 250g","Sos.de.rosii 500ml","Usturoi x5","Oregano x2 lingurita","Busuioc","Ceapa x1","Sare","Piper","","","","","","","","","","","");
        recipe_steps_id = addRecipeSteps("Pentru început, pune pastele la fiert.Cât timp fierb, toacă ceapa şi usturoiul şi pune-le într-o tigaie la călit, în uleiul scurs din conserva de ton.","Peste ceapa şi usturoiul călite adaugă cutia de roşii pasate şi lasă totul să fiarbă timp de 5 minute, la foc mediu.","După scurgerea timpului, adaugă busuiocul şi mai lasă să fiarbă încă 10 minute, până ce conţinutul tigăii scade."," Condimentează cu sare şi piper şi adaugă peste sosul din tigaie şi tonul mărunţit.","Mai lasă ingredientele pe foc încă 4-5 minute.","Între timp, scoate pastele de la fiert şi lasă-le la scurs într-o strecurătoare.","Când sosul este gata, toarnă-l peste paste şi încorporează-l în ele.","","","","","","","","","","","","","");
        recipe_categ_id = addRecipeCateg("carne","condimente","cereale","leguma","","","");

        recipe_id = addRecipe("pranz","15 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,4,"Paste cu ton");
        Log.d("DB","Reteta id: " + recipe_id);



        recipe_ing_id = addRecipeIngredient("Piept.de.pui x1","Morcov x3","Cartofi x3","Apa 700ml","Curry.cuburi x3","Miere x1 lingura","Ketchup.picant x1 lingura","Ulei x2 lingura","Ceapa x4","","","","","","","","","","","");
        recipe_steps_id = addRecipeSteps("Usturoiul se feliază subțire și se călește puțin cu uleiul. Se adaugă ceapa tăiată julien și se călesc împreună, până se înmoaie și ceapa devine translucidă.","Adăugăm pieptul de pui tăiat cubulețe și amestecăm constant, până ce puiul devine alb.","Punem morcovii tăiați rondele, amestecăm, lăsăm toate ingredientele încă puțin, apoi adăugăm și cartofii tăiați cuburi și apa.","Lăsăm să fiarbă la foc mic, sub capac. Când cartofii sunt pătrunși, încorporăm cuburile de curry, mierea și ketchupul.","Le mai lăsăm să fiarbă nițel, amestecând din când în când, până se îngroașe.","Et voila! Servim cu orez proaspăt fiert și savurăm minunăția asta care te face să spui „mhmmmm” la fiecare înghițitură.","","","","","","","","","","","","","","");
        recipe_categ_id = addRecipeCateg("carne","condimente","leguma","","","","");

        recipe_id = addRecipe("cina","30 min",recipe_ing_id,recipe_steps_id,recipe_categ_id,5,"Curry japonez");
        Log.d("DB","Reteta id: " + recipe_id);


    }


    public boolean hasNumbers(String str) {
        if(str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5")
                || str.contains("6") || str.contains("7") ||str.contains("8")||str.contains("9")){
            return true;
        }
        return false;
    }

}