package com.example.mily_alpha;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShoppingListActivity extends AppCompatActivity {

    public String NameUser;
    public String EmailUser;
    public DatabaseHelper databaseHelper = new DatabaseHelper(this);

    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);

        listView = findViewById(R.id.shoppingListView);
        final Button product_bought = findViewById(R.id.ProductBought);

        final int user_id = databaseHelper.getUserID(EmailUser,NameUser);
        ArrayList<String> productsIds;
        ArrayList<String> ceva = new ArrayList<String>();
        productsIds = databaseHelper.GetProductsIDThatAreInShoppingList(user_id);
        if(!productsIds.isEmpty())
            {
                final String[] ProductsName = new String[productsIds.size()];
                int count = 0;
                for (String productid : productsIds) {
                    ProductsName[count] = databaseHelper.getIngredientById(productid);
                    count++;
                }
                if (ProductsName.length > 0) {
                    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ProductsName);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            view.setSelected(true);
                            Log.d("Position", position + "");

                            product_bought.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int ingredient_id = databaseHelper.getIngredientID(ProductsName[position]);
                                    if (ingredient_id == 0) {
                                        Log.d("Shopping list:", "Nu exista" + ProductsName[position]);
                                    } else {
                                        String categorie = databaseHelper.GetCategByUserIdIngrId(user_id, ingredient_id);
                                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());


                                        databaseHelper.addUser_Ingredient(user_id, ingredient_id, currentDate, categorie);
                                        Log.d("Shopping list:", "Add to fridge " + ProductsName[position]);
                                        Log.d("Shopping list:", " date " + currentDate);
                                        Log.d("Shopping list:", " ingredient id " + ingredient_id);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(this, "Nu sunt produse in lista", Toast.LENGTH_SHORT).show();
                }
            }
        else
        {
            Toast.makeText(this, "Nu sunt produse in lista", Toast.LENGTH_SHORT).show();
        }


    }

    private Boolean goBack =true;
    @Override
    public void onBackPressed() {
        if (goBack == true) {
            super.onBackPressed();
            Intent sendStuff = new Intent(ShoppingListActivity.this, ProfileActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else {
            Log.d("Go back:", "NO");
        }
    }
}
