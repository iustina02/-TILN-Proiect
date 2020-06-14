package com.example.mily_alpha;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
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

    private Spinner tip_spin;

    private Boolean goBack = false;

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
        categDisponibileListView.setVisibility(View.GONE);

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

        final ArrayList<String> toateIngredientele = new ArrayList<>();
        toateIngredientele.add("Avocado");
        toateIngredientele.add("Ceapa");
        toateIngredientele.add("Rosie");
        toateIngredientele.add("Lime");
        toateIngredientele.add("Avocado");
        toateIngredientele.add("Ceapa");
        toateIngredientele.add("Rosie");
        toateIngredientele.add("Lime");

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
        searchRetetaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipul_retetei = spinner.getSelectedItem().toString();
                ArrayList<String> search_ing = new ArrayList<>();
                ArrayList<String> search_categ = new ArrayList<>();

                for(int i = 0; i < toateIngredientele.size(); i++) {
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
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (goBack == true) {
            Log.d("Go back:", "YES");
            super.onBackPressed();
            Intent sendStuff = new Intent(SearchRecipeActivity.this, ProfileActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else {
            Log.d("Go back:", "NO");
        }
    }
}
