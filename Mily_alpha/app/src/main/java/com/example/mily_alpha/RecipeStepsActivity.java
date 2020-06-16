package com.example.mily_alpha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeStepsActivity extends AppCompatActivity {

    private String TAG = "Recipe Steps";
    private Integer goBack = 1;

    private String Title;
    private String Ingredients ="";
    public String NameUser;
    public String EmailUser;

    public ArrayList<String> Steps_array = new ArrayList<>();


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");
        Title =  startingIntent.getStringExtra("title");
        Ingredients = startingIntent.getStringExtra("ingredients");
        final String[] Steps = startingIntent.getStringExtra("steps").split("/");

        Log.d(TAG,TAG + Steps);

        TextView title_textView = findViewById(R.id.recipe_title);
        final TextView subtitle_textView = findViewById(R.id.recipe_subtitle);
        final TextView mily_textView = findViewById(R.id.mily_textview);
        final TextView step_textView = findViewById(R.id.step_textView);

        final Button next_Button = findViewById(R.id.next_step_button);
        final Button prev_Button = findViewById(R.id.prev_step_button);

        title_textView.setText(Title);

        int count = 0;

        if(count == 0){
            prev_Button.setVisibility(View.INVISIBLE);
            prev_Button.setEnabled(false);
            subtitle_textView.setText("Ingrediente: ");
            mily_textView.setText(mily_steps_text(count));
            step_textView.setText(Ingredients);
            count++;
        }

        class Counter {
            int steps;
        }

        final Counter counter = new Counter();

        //1
        counter.steps = count;

        next_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_Button.setVisibility(View.VISIBLE);
                prev_Button.setEnabled(true);
                if(next_Button.getText().equals("Gata")){
                    Intent sendStuff = new Intent(RecipeStepsActivity.this, ListCategoryActivity.class);
                    sendStuff.putExtra("name", NameUser);
                    sendStuff.putExtra("email",EmailUser);
                    startActivity(sendStuff);
                }
                if(counter.steps == Steps.length) {
                    next_Button.setText("Gata");

                    //Pasul 2
                    subtitle_textView.setText("Pasul " + (counter.steps));
                    //Text 1
                    mily_textView.setText(mily_steps_text(counter.steps));
                    //Step 0
                    step_textView.setText(Steps[(counter.steps - 1)]);

                }
                else {
                    //Pasul 1
                    subtitle_textView.setText("Pasul " + (counter.steps));
                    //Text 1
                    mily_textView.setText(mily_steps_text(counter.steps));
                    //Step 0
                    step_textView.setText(Steps[(counter.steps - 1)]);

                    // Counter 2
                    counter.steps++;
                }
            }
        });

        prev_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next_Button.getText().equals("Gata")){
                    next_Button.setText("Urmatorul pas");
                }
                counter.steps--;
                if(counter.steps == 1) {
                    prev_Button.setVisibility(View.INVISIBLE);
                    prev_Button.setEnabled(false);

                    //Pasul 1
                    subtitle_textView.setText("Ingrediente: ");
                    //Text 0
                    mily_textView.setText(mily_steps_text(counter.steps -1));
                    step_textView.setText(Ingredients);
                }
                else {
                    //Counter == 2

                    //Pasul 2
                    subtitle_textView.setText("Pasul " + (counter.steps-1));
                    // Text 1
                    mily_textView.setText(mily_steps_text(counter.steps - 1));
                    //Step
                    step_textView.setText(Steps[(counter.steps - 2)]);
                }


            }
        });

    }

    private String mily_steps_text(int count) {
        String text = "";
        switch(count) {
            case 0:
                text = "Prima data vom aduna ingredientele.";
                break;
            case 1:
                text = "Incepem!";
                break;
            case 2:
                text = "Al treilea pas!";
                break;
            case 3:
                text = "De aici devine interesant";
                break;
            case 4:
                text = "Cel de al cincilea pas!";
                break;
            case 5:
                text = "Am ajuns deja asa departe!";
                break;
            case 6:
                text = "Pasul 7 este cel mai usor";
                break;
            case 7:
                text = "Ador pasul asta!";
                break;
            case 8:
                text = "Al noualea pas!";
                break;
            default:
                // code block
        }
        return text;
    }

    @Override
    public void onBackPressed() {
        if (goBack == 1) {
            Log.d("Go back:", "YES");
            super.onBackPressed();
            Intent sendStuff = new Intent(this, SearchRecipeActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else if(goBack == 0) {
            Log.d("Go back:", "NO");
        }
    }
}
