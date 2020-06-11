package com.example.mily_alpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mily_alpha.DB_Helper.DatabaseHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    DatabaseHelper databaseHelper;

    private ImageView profileImage;

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getEmailTextView() {
        return emailTextView;
    }

    public TextView nameTextView;
    public TextView emailTextView;
    public String NameUser;
    public String EmailUser;

    private Button signOutButton;
    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;
    private Button searchRecipeButton;

    private Button SeeShoopingList;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profileImage = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        signOutButton = findViewById(R.id.signOutButton);

        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);
        fridgeButton = findViewById(R.id.frig_button);
        searchRecipeButton = findViewById(R.id.recipe_button);

        SeeShoopingList = findViewById(R.id.SeeShoopingList);

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);

        databaseHelper = new DatabaseHelper(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            String checkbox = preferences.getString("remember","");
                            if(checkbox.equals("true")){
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remember","false");
                                editor.apply();
                            }
                            gotoLoginActivity();
                        }
                        else
                            Toast.makeText(ProfileActivity.this,"Log Out Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        SeeShoopingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, ShoppingListActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, ProfileActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, MainActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, ListCategoryActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(ProfileActivity.this, SearchRecipeActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });



    }


    private void gotoMainActivity() {

        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }


    private void gotoLoginActivity() {

        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            NameUser = account.getDisplayName();
            EmailUser = account.getEmail();

            nameTextView.setText(NameUser);
            emailTextView.setText(EmailUser);
            AddData(account.getEmail(),account.getDisplayName());

            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(profileImage);
        }else{
            Toast.makeText(ProfileActivity.this,"Log in Failed", Toast.LENGTH_SHORT).show();
            Log.w("Log in String: failed! ","Logged in failed!");
           gotoMainActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void AddData(String UserEmail, String UserName){
        boolean insertData = databaseHelper.addUser(UserEmail,UserName);
//        if(insertData) {
////            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
//        }else{
////            Toast.makeText(this, "Data Insert Failed!", Toast.LENGTH_SHORT).show();
//        }
    }

    private Boolean goBack = false;
    @Override
    public void onBackPressed() {
        if (goBack == true) {
            Log.d("Go back:", "YES");
            super.onBackPressed();
            Intent sendStuff = new Intent(ProfileActivity.this, ProfileActivity.class);
            sendStuff.putExtra("name", NameUser);
            sendStuff.putExtra("email",EmailUser);
            startActivity(sendStuff);
            finish();
        } else {
            Log.d("Go back:", "NO");
        }
    }
}
