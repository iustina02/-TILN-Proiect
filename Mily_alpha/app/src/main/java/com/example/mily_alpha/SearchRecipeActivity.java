package com.example.mily_alpha;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;


import java.util.ArrayList;
import java.util.Calendar;


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

        final Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tips_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String tipul_retetei = spinner.getSelectedItem().toString();
        Log.d("Search Recipe","Tipul retetei: " + tipul_retetei);

        categDisponibileButton = findViewById(R.id.categDisponibile_button);
        toateIngredienteButton = findViewById(R.id.toateIngredientele_button);

        categDisponibileListView = findViewById(R.id.categDisponibile_listView);
        categDisponibileListView.setVisibility(View.GONE);

        int User_id = databaseHelper.getUserID(EmailUser, NameUser);
        ArrayList<String> CategoriiDisponibile = new ArrayList<>();
        if(databaseHelper.getCategoriesFromUser(User_id+"") != null){
            CategoriiDisponibile = databaseHelper.getCategoriesFromUser(User_id+"");
        }

        if (!CategoriiDisponibile.isEmpty()) {
            ListAdapter categ = new ArrayAdapter<>(SearchRecipeActivity.this, android.R.layout.simple_list_item_1, CategoriiDisponibile);
            categDisponibileListView.setAdapter(categ);
        }

        toateIngredienteleListView = findViewById(R.id.toateIngredientele_listView);
        toateIngredienteleListView.setVisibility(View.GONE);

        ArrayList<String> toateIngredientele = new ArrayList<>();
        toateIngredientele.add("Avocado");
        toateIngredientele.add("Ceapa");

        ListAdapter ingre = new ArrayAdapter<>(SearchRecipeActivity.this, android.R.layout.simple_list_item_1, toateIngredientele);
        toateIngredienteleListView.setAdapter(ingre);

        categDisponibileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toateIngredienteleListView.setVisibility(View.GONE);
                categDisponibileListView.setVisibility(View.VISIBLE);
            }
        });

        toateIngredienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categDisponibileListView.setVisibility(View.GONE);
                toateIngredienteleListView.setVisibility(View.VISIBLE);
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
