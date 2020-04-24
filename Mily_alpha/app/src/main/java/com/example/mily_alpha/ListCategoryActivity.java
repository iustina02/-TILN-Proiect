package com.example.mily_alpha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.IngredientDBHelper;
import com.example.mily_alpha.DB_Helper.UserDBHelper;
import com.example.mily_alpha.DB_Helper.User_IngreDBHelper;

import java.util.ArrayList;

public class ListCategoryActivity extends AppCompatActivity {

     String TAG  = "ListUsers";

    UserDBHelper userDBHelper;
    IngredientDBHelper ingredientDBHelper;
    User_IngreDBHelper user_ingreDBHelper;

    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;
    private Button fainaButton;
    private Button carneButton;
    private Button fructeButton;
    private Button condimenteButton;
    private Button lactateButton;
    private Button mirodeniiButton;
    private Button sosButton;
    private Button pasteButton;

    public String NameUser;
    public String EmailUser;

     ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frigider);
        listView = findViewById(R.id.list_users);

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);


        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);
        fridgeButton = findViewById(R.id.frig_button);

        fainaButton =  findViewById(R.id.faina_button);
        carneButton = findViewById(R.id.carne_button);
        fructeButton = findViewById(R.id.fructe_button);

        condimenteButton = findViewById(R.id.condimente_button);
        lactateButton = findViewById(R.id.lactate_button);
        mirodeniiButton = findViewById(R.id.mirodenii_button);
        sosButton = findViewById(R.id.sos_button);
        pasteButton = findViewById(R.id.paste_button);

        fainaButton.setVisibility(View.GONE);
        carneButton.setVisibility(View.GONE);
        fructeButton.setVisibility(View.GONE);

        condimenteButton.setVisibility(View.GONE);
        lactateButton.setVisibility(View.GONE);
        mirodeniiButton.setVisibility(View.GONE);
        sosButton.setVisibility(View.GONE);
        pasteButton.setVisibility(View.GONE);

        userDBHelper = new UserDBHelper(this);
        ingredientDBHelper = new IngredientDBHelper(this);
        user_ingreDBHelper = new User_IngreDBHelper(this);

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

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ListCategoryActivity.this, ListCategoryActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        populateList();
    }

    private void populateList() {

        Log.d(TAG, "populateListUsers: Displaying data in the ListView");

        ArrayList<String> listData ;
        if(user_ingreDBHelper.getData() != null) {
            listData = user_ingreDBHelper.getData();
        }
        else
            listData = ingredientDBHelper.getData();

        for ( String categ : listData) {
            Log.d(TAG, "Categ:" + categ);
            if(categ.equals(" carne")) {
                carneButton.setVisibility(View.VISIBLE);
                carneButton.setEnabled(true);
            }
            else if(categ.equals(" faina")) {
                fainaButton.setVisibility(View.VISIBLE);
                fainaButton.setEnabled(true);
            }
            else if(categ.equals(" fruct")) {
                fructeButton.setVisibility(View.VISIBLE);
                fructeButton.setEnabled(true);
            }
            else if(categ.equals(" lactate")) {
                lactateButton.setVisibility(View.VISIBLE);
                lactateButton.setEnabled(true);
            }
            else if(categ.equals(" condiment")) {
                condimenteButton.setVisibility(View.VISIBLE);
                condimenteButton.setEnabled(true);
            }
            else if(categ.equals(" mirodenii")) {
                mirodeniiButton.setVisibility(View.VISIBLE);
                mirodeniiButton.setEnabled(true);
            }
            else if(categ.equals("sos")) {
                sosButton.setVisibility(View.VISIBLE);
                sosButton.setEnabled(true);
            }
            else if(categ.equals(" paste")) {
                pasteButton.setVisibility(View.VISIBLE);
                pasteButton.setEnabled(true);
            }
        }

//
//        if(!listData.isEmpty()) {
//            Log.d(TAG, "Lista este afisata !");
//            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//            listView.setAdapter(adapter);
//        }else
//        {
//            Log.d(TAG, "Lista este goala !");
//        }
    }
}
