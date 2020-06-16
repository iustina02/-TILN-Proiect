package com.example.mily_alpha;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;


import java.util.ArrayList;


public class SearchRecipeActivity extends AppCompatActivity {

    public String NameUser;
    public String EmailUser;
    public DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;

    private Button categDisponibileButton;
    private Button toateIngredienteButton;
    private Button searchRetetaButton;

    private ListView categDisponibileListView;
    private ListView toateIngredienteleListView;


    private int goBack = 0;

    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchrecipe);

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);


        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);
        fridgeButton = findViewById(R.id.frig_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(SearchRecipeActivity.this, ProfileActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(SearchRecipeActivity.this, MainActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendStuff = new Intent(SearchRecipeActivity.this, ListCategoryActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        final Spinner spinner = findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tips_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        categDisponibileButton = findViewById(R.id.categDisponibile_button);
        toateIngredienteButton = findViewById(R.id.toateIngredientele_button);
        searchRetetaButton = findViewById(R.id.search_recipe_button);

        categDisponibileListView = findViewById(R.id.categDisponibile_listView);
//        categDisponibileListView.setVisibility(View.GONE);

        int User_id = databaseHelper.getUserID(EmailUser, NameUser);
        ArrayList<String> CategoriiDisponibile = new ArrayList<>();
        if(databaseHelper.getCategoriesFromUser(User_id+"") != null){
            CategoriiDisponibile = databaseHelper.getCategoriesFromUser(User_id+"");
        }

        if (!CategoriiDisponibile.isEmpty()) {
            ListAdapter categ = new ArrayAdapter<>(SearchRecipeActivity.this, android.R.layout.simple_list_item_multiple_choice, CategoriiDisponibile);
            categDisponibileListView.setAdapter(categ);
        }

        toateIngredienteleListView = findViewById(R.id.toateIngredientele_listView);
        toateIngredienteleListView.setVisibility(View.GONE);

        ArrayList<String> toateIngredientele = new ArrayList<>();
        toateIngredientele = databaseHelper.getAllIngredientsFromRecipes();

        ListAdapter ingre = new ArrayAdapter<>(SearchRecipeActivity.this, android.R.layout.simple_list_item_multiple_choice, toateIngredientele);

        toateIngredienteleListView.setAdapter(ingre);
        toateIngredienteleListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        categDisponibileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toateIngredienteleListView.setVisibility(View.GONE);
                categDisponibileListView.setVisibility(View.VISIBLE);
                toateIngredienteleListView.clearChoices();
                toateIngredienteleListView.clearFocus();
            }
        });

        toateIngredienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categDisponibileListView.setVisibility(View.GONE);
                toateIngredienteleListView.setVisibility(View.VISIBLE);
                categDisponibileListView.clearChoices();
                categDisponibileListView.clearFocus();
            }
        });

        final SparseBooleanArray toateIngredienteleChecked = toateIngredienteleListView.getCheckedItemPositions();

        final SparseBooleanArray toatecategoriileChecked = categDisponibileListView.getCheckedItemPositions();


        final ArrayList<String> finalCategoriiDisponibile = CategoriiDisponibile;
        final ArrayList<String> finalToateIngredientele = toateIngredientele;
        searchRetetaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipul_retetei = spinner.getSelectedItem().toString();
                ArrayList<String> search_ing = new ArrayList<>();
                ArrayList<String> search_categ = new ArrayList<>();

                for(int i = 0; i < finalToateIngredientele.size(); i++) {
                    if (toateIngredienteleChecked.get(i)) {
                        search_ing.add(toateIngredienteleListView.getItemAtPosition(i).toString());
                    }
                }

                for(int i = 0; i < finalCategoriiDisponibile.size(); i++) {
                    if (toatecategoriileChecked.get(i)) {
                        search_categ.add(categDisponibileListView.getItemAtPosition(i).toString());
                    }
                }

                Log.d("SR","Type: "+ tipul_retetei + " Ingredients: " + search_ing + " Categories: " + search_categ);

                ArrayList<String> id_recipes = new ArrayList<>();
                if(search_ing.size() > 0){
                    // Cautare reteta dupa tipul retetei si ingrediente
                    Log.d("SR","Check ingredients: " + search_ing);
                    id_recipes = databaseHelper.searchRecipesAfterIngredient_returnListIdRetete(tipul_retetei,search_ing);
                    Log.d("SP","Found recipes id: " + id_recipes);
                }
                if(search_categ.size() > 0){
                    // Cautare reteta dupa tipul retetei si categorii
                    Log.d("SR","Check categ: " + search_categ);
                    id_recipes = databaseHelper.searchRecipesAfterCategory_returnListIdRetete(tipul_retetei,search_categ);
                    Log.d("SP","Found recipes id: " + id_recipes);
                }

                if(!id_recipes.isEmpty()) {
                    setContentView(R.layout.activity_list_recipes);
                    goBack = 1;
                    ListView recipes_listView = findViewById(R.id.recipes_listview);

                    ArrayList<String> Names = databaseHelper.getNamesFromRecipes(id_recipes);
                    ArrayList<String> Times = databaseHelper.getTimeFromRecipes(id_recipes);
                    ArrayList<Integer> Img_numbers = databaseHelper.getImgNumberFromRecipes(id_recipes);

                    ArrayList<Integer> Img_numbers_drawable = new ArrayList<>();

                    for(Integer img_number: Img_numbers){
                        String photo =  "recipe_"+ img_number;
                        int resID = getResources().getIdentifier(photo , "drawable", getPackageName());
                        Img_numbers_drawable.add(resID);
                    }

                    RecipeListView adapter = new RecipeListView(SearchRecipeActivity.this, Names,Times, Img_numbers_drawable);
                    recipes_listView.setAdapter(adapter);

                    recipes_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            openDialog(position+1);
                        }
                    });
                }
                else{
                    ErrorDialog errorDialog = new ErrorDialog("Retete", "Nu am gasit retete.");
                    errorDialog.show(getSupportFragmentManager(),"error dialog");
                }
            }
        });
    }

    public void openDialog(int id_recipe){
        Log.d("SR","position" + id_recipe);
        String title = databaseHelper.getNameFromRecipe(id_recipe+"");
        ArrayList<String> ingredients = databaseHelper.getAllIngredientsFullFromRecipe(id_recipe+"");
        ArrayList<String> steps = databaseHelper.getAllStepsFromRecipe(id_recipe+"");


        RecipeDialog recipeDialog =  new RecipeDialog(title,ingredients, steps, NameUser, EmailUser);
        recipeDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onBackPressed() {
        if (goBack == 1) {
            Log.d("Go back:", "YES");
            super.onBackPressed();
            Intent sendStuff = new Intent(SearchRecipeActivity.this, SearchRecipeActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else if(goBack == 0) {
            Log.d("Go back:", "NO");
        }
    }
}
