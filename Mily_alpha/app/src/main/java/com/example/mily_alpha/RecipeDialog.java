package com.example.mily_alpha;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mily_alpha.DB_Helper.Ingredients;

import java.util.ArrayList;

public class RecipeDialog extends AppCompatDialogFragment {
    private String Title;
    private String Ingredients ="";
    private String Steps = "";
    public String NameUser;
    public String EmailUser;
    private String Steps_intent ="";


    public RecipeDialog(String title, ArrayList<String> ingredients, ArrayList<String> steps, String nameUser, String emailUser){
        Title = title;
        NameUser = nameUser;
        EmailUser = emailUser;

        for(String ing: ingredients){
            if(!ing.equals("")) {
                Ingredients += ing;
                Ingredients += "\n";
            }
        }

        int counter = 1;
        for(String step: steps){
            if(!step.equals("")) {
                Steps += "Pasul " + counter + "\n\n";
                Steps += step;
                Steps_intent +=step;
                Steps_intent +="/";
                Steps += "\n\n";
                counter++;
            }

        }

    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Title)
                .setMessage("Ingrediente: \n\n" + Ingredients +"\n\n\n" +Steps)
                .setNegativeButton("Inchide", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sendStuff = new Intent(getContext(), RecipeStepsActivity.class);
                        sendStuff.putExtra("name", NameUser);
                        sendStuff.putExtra("email",EmailUser);
                        sendStuff.putExtra("title", Title);
                        sendStuff.putExtra("ingredients", Ingredients);
                        sendStuff.putExtra("steps", Steps_intent);
                        startActivity(sendStuff);
                    }
                });

        return builder.create();
    }
}
