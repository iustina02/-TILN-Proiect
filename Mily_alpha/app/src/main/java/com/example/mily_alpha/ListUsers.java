package com.example.mily_alpha;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mily_alpha.DB_Helper.UserDBHelper;

import java.util.ArrayList;

public class ListUsers extends AppCompatActivity {

     String TAG  = "ListUsers";

    UserDBHelper userDBHelper;

     ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_users_layout);
        listView = findViewById(R.id.list_users);
        userDBHelper = new UserDBHelper(this);

        populateList();
    }

    private void populateList() {

        Log.d(TAG, "populateListUsers: Displaying data in the ListView");

        Cursor data = userDBHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(2));
            listData.add(data.getString(1));
        }
        if(!listData.isEmpty()) {
            Log.d(TAG, "Lista este afisata !");
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            listView.setAdapter(adapter);
        }else
        {
            Log.d(TAG, "Lista este goala !");
        }
    }
}
