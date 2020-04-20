package com.example.mily_alpha.DB_Helper;

public class Ingredients {

    int Ingredient_Id;
    String IngredientName;
    int IngredientCalories;

    public Ingredients(String ingredientsName, int ingredientsCalories){
        IngredientName = ingredientsName;
        IngredientCalories = ingredientsCalories;
    }

    public int getIngredient_Id() {
        return Ingredient_Id;
    }

    public void setIngredient_Id(int ingredient_Id) {
        Ingredient_Id = ingredient_Id;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public int getIngredientCalories() {
        return IngredientCalories;
    }

    public void setIngredientCalories(int ingredientCalories) {
        IngredientCalories = ingredientCalories;
    }
}
