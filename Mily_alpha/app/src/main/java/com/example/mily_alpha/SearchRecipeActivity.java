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

    private Boolean goBack = false;

    public ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
