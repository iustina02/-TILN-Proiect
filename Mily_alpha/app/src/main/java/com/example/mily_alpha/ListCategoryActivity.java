package com.example.mily_alpha;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListCategoryActivity extends AppCompatActivity {

     String TAG  = "FRIDGE";

    DatabaseHelper databaseHelper;

    private Boolean goBack = false;

    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;
    private Button fructeButton;
    private Button legumeButton;
    private Button lactateButton;
    private Button carneButton;
    private Button fainaButton;
    private Button condimenteButton;
    private Button gustariButton;

    public String NameUser;
    public String EmailUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frigider);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);


        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);

        fructeButton = findViewById(R.id.fructe_button);
        legumeButton =  findViewById(R.id.legume_button);
        lactateButton = findViewById(R.id.lactate_button);
        carneButton = findViewById(R.id.carne_button);
        fainaButton =  findViewById(R.id.faina_button);
        condimenteButton = findViewById(R.id.condimente_button);
        gustariButton = findViewById(R.id.gustari_button);


        fructeButton.setVisibility(View.GONE);
        legumeButton.setVisibility(View.GONE);
        lactateButton.setVisibility(View.GONE);
        carneButton.setVisibility(View.GONE);
        fainaButton.setVisibility(View.GONE);
        condimenteButton.setVisibility(View.GONE);
        gustariButton.setVisibility(View.GONE);


        databaseHelper = DatabaseHelper.getInstance(this);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ListCategoryActivity.this, ProfileActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ListCategoryActivity.this, MainActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        Button searchRecipeButton = findViewById(R.id.recipe_button);

        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ListCategoryActivity.this, SearchRecipeActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });


        fainaButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                ActionBar actionBar = getSupportActionBar();
                actionBar.hide();
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "cereale") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "cereale");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }

//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));

                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "cereale") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "cereale");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                    {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        lactateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "lactate") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "lactate");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    final ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }

//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));

                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "lactate") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "lactate");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        carneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "carne") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "carne");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }

//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));

                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "carne") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "carne");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        fructeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "fruct") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "fruct");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }

//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));

                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "fruct") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "fruct");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        legumeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "legume") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "legume");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }

//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));

                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "legume") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "legume");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        condimenteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "condiment") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "condiment");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }

//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));

                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "condiment") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "condiment");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        gustariButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_produs_categ);
                final ListView seeProductByCateg = findViewById(R.id.list_products);
                final Button deleteProdusButon = findViewById(R.id.DeleteProdusButton);
                final Button addToShoppingList = findViewById(R.id.AddToShoppingList);

                final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
                final TextView textViewDataExpiration = findViewById(R.id.dataExpirareTextView);
                final TextView textViewCalories =  findViewById(R.id.caloriesTextView);
                final int User_id = databaseHelper.getUserID(EmailUser, NameUser);

                final int[] Ingredient_id = new int[1];
                Ingredient_id[0] = -1;

                ArrayList<String> list_userIng_ids = new ArrayList<>();
                if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "gustarisibauturi") != null) {
                    list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "gustarisibauturi");
                }
                if(!list_userIng_ids.isEmpty())
                {
                    ArrayList<String> list_Ing_ids = new ArrayList<>();
                    for (String userIng_id : list_userIng_ids){
                        list_Ing_ids.add(databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                    }

                    if(!list_Ing_ids.isEmpty()) {
                        Log.d("Produs in frigider", "Id ingredient !" + list_Ing_ids);
                        ArrayList<String> Ingrediente = new  ArrayList<String>();

                        for (String ingredient_id : list_Ing_ids) {
                            Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                            if(databaseHelper.getIngredientById(ingredient_id) == null){
                                Log.d("From refrigerator", "Null produs: " + ingredient_id);
                            }
                        }
                        Log.d("From refrigerator", "Produse: " + Ingrediente);

                        if (!Ingrediente.isEmpty()) {
                            ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                            seeProductByCateg.setAdapter(adapter);
                        }


//                     Selectarea unui produs din lista dupa categorie
                        final ArrayList<String> finalList_Ing_ids= list_Ing_ids;
                        final ArrayList<String> finalList_userIng_ids = list_userIng_ids;
                        seeProductByCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                view.setSelected(true);

                                Ingredient_id[0] = databaseHelper.getIngredientID(seeProductByCateg.getItemAtPosition(i).toString());
                                Ingredient_id[0] = Integer.parseInt(finalList_Ing_ids.get(i));
                                final int User_Ingredient_Id = Integer.parseInt(finalList_userIng_ids.get(i));
                                String DataExpiration = databaseHelper.GetProdusDataExpiration(User_Ingredient_Id);
                                String calories = databaseHelper.GetIngCaloriesByID(Ingredient_id[0]);
                                textViewDataExpiration.setText(DataExpiration);
                                textViewCalories.setText(calories);
                                Log.d("Produs in frigide", "Produs selectat !" + seeProductByCateg.getItemAtPosition(i).toString());
                                Log.d("Produs in frigide", "Data expirare !" + DataExpiration);
                                Log.d("Produs in frigide", "Id ingredient !" + Ingredient_id[0]);
                                Log.d("Produs in frigide", "User_Ing_id !" + finalList_userIng_ids.get(i));


                                final String NumeProdus = seeProductByCateg.getItemAtPosition(i).toString();

                                deleteProdusButon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("From refrigerator", "Delete: " + NumeProdus);
                                        databaseHelper.DeleteIngredientByUserIngID(User_Ingredient_Id);

                                        ArrayList<String> list_userIng_ids = new ArrayList<>();
                                        if(databaseHelper.GetUserIngIDByCategAndUser(User_id, "gustarisibauturi") != null) {
                                            list_userIng_ids = databaseHelper.GetUserIngIDByCategAndUser(User_id, "gustarisibauturi");
                                        }
                                        if(!list_userIng_ids.isEmpty()) {
                                            ArrayList<String> list_Ing_ids = new ArrayList<>();
                                            int count = 0;
                                            for (String userIng_id : list_userIng_ids) {
                                                list_Ing_ids.add(count, databaseHelper.GetIngIdByUserIngId(Integer.parseInt(userIng_id)));
                                                count++;
                                            }

                                            if(!list_Ing_ids.isEmpty()) {
                                                Log.d("Produs in frigide", "Id ingredient !" + list_Ing_ids);
                                                ArrayList<String> Ingrediente = new  ArrayList<String>();

                                                for (String ingredient_id : list_Ing_ids) {
                                                    Ingrediente.add(databaseHelper.getIngredientById(ingredient_id));
                                                    if(databaseHelper.getIngredientById(ingredient_id) == null){
                                                        Log.d("From refrigerator", "Null produs: " + ingredient_id);
                                                    }
                                                }
                                                Log.d("From refrigerator", "Produse: " + Ingrediente);

                                                if (!Ingrediente.isEmpty()) {
                                                    ListAdapter adapter = new ArrayAdapter<>(ListCategoryActivity.this, android.R.layout.simple_list_item_1, Ingrediente);
                                                    seeProductByCateg.setAdapter(adapter);
                                                }
                                            }
                                        } else {
                                            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }
                                    }
                                });

                                addToShoppingList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (databaseHelper.AddIngredientInShoopingList(User_Ingredient_Id))
                                            Log.d("From refrigerator", "Add to shopping list: " + NumeProdus);
                                        else {
                                            Log.d("From refrigerator", "Nu s-a putut adauga la shopping list: " + NumeProdus);
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
                else
                {
                    Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                    finish();
                }
                goBack = true;


            }
        });

        populateList();
    }

    private int check_date_expiration(String dataExpiration) {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd");
        Date date_now = new Date(System.currentTimeMillis());
        String dateNow = formatter.format(date_now);

        String[] year_month_day_expiration_date = dataExpiration.split("/");
        String[] year_month_day_now = dateNow.split("/");
        if (Integer.parseInt(year_month_day_expiration_date[0]) >= Integer.parseInt(year_month_day_now[0])) {
            if (Integer.parseInt(year_month_day_expiration_date[1]) >= Integer.parseInt(year_month_day_now[1])) {
                if (Integer.parseInt(year_month_day_expiration_date[2]) >=  (Integer.parseInt(year_month_day_now[2]) + 14)) {
                    return 5;
                } else if (Integer.parseInt(year_month_day_expiration_date[2]) >=  (Integer.parseInt(year_month_day_now[2]) + 10)) {
                    return 4;
                }
                else if (Integer.parseInt(year_month_day_expiration_date[2]) >=  (Integer.parseInt(year_month_day_now[2]) + 5)) {
                    return 3;
                }
                else if (Integer.parseInt(year_month_day_expiration_date[2]) >=  (Integer.parseInt(year_month_day_now[2]) + 3)) {
                    return 2;
                }
            }
        }
        return 6;
    }

    private void populateList() {

        Log.d(TAG, "populateListUsers: Displaying data in the ListView");

//        UserDBHelper userDBHelper = new UserDBHelper(ListCategoryActivity.this);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ListCategoryActivity.this);
        int User_id = databaseHelper.getUserID(EmailUser, NameUser);
        ArrayList<String> listData = null;

        Log.d(TAG," "+User_id);
        if (databaseHelper.getCategoriesFromUser(User_id + "") != null) {
            listData = databaseHelper.getCategoriesFromUser(User_id + "");
            if (listData != null) {
                for (String categ : listData) {
                    Log.d(TAG, "Categ:" + categ);
                    if (categ.equals("carne")) {
                        carneButton.setVisibility(View.VISIBLE);
                        carneButton.setEnabled(true);
                    } else if (categ.equals("cereale")) {
                        fainaButton.setVisibility(View.VISIBLE);
                        fainaButton.setEnabled(true);
                    } else if (categ.equals("fruct")) {
                        fructeButton.setVisibility(View.VISIBLE);
                        fructeButton.setEnabled(true);
                    } else if (categ.equals("lactate")) {
                        lactateButton.setVisibility(View.VISIBLE);
                        lactateButton.setEnabled(true);
                    } else if (categ.equals("condiment")) {
                        condimenteButton.setVisibility(View.VISIBLE);
                        condimenteButton.setEnabled(true);
                    }else if (categ.equals("legume")) {
                        legumeButton.setVisibility(View.VISIBLE);
                        legumeButton.setEnabled(true);
                    }else if (categ.equals("gustarisibauturi")) {
                        gustariButton.setVisibility(View.VISIBLE);
                        gustariButton.setEnabled(true);
                    }
                }
            } else {
                Log.d(TAG, "Lista este goala !");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (goBack == true) {
            Log.d("Go back:", "YES");
            super.onBackPressed();
            Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else {
            Log.d("Go back:", "NO");
        }
    }
}
