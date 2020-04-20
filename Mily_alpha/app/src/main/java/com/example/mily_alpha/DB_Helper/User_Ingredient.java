package com.example.mily_alpha.DB_Helper;

public class User_Ingredient {
    int User_Ingredient_id;
    int User_id;
    int Ingredient_id;
    String Date;
    String Amount;

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public int getIngredient_id() {
        return Ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        Ingredient_id = ingredient_id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public int getUser_Ingredient_id() {
        return User_Ingredient_id;
    }

    public void setUser_Ingredient_id(int user_Ingredient_id) {
        User_Ingredient_id = user_Ingredient_id;
    }
}
