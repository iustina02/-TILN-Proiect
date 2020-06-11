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
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;
import com.example.mily_alpha.RoWordNet.src.AlgCateg;


import java.util.ArrayList;
import java.util.Calendar;


public class ShoppingListActivity extends AppCompatActivity {

    public String NameUser;
    public String EmailUser;
    public DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        final Button addDataExpirareButton = findViewById(R.id.addDateButton);
        final TextView dataExpirareText = findViewById(R.id.dataExpirareText);
        final Button deleteProdusButton = findViewById(R.id.DeleteProdusButton);


        final int user_id = databaseHelper.getUserID(EmailUser,NameUser);
        ArrayList<String> productsIds = new ArrayList<String>();
        if(databaseHelper.GetProductsIDThatAreInShoppingList(user_id) != null){
            productsIds = databaseHelper.GetProductsIDThatAreInShoppingList(user_id);
        }
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

                            addDataExpirareButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Calendar calendar = Calendar.getInstance();
                                    int year = calendar.get(Calendar.YEAR);
                                    int month = calendar.get(Calendar.MONTH);
                                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                                    DatePickerDialog dialog = new DatePickerDialog(
                                            ShoppingListActivity.this,
                                            android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                                            mDateSetListener,
                                            year, month, day);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();
                                }
                            });

                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    month += 1;
                                    String luna = "0"+month;
                                    Log.d("DateTime: ", year + "/" + luna + "/" + dayOfMonth);
                                    dataExpirareText.setText(year + "/" + luna + "/" + dayOfMonth);
                                }
                            };

                            product_bought.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int ingredient_id = databaseHelper.getIngredientID(ProductsName[position]);
                                    if (ingredient_id == 0) {
                                        Log.d("Shopping list:", "Nu exista" + ProductsName[position]);
                                    } else {
                                        String categorie = databaseHelper.GetCategByUserIdIngrId(user_id, ingredient_id);
                                        String date = "";
                                        if(dataExpirareText.getText().toString().equals(" Selecteaza data de expirare ")){
                                            date = "Nici o data adaugata!";
                                        }else{
                                            date = dataExpirareText.getText().toString();
                                        }


                                        databaseHelper.addUser_Ingredient(user_id, ingredient_id, date, categorie);
                                        Log.d("Shopping list:", "Add to fridge " + ProductsName[position]);
                                        Log.d("Shopping list:", " date " + date);
                                        Log.d("Shopping list:", " ingredient id " + ingredient_id);

                                        dataExpirareText.setText(" Selecteaza data de expirare ");
                                    }
                                }
                            });

                            deleteProdusButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int ingredient_id = databaseHelper.getIngredientID(ProductsName[position]);
                                    if (ingredient_id == 0) {
                                        Log.d("Shopping list:", "Nu exista" + ProductsName[position]);
                                    } else {
                                        databaseHelper.DeleteIngredientFromShoopingList(user_id, ingredient_id);

                                        ArrayList<String> productsIds = new ArrayList<String>();
                                        if (databaseHelper.GetProductsIDThatAreInShoppingList(user_id) != null) {
                                            productsIds = databaseHelper.GetProductsIDThatAreInShoppingList(user_id);
                                        }
                                        if (!productsIds.isEmpty()) {
                                            final String[] ProductsName = new String[productsIds.size()];
                                            int count = 0;
                                            for (String productid : productsIds) {
                                                ProductsName[count] = databaseHelper.getIngredientById(productid);
                                                count++;
                                            }
                                            if (ProductsName.length > 0) {
                                                ListAdapter adapter = new ArrayAdapter<>(ShoppingListActivity.this, android.R.layout.simple_list_item_1, ProductsName);
                                                listView.setAdapter(adapter);
                                            }
                                        }
                                        else
                                        {
                                            Intent sendStuff = new Intent(ShoppingListActivity.this, ShoppingListActivity.class);
                                            sendStuff.putExtra("name", NameUser);
                                            sendStuff.putExtra("email", EmailUser);
                                            startActivity(sendStuff);
                                            finish();
                                        }

                                    }
                                }
                            });
                        }
                    });
                } else {
                    Log.d("Shopping List", "Nu sunt produse in lista 1");
                }
            }
        else
        {
            Log.d("Shopping List", "Nu sunt produse in lista 2");
            addDataExpirareButton.setVisibility(View.INVISIBLE);
            dataExpirareText.setText("Adauga produse in lista ta");
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
